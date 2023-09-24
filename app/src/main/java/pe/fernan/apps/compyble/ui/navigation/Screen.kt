package pe.fernan.apps.compyble.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import pe.fernan.apps.compyble.R

sealed class Screen(
    val route: String,
    @StringRes val title: Int = R.string.app_name,
    val icon: ImageVector,
) {
    object Home : Screen("home", R.string.home, Icons.Default.Home)
    object Category : Screen("category", R.string.category, Icons.Default.Lock)
    object Offers : Screen("offers", R.string.offers, Icons.Default.Star)
    object Favorite : Screen("favorite", R.string.favorite, Icons.Default.Favorite)
    object Products : Screen("products/{$CATEGORY_ARGUMENT_KEY}/{$SUB_CATEGORY_ARGUMENT_KEY}", R.string.products, Icons.Default.Favorite) {
        fun pass(category: String, subcategory: String) = "products/$category/$subcategory"
    }

}

const val CATEGORY_ARGUMENT_KEY = "category"
const val SUB_CATEGORY_ARGUMENT_KEY = "subcategory"



val bottomNavItems = listOf(
    Screen.Home,
    Screen.Category,
    Screen.Offers,
    Screen.Favorite
)