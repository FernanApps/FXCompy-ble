package pe.fernan.apps.compyble.domain.model

import java.io.Serializable


data class Details(

    val product: Product? = null,
    // Url to Original Shop -> Example PlazaVea
    // COMPRAR EN SHOPSTAR | URL
    /**
     * LABEL | URL
     */
    val hrefUrlMain: Pair<String, String>,
    // Sometimes the previous price or a label is shown
    val label: String,
    val shops: List<Shop>,
    /*
    Tipo de Producto        Electro
    Vendido por             plazaVea
    Descuentos exclusivos   sin descuentos
     */
    val specifications: List<Pair<String, String>>,
    val priceHistory: List<Day>
)

data class Shop(
    val name: String,
    val href: String,
    val logo: String,
    val priceOnline: String,
    val priceWithCard: String
)

data class Day(
    val days: Int,
    val history: List<Price>
)

data class Price(
    val label: String,
    val bestPrice: Int
)

data class Product(
    val title: String,
    val brand: String,
    val description: String,
    val imageUrl: String,
    val discount: String,
    val price: String,
    val currency: String,
    val href: String
    
): Serializable{
    override fun toString(): String {
        return "Product(title='$title', brand='$brand', description='$description', imageUrl='$imageUrl', discount='$discount', price='$price', href='$href')"
    }

    val priceFormat = when(currency){
        "PEN" -> "S/"
        else -> "$"
    } + " $price"
}