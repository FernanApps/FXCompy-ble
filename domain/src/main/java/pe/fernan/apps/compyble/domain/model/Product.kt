package pe.fernan.apps.compyble.domain.model

import java.io.Serializable


data class PageProducts(
    val products: List<Product>,
    val sortsKeys: List<String>
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