package pe.fernan.apps.compyble.ui.screen.products

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ehsanmsz.mszprogressindicator.progressindicator.BallClipRotatePulseProgressIndicator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import pe.fernan.apps.compyble.R
import pe.fernan.apps.compyble.ui.composables.HeaderTitle
import pe.fernan.apps.compyble.ui.composables.PageLoader
import pe.fernan.apps.compyble.ui.composables.SegmentedControlLazy
import pe.fernan.apps.compyble.ui.navigation.Screen
import pe.fernan.apps.compyble.ui.screen.home.ProductCard
import pe.fernan.apps.compyble.ui.theme.FXCompybleTheme
import pe.fernan.apps.compyble.utils.Path


// https://compy.pe/galeria?pagesize=24&page=1&sort=offer&category=Computadoras&subcategory=Consolas
@Composable
fun ProductScreen(
    paths: List<Path>,
    navController: NavHostController,
    viewModel: ProductsViewModel = hiltViewModel()
) {

    //viewModel.setInitial(category, subcategory)
    viewModel.setPaths(paths)


    val listState = rememberLazyGridState()


    val sortKeys = viewModel.sortKeys

    val products = viewModel.products

    val page = viewModel.page


    val loading by viewModel.loading



    LaunchedEffect(key1 = viewModel.products) {
        println("ProductScreen ::: products ${products.toList()}")
    }

    viewModel.getSortKeys()



    if (!sortKeys.isNullOrEmpty() && !products.isNullOrEmpty()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp, vertical = 25.dp),
        ) {
            HeaderTitle(title = stringResource(id = R.string.shop))
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = stringResource(id = R.string.order_by))
            Spacer(modifier = Modifier.height(5.dp))


            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                SegmentedControlLazy(
                    items = sortKeys.toMap(),
                    modifier = Modifier.weight(1f),
                    defaultSelectedItemIndex = 0,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 12.sp
                ) { item ->
                    println("Selected :: $item")
                    val isOldSorSelectedEquals = viewModel.checkIfEqualsSortSelected(item.first)
                    viewModel.setSortSelected(item)
                    viewModel.setSortKeyLoading(true)
                    viewModel.getProducts(isOldSorSelectedEquals)
                }
                if (viewModel.sortKeySelectedLoading.value) {
                    Spacer(modifier = Modifier.width(8.dp))
                    BallClipRotatePulseProgressIndicator()
                }

            }


            Spacer(modifier = Modifier.height(15.dp))
            LazyVerticalGrid(
                state = listState,
                columns = GridCells.Fixed(2)) {
                items(products) { product ->
                    ProductCard(product = product, modifier = Modifier.weight(1f)) {
                        navController.navigate(Screen.Details.pass(it))
                    }
                }
            }

            LaunchedEffect(listState) {
                snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                    .collectLatest { index ->
                        if (!loading && index != null && index >= products.size - 5) {
                            viewModel.loadNextPage()
                        }
                    }
            }


        }
    } else {
        PageLoader()
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PrevLazyColumnWithPagination() {
    val page = remember { mutableStateOf(1) }
    val loading = remember { mutableStateOf(false) }
    val itemList = remember { mutableStateListOf<String>() }
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(itemList) { item ->
            Text(text = item, modifier = Modifier.padding(10.dp))
        }

        item {
            if (loading.value) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(50.dp), strokeWidth = 2.dp)
                }
            }
        }
    }

    LaunchedEffect(key1 = page.value) {
        loading.value = true
        delay(2000) // Simulate a network delay
        itemList.addAll(generateFakeData(page.value))
        loading.value = false
    }

    // Observe scroll state to load more items
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collectLatest { index ->
                if (!loading.value && index != null && index >= itemList.size - 5) {
                    page.value++
                }
            }
    }
}

fun generateFakeData(page: Int): List<String> {
    return List(20) { "Item ${(page - 1) * 20 + it + 1}" }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PrevSegmentControl() {

    val modelItems = listOf(
        "art",
        "drawing",
        "photo",
        "none",
        "art",
        "drawing",
        "photo",
        "none",
        "art",
        "drawing",
        "photo",
        "none"
    ).map {
        it to it
    }.toMap()

    var modelType by remember {
        mutableStateOf("art")
    }

    FXCompybleTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            SegmentedControlLazy(
                items = modelItems,
                modifier = Modifier.fillMaxWidth(),
                defaultSelectedItemIndex = 0,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 12.sp
            ) { type ->
                println("type ::::: $type")
                //modelType = modelItems[type]
            }
        }
    }
}