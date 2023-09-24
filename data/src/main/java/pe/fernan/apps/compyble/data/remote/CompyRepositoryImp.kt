package pe.fernan.apps.compyble.data.remote

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import pe.fernan.apps.compyble.domain.model.Advertisement
import pe.fernan.apps.compyble.domain.model.Category
import pe.fernan.apps.compyble.domain.model.Data
import pe.fernan.apps.compyble.domain.model.Popup
import pe.fernan.apps.compyble.domain.model.Product
import pe.fernan.apps.compyble.domain.model.Slider
import pe.fernan.apps.compyble.domain.repository.CompyRepository


private val banner = "https://compy.pe/img/thumbnail/nano-menu_diasoh-rp-mobile.png"

private val popup = Popup(
    href = "https://compy.pe/galeria?pagesize=24&page=1&sort=save&topstore=Shopstar&prices=199-10000&tags=Tecnolog%C3%ADa&utm_campaign_store=powersale-sp-shopstar",
    imageUrl = "https://compy.pe/img/banner/powersale-shopstar-banner.webp"
)

class CompyRepositoryImp(private val api: CompyApi) : CompyRepository {
    private fun getProduct(element: Element?) = Product(
        brand = element?.attr("data-product-brand") ?: "",
        title = element?.attr("data-product-name") ?: "",
        description = "",
        price = element?.attr("data-product-price") ?: "",
        currency = element?.attr("data-product-currency") ?: "",
        imageUrl = element?.select("picture>img")?.attr("src") ?: "",
        discount = element?.select("p")?.first()?.select("small")?.text() ?: "",
        href = element?.attr("href") ?: "",
    )

    private val productSelectorCSS = "div[class=card-2 c-2-1]"
    private fun getProducts(doc: Document): List<Product> {
        val products = doc.select(productSelectorCSS).map {
            val elementA = it.select("a").first()
            getProduct(elementA)
        }
        return products
    }


    override fun getMain() = flow {

        val doc = Jsoup.parse(api.getHome().body()!!)
        val sliders = doc.select("section.section-1>div>div").map {
            val backgroundColor = it.attr("class").substringAfterLast("c-1")
            val buttonColor = it.select("a").first()?.attr("class")
                ?.substringAfterLast("btn-primary") ?: ""

            // /img/thumbnail/airpods.png
            Slider(
                title = it.select("h3").text(),
                description = it.select("a").first()?.text() ?: "",
                href = it.select("a").first()?.attr("href") ?: "",
                image = it.select("picture>img").attr("src"),

                buttonColor = buttonColor,
                backgroundColor = backgroundColor
            )
        }

        val advertisements = doc.select("section[class^=section-2]").select("div.image-block").map {
            Advertisement(
                imageUrl = it.select("img").attr("src"),
                href = it.select("a").first()?.attr("href") ?: ""
            )
        }

        val productCategories = mutableListOf<Pair<String, List<Product>>>()


        val productsTop = getProducts(doc)

        val productsTopTitle =
            doc.select(productSelectorCSS).parents().first()?.select("header>h4")?.first()?.text()
                ?: "Productos Top"
        productCategories.add(productsTopTitle to productsTop)

        val others = doc.select("div.card-4-block-group").first()
            ?.select("div.card-4-block")?.map {
                val titleCategory = it.select("h6").first()?.text() ?: ""
                val products = it.select("div.card-4").map { element ->
                    getProduct(element?.select("a")?.first())
                }
                titleCategory to products
            } ?: listOf()

        productCategories.addAll(others)

        emit(
            Data(
                banner = banner,
                popup = popup,
                sliders = sliders,
                advertisements = advertisements,
                productCategories = productCategories
            )
        )
    }


    override fun getCategories(): Flow<List<Category>> = flow {
        val doc = Jsoup.parse(api.getHome().body()!!)

        val scriptJson = doc.select("script[data-search-tokens]").html()
        val gson = Gson()

        val type = object : TypeToken<Map<String, Map<String, Any>>>() {}.type
        val productInfoList: Map<String, Map<String, Any>> = gson.fromJson(scriptJson, type)

        val categoryMap = mutableMapOf<String, List<String>>()

        for ((productName, productInfo) in productInfoList) {

            val category = productInfo["category"] as? String?
            val subcategory = productInfo["subcategory"] as? String

            if (category != null && subcategory != null) {
                val existingCategory = categoryMap[category]?.toMutableList()
                if (existingCategory == null) {
                    categoryMap[category] = (listOf(subcategory))
                } else {
                    existingCategory.add(subcategory)
                    categoryMap[category] = existingCategory.distinct()

                }
            }

        }

        val categoryList = categoryMap.map {
            Category(it.key, it.value)
        }
        emit(categoryList)

    }


    // https://compy.pe/galeria?pagesize=1&page=1&sort=offer&category=Computadoras&subcategory=Consolas
    override fun getSortKeys(category: String, subCategory: String) = flow {

        val finalSortKeys = if (sortKeys.isEmpty()) {
            val doc = Jsoup.parse(
                api.getProducts(category = category, subcategory = subCategory).body()!!
            )
            val inputElements = doc.select("form[data-filter-by-sort_form] input[type=radio]")
            println("Testing getSortKeys $inputElements")
            val inputLabelMap: MutableMap<String, String> = HashMap()

            for (inputElement in inputElements) {
                val value = inputElement.attr("value")
                val label = doc.select("label[for=" + inputElement.attr("id") + "]").text()
                inputLabelMap[value] = label
            }
            println("Testing inputLabelMap $inputLabelMap")
            inputLabelMap
        } else {
            sortKeys
        }

        sortKeys.putAll(finalSortKeys)
        println("getSortKeys :: $sortKeys")
        println("getSortKeys :: ${sortKeys.isEmpty()}")
        println("getSortKeys :: ${sortKeys.toMap().toList()}")

        emit(sortKeys.toMap().toList())
    }

    override fun getProducts(
        category: String,
        subCategory: String,
        page: Int,
        sort: String
    ): Flow<List<Product>> = flow {
        println("getProducts Repo init 0" )

        val doc = Jsoup.parse(
            api.getProducts(
                category = category,
                subcategory = subCategory,
                sort = sort,
                page = page
            ).body()!!
        )
        println("getProducts Repo init" )

        val products = getProducts(doc)
        println("getProducts Repo $products" )
        emit(products)
    }

}

val sortKeys: MutableMap<String, String> = mutableMapOf()


fun main() {
    val doc =
        Jsoup.connect("https://compy.pe/galeria?pagesize=1&page=1&sort=offer&category=Computadoras&subcategory=Consolas")
            .get()
    val inputElements = doc.select("form[data-filter-by-sort_form] input[type=radio]")
    val inputLabelMap: MutableMap<String, String> = HashMap()

    for (inputElement in inputElements) {
        val value = inputElement.attr("value")
        val label = doc.select("label[for=" + inputElement.attr("id") + "]").text()
        inputLabelMap[value] = label
    }

    // Imprimir el mapa

    // Imprimir el mapa

    inputLabelMap.forEach {
        println("Key: ${it.key}, Value: ${it.value}")
    }
}