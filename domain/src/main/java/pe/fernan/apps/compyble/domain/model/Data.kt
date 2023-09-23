package pe.fernan.apps.compyble.domain.model

import java.awt.Color


class Data(
    val banner: String,
    val popup: Popup?,
    val sliders: List<Slider>,
    val advertisements: List<Advertisement>,
    val productCategories: List<Pair<String, List<Product>>>
)

class Popup(
    val href: String,
    val imageUrl: String
)


class Advertisement(
    val imageUrl: String,
    val href: String
){
    override fun toString(): String {
        return "Advertisement(imageUrl='$imageUrl', href='$href')"
    }
}

data class Product(
    val title: String,
    val brand: String,
    val description: String,
    val imageUrl: String,
    val discount: String,
    val price: String,
    val currency: String,
    val href: String
){
    override fun toString(): String {
        return "Product(title='$title', brand='$brand', description='$description', imageUrl='$imageUrl', discount='$discount', price='$price', href='$href')"
    }

    val priceFormat = when(currency){
        "PEN" -> "S/"
        else -> "$"
    } + " $price"
}

data class Slider(
    val title: String,
    val description: String,
    val image: String,
    val href: String = "",
    val buttonColor: String = "",
    val backgroundColor: String = ""
){
    override fun toString(): String {
        return "Slider(title='$title', description='$description', image='$image', href='$href', buttonColor='$buttonColor', backgroundColor='$backgroundColor')"
    }
}
