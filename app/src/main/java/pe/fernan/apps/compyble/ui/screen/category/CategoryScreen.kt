package pe.fernan.apps.compyble.ui.screen.category

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import pe.fernan.apps.compyble.R
import pe.fernan.apps.compyble.domain.model.Category
import pe.fernan.apps.compyble.ui.composables.HeaderTitle
import pe.fernan.apps.compyble.ui.composables.PageLoader
import pe.fernan.apps.compyble.ui.composables.bounceClick
import pe.fernan.apps.compyble.ui.composables.pressClickEffect
import pe.fernan.apps.compyble.ui.navigation.Screen
import pe.fernan.apps.compyble.ui.theme.FXCompybleTheme
import pe.fernan.apps.compyble.utils.warmColors
import pe.fernan.apps.compyble.utils.warmGradients
import kotlin.random.Random

@Composable
fun CategoryScreen(
    navController: NavHostController,
    viewModel: CategoryViewModel = hiltViewModel()
) {

    val categories: List<Category>? by viewModel.getCategories.collectAsStateWithLifecycle(null)

    var categorieSelected: Category? by remember { mutableStateOf(null) }

    var posSelected: Int? by remember { mutableStateOf(null) }


    if (!categories.isNullOrEmpty()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp, vertical = 25.dp),
        ) {
            HeaderTitle(
                title = stringResource(id = R.string.category),
                style = MaterialTheme.typography.headlineMedium
            )

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(count = 3), // 3 columns
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(all = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                verticalItemSpacing = 8.dp
            ) {
                itemsIndexed(categories!!.map {
                    val sizeHeight = Random.nextInt(100, 200).dp
                    it to sizeHeight
                }) { index, item ->

                    var selected by remember { mutableStateOf(false) }
                    val scale = animateFloatAsState(if (selected) 0.90f else 1f)

                    LaunchedEffect(key1 = posSelected) {
                        if (posSelected != null) {
                            selected = posSelected == index
                        }
                    }

                    val warmBrushColor = warmGradients.random()
                    val roundedCornerShape = RoundedCornerShape(size = 10.dp)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(item.second)
                            .border(1.dp, warmBrushColor, roundedCornerShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .graphicsLayer {
                                    scaleX = scale.value
                                    scaleY = scale.value
                                }
                                .fillMaxSize()
                                .background(
                                    brush = warmBrushColor,
                                    shape = RoundedCornerShape(size = 10.dp)
                                )
                                .pressClickEffect {
                                    selected = !selected
                                    posSelected = index

                                    categorieSelected = if (selected) {
                                        categories?.getOrNull(posSelected!!)
                                    } else {
                                        null
                                    }

                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = item.first.category,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }


                }
            }

            if (categorieSelected != null) {
                LazyRow(Modifier.fillMaxWidth()) {
                    items(categorieSelected?.subcategories ?: listOf()) { item ->
                        val warmColor = warmColors.random()
                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .background(
                                    color = warmColor,
                                    shape = RoundedCornerShape(size = 5.dp)
                                )
                                .bounceClick {
                                    navController.navigate(
                                        route = Screen.Products.pass(
                                            categorieSelected!!.category,
                                            item
                                        )
                                    )
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 15.dp),
                                text = item,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(5.dp))

                    }
                }
            }

            Spacer(modifier = Modifier.size(100.dp))


        }

    } else {
        PageLoader()
    }

}


@OptIn(ExperimentalComposeUiApi::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AnimatedButton() {
    var selected by remember { mutableStateOf(false) }
    val scale = animateFloatAsState(if (selected) 0.70f else 1f)

    Column(
        Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { selected = !selected },
            modifier = Modifier
                .graphicsLayer {
                    scaleX = scale.value
                    scaleY = scale.value
                }
                //.scale(scale.value)
                .height(40.dp)
                .width(200.dp)
        ) {
            Text(text = "Explore Airbnb", fontSize = 15.sp, color = Color.White)
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MyComposable() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Header",
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(50) { index ->
                Text(
                    text = "Item $index",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }

        Text(
            text = "Footer",
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}


@Composable
fun MyComposable2() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (header, lazyColumn, footer) = createRefs()

        Text(
            text = "Header",
            fontSize = 24.sp,
            modifier = Modifier
                .constrainAs(header) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
        )
        Text(
            text = "Footer",
            fontSize = 24.sp,
            modifier = Modifier
                .constrainAs(footer) {
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
        )
        LazyColumn(
            modifier = Modifier
                .constrainAs(lazyColumn) {
                    top.linkTo(header.bottom, margin = 16.dp)
                    bottom.linkTo(footer.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
        ) {
            items(50) { index ->
                Text(
                    text = "Item $index",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }


    }
}

@Preview
@Composable
fun MyComposablePreview() {
    FXCompybleTheme {
        Surface {
            MyComposable2()
        }
    }
}