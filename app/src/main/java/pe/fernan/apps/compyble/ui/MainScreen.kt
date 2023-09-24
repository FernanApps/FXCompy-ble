package pe.fernan.apps.compyble.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pe.fernan.apps.compyble.ui.navigation.CATEGORY_ARGUMENT_KEY
import pe.fernan.apps.compyble.ui.navigation.PRODUCTS_DETAILS_ARGUMENT_KEY
import pe.fernan.apps.compyble.ui.navigation.ProductNavType
import pe.fernan.apps.compyble.ui.navigation.SUB_CATEGORY_ARGUMENT_KEY
import pe.fernan.apps.compyble.ui.screen.favorite.FavoriteScreen
import pe.fernan.apps.compyble.ui.screen.home.HomeScreen
import pe.fernan.apps.compyble.ui.screen.category.CategoryScreen
import pe.fernan.apps.compyble.ui.navigation.Screen
import pe.fernan.apps.compyble.ui.navigation.bottomNavItems
import pe.fernan.apps.compyble.ui.screen.details.DetailsScreen
import pe.fernan.apps.compyble.ui.screen.offers.OffersScreen
import pe.fernan.apps.compyble.ui.screen.products.ProductScreen
import pe.fernan.apps.compyble.ui.theme.FXCompybleTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            //BottomNavigation(navController = navController)
            BottomNavigation(navController)
        },
        content = {
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route
            ) {
                composable(route = Screen.Home.route) { HomeScreen(navController) }
                composable(route = Screen.Category.route) { CategoryScreen(navController) }
                composable(route = Screen.Offers.route) { OffersScreen(navController) }
                composable(route = Screen.Favorite.route) { FavoriteScreen(navController) }
                composable(
                    route = Screen.Products.route,
                    arguments = listOf(
                        navArgument(CATEGORY_ARGUMENT_KEY) {
                            type = NavType.StringType
                        },
                        navArgument(SUB_CATEGORY_ARGUMENT_KEY) {
                            type = NavType.StringType
                        }
                    )
                ) { backStackEntry ->
                    val category = backStackEntry.arguments?.getString(CATEGORY_ARGUMENT_KEY)!!
                    val subCategory =
                        backStackEntry.arguments?.getString(SUB_CATEGORY_ARGUMENT_KEY)!!
                    ProductScreen(category, subCategory, navController)
                }
                composable(
                    route = Screen.Details.route,
                    arguments = listOf(
                        navArgument(PRODUCTS_DETAILS_ARGUMENT_KEY) {
                            type = ProductNavType
                        }
                    )
                ) { backStackEntry ->
                    Screen.Details.get(backStackEntry.arguments)?.let { product ->
                        DetailsScreen(product, navController)
                    }
                }
            }
        }
    )
}


@Composable
fun BottomNavigation(navController: NavHostController) {


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    if (currentDestination?.route in bottomNavItems.map { item -> item.route }
    ) {
        NavigationBar(
            containerColor = Color.White,
            tonalElevation = 8.dp,
        ) {

            bottomNavItems.forEach { screen ->
                if (currentDestination != null) {
                    NavigationBarItem(

                        // The icon resource
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = stringResource(id = screen.title)
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(id = screen.title),
                                color = if (currentDestination.hierarchy.any {
                                        it.route == screen.route
                                    })
                                    MaterialTheme.colorScheme.primary else Color.Black,
                                textAlign = TextAlign.Start
                            )
                        },
                        selected = currentDestination.hierarchy.any { it.route == screen.route },
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        alwaysShowLabel = true,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = Color.Black,
                        ),
                        //modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
    } else {
        Spacer(modifier = Modifier.width(0.dp))
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    FXCompybleTheme {
        val navController = rememberNavController()
        MainScreen(navController)
    }
}