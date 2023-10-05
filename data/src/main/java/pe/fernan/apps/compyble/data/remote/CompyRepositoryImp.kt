package pe.fernan.apps.compyble.data.remote

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.Uri
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import pe.fernan.apps.compyble.domain.model.Advertisement
import pe.fernan.apps.compyble.domain.model.Banner
import pe.fernan.apps.compyble.domain.model.Category
import pe.fernan.apps.compyble.domain.model.Data
import pe.fernan.apps.compyble.domain.model.Day
import pe.fernan.apps.compyble.domain.model.Details
import pe.fernan.apps.compyble.domain.model.Popup
import pe.fernan.apps.compyble.domain.model.Price
import pe.fernan.apps.compyble.domain.model.Product
import pe.fernan.apps.compyble.domain.model.Shop
import pe.fernan.apps.compyble.domain.model.Slider
import pe.fernan.apps.compyble.domain.repository.CompyRepository
import java.net.URI


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


        // ´´´ Pop

        val banner: Banner? = doc.select("div[class=cintillo hidden-desktop]").first()?.let {
            val elementA = it.select("a[href]")
            val href = elementA.attr("href")
            val image = elementA.select("img").attr("src")
            if (!href.isNullOrEmpty() && !image.isNullOrEmpty()) {
                Banner(href, image)
            } else {
                null
            }
        }


        val dialogGame = doc.getElementById("dialog-game")
        val popHref = dialogGame?.select("a")?.first()?.attr("href")
        val popImage = dialogGame?.select("img")?.attr("src")

        val popUp = if (!popImage.isNullOrEmpty() && !popHref.isNullOrEmpty()) {
            Popup(
                href = popHref,
                imageUrl = popImage
            )
        } else {
            null
        }

        emit(
            Data(
                banner = banner,
                popup = popUp,
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


    private fun checkPath(paths: Map<String, String>): MutableMap<String, String> {
        val pathsMap = paths.toMutableMap()
        // Checking
        if(!pathsMap.containsKey("pagesize")){
            pathsMap["pagesize"] = "24"
        }

        if(!pathsMap.containsKey("page")){
            pathsMap["page"] = "1"
        }

        if(!pathsMap.containsKey("sort")){
            pathsMap["sort"] = "offer"
        }

        return pathsMap
    }

    // https://compy.pe/galeria?pagesize=1&page=1&sort=offer&category=Computadoras&subcategory=Consolas
    override fun getSortKeys(paths: Map<String, String>) = flow {

        val pathsMap = checkPath(paths)

        val finalSortKeys = if (sortKeys.isEmpty()) {
            val doc = Jsoup.parse(
                api.getProducts(pathsMap).body()!!
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
        paths: Map<String, String>
    ): Flow<List<Product>> = flow {

        paths.forEach {
            println("getProducts ::: ${it.key} = ${it.value}")
        }

        val pathsMap = checkPath(paths)

        println("getProducts Repo init 0")

        val doc = Jsoup.parse(
            api.getProducts(pathsMap).body()!!
        )
        println("getProducts Repo init")

        val products = getProducts(doc)
        println("getProducts Repo $products")
        emit(products)
    }

    override fun getDetails(path: String): Flow<Details> = flow {


        // Fixing if start /path :
        val decodePath = path.replace("^/".toRegex(), "")
        val doc = Jsoup.parse(
            api.getDetail(
                path = decodePath
            ).body()!!
        )

        // Products If From Dialog :x
        val imageUrl = doc.select("meta[property=og:image]").attr("content")
        val description = doc.select("meta[property=og:description]").attr("content")
        val title =  doc.select("meta[property=og:title]").attr("content")
        val price = doc.select("div.c21c-price>p").first()?.text() ?: ""

        val product = Product(
            brand = "",
            title = title,
            description = description,
            imageUrl = imageUrl,
            discount = "",
            currency = "",
            href = "",
            price = price
        )
        val priceHistory = doc.select("script[data-chart_days]").map {
            val days = it.attr("data-chart_days").toInt()

            val type = object : TypeToken<List<Price>>() {}.type
            val jsonHtml = it.html()
            val prices = Gson().fromJson<List<Price>>(jsonHtml, type)
            Day(days = days, prices)
        }

        val label = doc.select("div.c21c-price>p").last()?.text() ?: ""

        val hrefUrlMain = doc.select("div.c21c-actions").select("a").first().let {
            val href = it?.attr("href")
            val shopLabel = it?.select("span")?.text()
            return@let if (href != null && shopLabel != null) {
                shopLabel to href
            } else {
                "" to ""
            }
        }

        val shops = doc.select("div.t-body>a").mapNotNull {
            if (it == null) {
                return@mapNotNull null
            }
            val href = it.attr("href")
            val name = it.attr("data-store")
            val logo = it.select("picture>img").attr("src") ?: ""

            var priceOnline = ""
            var priceWithCard = ""

            val divElement: Element? = it.select("div.card-19").first()
            if (divElement != null) {
                val divText = divElement.ownText().trim()
                priceOnline = divText
                // Esto imprimirá "S/.1,349"
            }

            val spanElement: Element? = it.select("span.card-19").last()
            if (spanElement != null) {
                val spanText = spanElement.ownText().trim()
                priceWithCard = spanText
                // Esto imprimirá "S/.1,309"
            }


            Shop(
                name = name,
                logo = logo,
                href = href,
                priceOnline = priceOnline,
                priceWithCard = priceWithCard
            )
        }

        val specifications = doc.select("div.c21e-content>p").mapNotNull {
            val smalls = it.select("small")
            return@mapNotNull if (smalls.size > 1) {
                smalls.first()!!.text() to smalls.last()!!.text()
            } else {
                null
            }
        }

        val details = Details(
            product = product,
            hrefUrlMain = hrefUrlMain,
            label = label,
            shops = shops,
            specifications = specifications,
            priceHistory = priceHistory
        )

        emit(details)
    }

}

val sortKeys: MutableMap<String, String> = mutableMapOf()


fun main() {
    val doc =
        Jsoup.connect("https://compy.pe/galeria/producto/65168e8cf0ffaae7a32f6d84/lavadora-oster-os-pwsmk0014b-14kg-negro?utm_campaign_store=P-031023-061023-oechsle")
            .get()

    val price = doc.select("div.c21c-price>p").first()?.text() ?: ""
    println(price)

}
