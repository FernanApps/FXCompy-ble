package pe.fernan.apps.compyble.domain.model

import java.io.Serializable
import kotlin.Pair



data class Category(
    val category: String,
    val subcategories: List<String>? = null
)