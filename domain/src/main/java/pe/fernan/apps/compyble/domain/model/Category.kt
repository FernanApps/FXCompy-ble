package pe.fernan.apps.compyble.domain.model

data class Category(
    val category: String,
    val subcategories: List<String>? = null
)