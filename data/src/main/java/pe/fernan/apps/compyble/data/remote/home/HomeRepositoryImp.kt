package pe.fernan.apps.compyble.data.remote.home

import kotlinx.coroutines.flow.flow
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import pe.fernan.apps.compyble.data.remote.CompyApi
import pe.fernan.apps.compyble.domain.model.Advertisement
import pe.fernan.apps.compyble.domain.model.Data
import pe.fernan.apps.compyble.domain.model.Popup
import pe.fernan.apps.compyble.domain.model.Product
import pe.fernan.apps.compyble.domain.model.Slider
import pe.fernan.apps.compyble.domain.repository.HomeRepository


private val banner = "https://compy.pe/img/thumbnail/nano-menu_diasoh-rp-mobile.png"

private val popup = Popup(
    href = "https://compy.pe/galeria?pagesize=24&page=1&sort=save&topstore=Shopstar&prices=199-10000&tags=Tecnolog%C3%ADa&utm_campaign_store=powersale-sp-shopstar",
    imageUrl = "https://compy.pe/img/banner/powersale-shopstar-banner.webp"
)

class HomeRepositoryImp(private val api: CompyApi) : HomeRepository {
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

        val selector = "div[class=card-2 c-2-1]"

        fun getProduct(element: Element?) = Product(
            brand = element?.attr("data-product-brand") ?: "",
            title = element?.attr("data-product-name") ?: "",
            description = "",
            price = element?.attr("data-product-price") ?: "",
            currency = element?.attr("data-product-currency") ?: "",
            imageUrl = element?.select("picture>img")?.attr("src") ?: "",
            discount = element?.select("p")?.first()?.select("small")?.text() ?: "",
            href = element?.attr("href") ?: "",
        )

        val productsTop = doc.select(selector).map {
            val elementA = it.select("a").first()
            getProduct(elementA)
        }

        val productsTopTitle =
            doc.select(selector).parents().first()?.select("header>h4")?.first()?.text()
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

}

fun main() {


    val doc = Jsoup.connect("https://compy.pe/").get()
    val advertisements = doc.select("section[class^=section-2]").select("div.image-block").map {
        Advertisement(
            imageUrl = it.select("img").attr("src"),
            href = it.select("a").first()?.attr("href") ?: ""
        )
    }

    advertisements.forEach {
        println(it.toString())
    }

}