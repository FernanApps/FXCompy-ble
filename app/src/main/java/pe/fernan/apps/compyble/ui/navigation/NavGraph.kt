package pe.fernan.apps.compyble.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController


@Composable
fun RowScope.AddItem(
    screen: Screen,
    onClick: (Screen) -> Unit
) {
    NavigationBarItem(
        // Text that shows bellow the icon
        label = {
            Text(text = stringResource(id = screen.title))
        },

        // The icon resource
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = stringResource(id = screen.title)
            )
        },

        // Display if the icon it is select or not
        selected = true,

        // Always show the label bellow the icon or not
        alwaysShowLabel = true,

        // Click listener for the icon
        onClick = { onClick(screen) },

        // Control all the colors of the icon
        colors = NavigationBarItemDefaults.colors()
    )
}





@Composable
fun ScreenTwo(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Screen Two")
        Button(
            onClick = {
                navController.navigate(Screen.Home.route)
            }
        ) {
            Text(text = "Next Screen")
        }
    }
}
