package pe.fernan.apps.compyble.ui.navigation

import android.net.Uri
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.google.gson.Gson
import pe.fernan.apps.compyble.R
import pe.fernan.apps.compyble.domain.model.Product
import java.io.File.separator

sealed class Screen(
    private val baseRoute: String,
    val argKeys: List<String> = emptyList(),
    @StringRes val title: Int = R.string.app_name,
    val icon: ImageVector,
) {
    private val separator = "/"
    private val stringFormat = "%s"
    private val keysFormat = "{$stringFormat}"

    val routeFormat: String
        get() = if (argKeys.isEmpty()) throw Exception("The format does not exist since there are no ArgKeys") else {
            val builder = argKeys.joinToString(
                prefix = separator,
                separator = separator
            ) { stringFormat }
            baseRoute + builder
        }

    val route: String
        get() = if (argKeys.isEmpty()) baseRoute else {
            val builder = argKeys.joinToString(
                prefix = separator,
                separator = separator
            ) { keysFormat.format(it) }
            baseRoute + builder
        }

    object Home : Screen("home", title = R.string.home, icon = Icons.Default.Home)
    object Category : Screen("category", title = R.string.category, icon = Icons.Default.Lock)
    object Offers : Screen("offers", title = R.string.offers, icon = Icons.Default.Star)
    object Favorite : Screen("favorite", title = R.string.favorite, icon = Icons.Default.Favorite)
    object Products : Screen(
        "products",
        argKeys = listOf(CATEGORY_ARGUMENT_KEY, SUB_CATEGORY_ARGUMENT_KEY),
        title = R.string.products,
        icon = Icons.Default.Favorite
    ) {
        fun pass(category: String, subcategory: String) = routeFormat.format(category, subcategory)
    }

    object Details : Screen(
        "product",
        argKeys = listOf(PRODUCTS_DETAILS_ARGUMENT_KEY),
        title = R.string.products,
        icon = Icons.Default.Favorite
    ) {
        fun pass(product: Product): String {
            val json = Uri.encode(Gson().toJson(product))
            return routeFormat.format(json)
        }

        fun get(arguments: Bundle?): Product? {
            val productArguments = arguments?.let {
                ProductNavType[it, PRODUCTS_DETAILS_ARGUMENT_KEY]
            }
            return productArguments

        }
    }

}

val ProductNavType = ObjectNavType(Product::class.java)

const val CATEGORY_ARGUMENT_KEY = "category"
const val SUB_CATEGORY_ARGUMENT_KEY = "subcategory"

const val PRODUCTS_DETAILS_ARGUMENT_KEY = "subcategory"


val bottomNavItems = listOf(
    Screen.Home,
    Screen.Category,
    Screen.Offers,
    Screen.Favorite
)

