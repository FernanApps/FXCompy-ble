package pe.fernan.apps.compyble.domain.model

import java.awt.Color


class Data(
    val banner: Banner?,
    val popup: Popup?,
    val sliders: List<Slider>,
    val advertisements: List<Advertisement>,
    val productCategories: List<Pair<String, List<Product>>>
)

class Banner(
    val href: String,
    val imageUrl: String
)






