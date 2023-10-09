package pe.fernan.apps.compyble.ui.screen.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import pe.fernan.apps.compyble.R
import pe.fernan.apps.compyble.ui.composables.HeaderTitle
import pe.fernan.apps.compyble.ui.composables.PageLoader
import pe.fernan.apps.compyble.ui.navigation.Screen
import pe.fernan.apps.compyble.ui.screen.home.ProductCard


@Composable
fun FavoriteScreen(
    navController: NavHostController,
    viewModel: FavoriteViewModel = hiltViewModel()
) {

    val listState = rememberLazyGridState()

    //val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val products by viewModel.products.collectAsStateWithLifecycle(initialValue = emptyList())

    if (products.isNotEmpty()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp, vertical = 25.dp),
        ) {
            HeaderTitle(title = stringResource(id = R.string.favorite))

            Spacer(modifier = Modifier.height(15.dp))
            LazyVerticalGrid(
                state = listState,
                columns = GridCells.Fixed(2)
            ) {
                items(products) { product ->
                    ProductCard(state = product, modifier = Modifier.weight(1f), onItemClick = {
                        navController.navigate(Screen.Details.pass(it))
                    }, onFavClick = {
                        viewModel.deleteFavorite(it)
                    })
                }
            }

        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Text(text = stringResource(id = R.string.there_no_data))
        }
    }


}