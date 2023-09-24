package pe.fernan.apps.compyble.ui.screen.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest


// https://compy.pe/galeria?pagesize=24&page=1&sort=offer&category=Computadoras&subcategory=Consolas
@Composable
fun ProductScreen(category: String, subcategory: String, navController: NavHostController, viewModel: ProductsViewModel = hiltViewModel()) {

    val sortKeys by viewModel.sortKeys.collectAsStateWithLifecycle(null)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "ProductScreen $category - $subcategory")

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PrevLazyColumnWithPagination(){
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
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), contentAlignment = Alignment.Center) {
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