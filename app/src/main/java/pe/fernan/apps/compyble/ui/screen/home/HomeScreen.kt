package pe.fernan.apps.compyble.ui.screen.home

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.ehsanmsz.mszprogressindicator.progressindicator.BallClipRotateMultipleProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.BallScaleRippleMultipleProgressIndicator
import pe.fernan.apps.compyble.R
import pe.fernan.apps.compyble.data.remote.home.HomeRepositoryImp
import pe.fernan.apps.compyble.di.NetworkModule
import pe.fernan.apps.compyble.domain.model.Advertisement
import pe.fernan.apps.compyble.domain.model.Data
import pe.fernan.apps.compyble.domain.model.Product
import pe.fernan.apps.compyble.domain.model.Slider
import pe.fernan.apps.compyble.domain.useCase.GetHomeDataUseCase
import pe.fernan.apps.compyble.ui.composables.bounceClick
import pe.fernan.apps.compyble.ui.theme.FXCompybleTheme
import pe.fernan.apps.compyble.utils.fixImage
import pe.fernan.apps.compyble.utils.getColorByNameOrDefault


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {

    val data: Data? by viewModel.getData.collectAsStateWithLifecycle(null)

    val infoDialog = remember { mutableStateOf(true) }

    if (data != null) {

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

                if (data?.banner?.isNotEmpty() == true) {
                    AsyncImage(
                        model = data!!.banner,
                        contentDescription = null,
                    )
                    Spacer(Modifier.size(15.dp))
                }
                Sliders(data?.sliders ?: listOf())
                Advertisements(data?.advertisements ?: listOf())
            }

            // TopProducts()
            if (data?.productCategories?.isNotEmpty() == true) {
                val firstElement = data?.productCategories?.firstOrNull()
                if (firstElement != null) {
                    item {
                        Spacer(Modifier.size(15.dp))
                        HeaderTitle(title = firstElement.first)

                    }

                    items(firstElement.second.windowed(2, 2, true)) { subList ->
                        Row(Modifier.fillMaxWidth()) {
                            subList.forEach { product ->
                                ProductCard(product = product, modifier = Modifier.weight(1f)) {

                                }
                            }
                        }

                    }
                }

                if (data!!.productCategories.size >= 2) {
                    val lastList = data!!.productCategories.drop(1)
                    items(lastList) {
                        CategoryProduct(it)
                        Spacer(modifier = Modifier.size(15.dp))
                    }

                    item {
                        Spacer(modifier = Modifier.size(45.dp))
                    }

                }


            }


        }


        if (infoDialog.value) {
            if (data?.popup != null) {

                Dialog(onDismissRequest = {

                }) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .bounceClick(0.85f) {
                                infoDialog.value = false
                            },
                        contentAlignment = Alignment.Center,
                    ) {

                        AsyncImage(
                            model = data!!.popup!!.imageUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(15.dp))
                                .background(Color.White),
                            contentScale = ContentScale.FillBounds
                        )

                        /*
                        AsyncImage(
                            model = data!!.popup!!.imageUrl,
                            modifier = Modifier.fillMaxSize()
                                .clip(RoundedCornerShape(50.dp)
                                ),
                            contentDescription = null,
                        )
                        */
                    }
                }
            }

        }

    } else {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            BallClipRotateMultipleProgressIndicator(
                modifier = Modifier.size(100.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }


}


@Composable
fun HeaderTitle(title: String) {
    val formattedTitle = title.take(1).uppercase() + title.lowercase().substring(1)
    Text(
        text = formattedTitle,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp),
        textAlign = TextAlign.Start
    )
}


@Composable
fun CategoryProduct(pair: Pair<String, List<Product>>) {
    HeaderTitle(title = pair.first)
    Spacer(modifier = Modifier.height(10.dp))
    LazyRow(
        Modifier
            .fillMaxWidth()
            .padding(start = 15.dp)
    ) {
        items(pair.second) { product ->
            Column(
                Modifier.width(150.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(CircleShape)
                        .bounceClick()
                        .border(width = 0.4.dp, Color.Black, CircleShape)
                        .background(Color.White)
                        .padding(15.dp),
                    contentScale = ContentScale.Inside
                )
                Spacer(modifier = Modifier.size(5.dp))
                ProductInformation(product)
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}


@Composable
fun ProductCard(product: Product, modifier: Modifier, onItemClick: () -> Unit) {

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = product.discount,
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier
                .background(color = Color.Red, shape = RoundedCornerShape(20.dp))
                .align(Alignment.End)
                .padding(horizontal = 12.5.dp, vertical = 2.5.dp)
        )
        Box(modifier = Modifier
            .padding(0.dp)
            .bounceClick()) {


            AsyncImage(
                model = product.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    //.size(250.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(15.dp))
                    .border(width = 0.4.dp, Color.Black, RoundedCornerShape(15.dp))
                    .background(Color.White)
                    .padding(4.dp),
                contentScale = ContentScale.Crop
            )

            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
            )

        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier.fillMaxWidth(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ProductInformation(product)

        }

        Spacer(modifier = Modifier.width(16.dp))


    }

}

@Composable
fun ProductInformation(product: Product) {
    Text(
        text = product.brand.uppercase(),
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color.LightGray
    )
    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = product.title,
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.SemiBold,
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = product.priceFormat,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
    )
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
        model = advertisement.imageUrl.fixImage(),
        contentDescription = null,
        modifier = Modifier
            .width(220.dp)
            .height(100.dp)
            .aspectRatio(3f / 1.7f)
            .clip(RoundedCornerShape(20.dp)),
        contentScale = ContentScale.FillBounds
    )

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Sliders(sliders: List<Slider>) {

    if (sliders.isEmpty()) return

    val state = rememberPagerState { sliders.size - 1 }

    val colorPrimary = MaterialTheme.colorScheme.primary

    HorizontalPager(
        state = state,
        modifier = Modifier.fillMaxWidth(0.7f),
    ) { page ->
        val slider = sliders[page]
        val backgroundColor = getColorByNameOrDefault(slider.backgroundColor) ?: colorPrimary
        val buttonColor = getColorByNameOrDefault(slider.buttonColor) ?: colorPrimary
        Column(
            modifier = Modifier
                .padding(25.dp)
                .background(
                    backgroundColor,
                    RoundedCornerShape(25.dp)
                )
                .fillMaxWidth()
                .aspectRatio(1f / 1.5f),
        ) {

            Text(
                text = slider.title,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier.padding(25.dp)
            )
            Button(
                modifier = Modifier.padding(start = 25.dp),
                onClick = { }, shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor
                )
            ) {
                Text(
                    text = slider.description,
                    color = if (buttonColor == Color.White) Color.Black else Color.White
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = slider.image.fixImage(),
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

    val interceptor = NetworkModule.provideLoggingInterceptor()
    val okHttpClient = NetworkModule.provideHttpClient(interceptor)
    val retrofit = NetworkModule.provideRetrofitInstance(okHttpClient)
    val api = NetworkModule.provideMovieApi(retrofit)

    val homeRepository = HomeRepositoryImp(api)
    val getHomeDataUseCase = GetHomeDataUseCase(homeRepository)
    val viewModel = HomeViewModel(getHomeDataUseCase)
    val navController = rememberNavController()

    FXCompybleTheme {
        HomeScreen(navController, viewModel)
    }


}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PrevLoading(){
    FXCompybleTheme {
        BallClipRotateMultipleProgressIndicator(
            modifier = Modifier.size(60.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}







