package pe.fernan.apps.compyble.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import pe.fernan.apps.compyble.R
import pe.fernan.apps.compyble.ui.theme.FXCompybleTheme


val productTopList = listOf(

    ProductTop(
        "Apple iPhone 11 64GB 4GB Purpura",
        "Y enteráte de las últimas ofertas",
        "https://mercury.vteximg.com.br/arquivos/ids/10156232/image-f97e710146224892965f40db094c25c8.jpg?v=638024265344500000",
        "-33%",
        "S/.2,249"
    ),
    ProductTop(
        "Apple iPhone 11 64GB 4GB Purpura",
        "Y enteráte de las últimas ofertas",
        "https://mercury.vteximg.com.br/arquivos/ids/10156232/image-f97e710146224892965f40db094c25c8.jpg?v=638024265344500000",
        "-33%",
        "S/.2,249"
    ),
    ProductTop(
        "Apple iPhone 11 64GB 4GB Purpura",
        "Y enteráte de las últimas ofertas",
        "https://mercury.vteximg.com.br/arquivos/ids/10156232/image-f97e710146224892965f40db094c25c8.jpg?v=638024265344500000",
        "-33%",
        "S/.2,249"
    ),
    ProductTop(
        "Apple iPhone 11 64GB 4GB Purpura",
        "Y enteráte de las últimas ofertas",
        "https://mercury.vteximg.com.br/arquivos/ids/10156232/image-f97e710146224892965f40db094c25c8.jpg?v=638024265344500000",
        "-33%",
        "S/.2,249"
    ),
    ProductTop(
        "Apple iPhone 11 64GB 4GB Purpura",
        "Y enteráte de las últimas ofertas",
        "https://mercury.vteximg.com.br/arquivos/ids/10156232/image-f97e710146224892965f40db094c25c8.jpg?v=638024265344500000",
        "-33%",
        "S/.2,249"
    )

)

val advertisements = listOf(
    Advertisement(
        "https://compy.pe/img/thumbnail/telegram.webp"
    ),
    Advertisement(
        "https://compy.pe/img/thumbnail/minimos.webp"
    ),
    Advertisement(
        "https://compy.pe/img/thumbnail/fechascyber.webp"
    ),
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavHostController) {


    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        //.verticalScroll(rememberScrollState())
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,

        ) {

        item {
            SearchBar()
            Spacer(Modifier.size(10.dp))
            AsyncImage(
                model = "https://compy.pe/img/thumbnail/nano-menu_diasoh-rp-mobile.png",
                contentDescription = null,
            )
            Spacer(Modifier.size(15.dp))
            Sliders()
            Advertisements(advertisements)
        }

        // TopProducts()
        item {
            Text(
                text = stringResource(id = R.string.top_products),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp),
                textAlign = TextAlign.Start
            )

        }

        items(productTopList) { product ->
            ProductCard(productTop = product) {
                // Handle item click here
            }
        }


    }
}


class Advertisement(
    val imageUrl: String
)

data class ProductTop(
    val title: String,
    val description: String,
    val imageUrl: String,
    val discount: String,
    val price: String
)

@Composable
fun ProductCard(productTop: ProductTop, onItemClick: () -> Unit) {

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(modifier = Modifier.padding(15.dp)) {

            AsyncImage(
                model = productTop.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(250.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(15.dp))
                    .border(width = 0.4.dp, Color.Black, RoundedCornerShape(15.dp))
                    .background(Color.White)
                    .padding(4.dp),
                contentScale = ContentScale.Crop
            )
            Icon(
                imageVector = Icons.Outlined.Favorite,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = productTop.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = productTop.description,
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = productTop.price,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = productTop.discount,
                fontSize = 14.sp,
            )
        }

        Spacer(modifier = Modifier.width(16.dp))


    }

}


@Composable
fun Advertisements(advertisements: List<Advertisement>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        items(advertisements) { ad ->
            AdCard(advertisement = ad)
            Spacer(modifier = Modifier.width(16.dp))
        }
    }

}


@Composable
fun AdCard(advertisement: Advertisement) {

    AsyncImage(
        model = advertisement.imageUrl,
        contentDescription = null,
        modifier = Modifier
            .width(220.dp)
            .height(100.dp)
            .aspectRatio(3f / 1.8f)
            .clip(RoundedCornerShape(20.dp)),
        contentScale = ContentScale.FillBounds
    )


}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Sliders() {
    val state = rememberPagerState { 10 }

    val colorList = listOf(
        Color.Red,
        Color.Gray,
        Color.Magenta,
        Color.Black,
        Color.Blue
    )


    HorizontalPager(
        state = state,
        modifier = Modifier.fillMaxWidth(0.7f),
    ) { page ->
        Column(
            modifier = Modifier
                .padding(25.dp)
                .background(colorList.random(), RoundedCornerShape(25.dp))
                .fillMaxWidth()
                .aspectRatio(1f / 1.5f),
        ) {

            Text(
                text = "Más poder en tus manos",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier.padding(25.dp)
            )
            Button(
                modifier = Modifier.padding(start = 25.dp),
                onClick = { }, shape = RoundedCornerShape(50.dp)
            ) {
                Text(text = "Mundo Gammer")
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = "https://compy.pe/img/thumbnail/macbook.png",
                    contentDescription = null,
                )

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchBar() {
    var text by remember { mutableStateOf("") } // Query for SearchBar
    var active by remember { mutableStateOf(false) } // Active state for SearchBar
    val searchHistory = remember { mutableStateListOf("") }

    Column(modifier = Modifier.padding(horizontal = 10.dp)) {

        SearchBar(modifier = Modifier.fillMaxWidth(),
            query = text,
            onQueryChange = {
                text = it
            },
            onSearch = {
                searchHistory.add(text)
                active = false
            },
            active = active,
            onActiveChange = {
                active = it
            },
            placeholder = {
                Text(text = stringResource(id = R.string.search_in_compyble))
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search icon")
            },
            trailingIcon = {
                if (active) {
                    Icon(
                        modifier = Modifier.clickable {
                            if (text.isNotEmpty()) {
                                text = ""
                            } else {
                                active = false
                            }
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.close_search)
                    )
                }
            }
        ) {
            searchHistory.distinct().forEach {
                if (it.isNotEmpty()) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            text = it
                        }
                        .padding(all = 14.dp)) {
                        Icon(imageVector = Icons.Default.Home, contentDescription = null)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = it)
                    }
                }
            }

            Divider()
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        searchHistory.clear()
                    }
                    .padding(all = 14.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                text = stringResource(id = R.string.clear_all_history)
            )
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PrevHomeScreen() {
    FXCompybleTheme {
        val navController = rememberNavController()
        HomeScreen(navController)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PrevProductTop() {
    FXCompybleTheme {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            //.verticalScroll(rememberScrollState())
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,

            ) {
            items(productTopList) { product ->
                ProductCard(productTop = product) {

                }
            }


        }
    }
}

