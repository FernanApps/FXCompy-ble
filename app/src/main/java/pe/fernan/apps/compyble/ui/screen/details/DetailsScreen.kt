package pe.fernan.apps.compyble.ui.screen.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ehsanmsz.mszprogressindicator.progressindicator.BallScaleRippleMultipleProgressIndicator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entryOf
import pe.fernan.apps.compyble.R
import pe.fernan.apps.compyble.domain.model.Details
import pe.fernan.apps.compyble.domain.model.Price
import pe.fernan.apps.compyble.domain.model.Product
import pe.fernan.apps.compyble.ui.composables.HeaderTitle
import pe.fernan.apps.compyble.ui.composables.HorizontalAxisOnlyLineChart
import pe.fernan.apps.compyble.ui.composables.LineChartEntity
import pe.fernan.apps.compyble.ui.composables.LineChartVerticalTest
import pe.fernan.apps.compyble.ui.composables.SegmentedControl
import pe.fernan.apps.compyble.ui.composables.bounceClick
import pe.fernan.apps.compyble.ui.theme.FXCompybleTheme
import pe.fernan.apps.compyble.utils.fixImage
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@Composable
fun DetailsScreen(
    product: Product,
    navController: NavHostController,
    viewModel: DetailsViewModel = hiltViewModel()
) {

    viewModel.getDetails(product.href)

    val context = LocalContext.current

    val details: Details? by viewModel.details.collectAsStateWithLifecycle(initialValue = null)

    println("DetailsScreen :::: $product")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(android.graphics.Color.parseColor("#F5F5F7")))
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        item {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = product.brand.uppercase(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.LightGray
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = product.discount,
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .alpha(if (product.discount.isEmpty()) 0f else 1f)
                        .background(color = Color.Red, shape = RoundedCornerShape(20.dp))
                        .align(Alignment.End)
                        .padding(horizontal = 12.5.dp, vertical = 2.5.dp)
                )

                Box(
                    modifier = Modifier
                        .padding(0.dp)
                        .bounceClick {}
                ) {


                    AsyncImage(
                        // model = product.imageUrl.fixImage(),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(product.imageUrl.fixImage())
                            .crossfade(true)
                            .build(),
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


                    Text(
                        text = product.title,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row() {
                        Text(
                            text = product.priceFormat,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        if (details != null) {
                            Text(
                                text = details!!.label, Modifier
                                    .background(
                                        MaterialTheme.colorScheme.primary,
                                        RoundedCornerShape(10.dp)
                                    )
                                    .padding(horizontal = 15.dp, vertical = 8.dp),
                                fontSize = 8.sp,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold

                            )
                        }

                    }

                    if (details == null) {
                        Spacer(modifier = Modifier.height(20.dp))
                        BallScaleRippleMultipleProgressIndicator(Modifier.size(100.dp))

                    }


                    if (details?.hrefUrlMain != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            modifier = Modifier.fillMaxWidth(), onClick = {
                                val url = details?.hrefUrlMain!!.second
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                context.startActivity(intent)

                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            )) {
                            Text(text = details!!.hrefUrlMain.first)
                        }
                    }

                }
                Spacer(modifier = Modifier.height(20.dp))

                if (details?.priceHistory?.isNotEmpty() == true) {

                    var daysCurrent by remember {
                        mutableStateOf(details!!.priceHistory[0])
                    }
                    val daysNumbers =
                        details!!.priceHistory.map { "${it.days} ${stringResource(id = R.string.days)}" }


                    HeaderTitle(
                        title = stringResource(id = R.string.history_price), modifier = Modifier
                            .fillMaxWidth()
                            .padding()
                    )
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.show_in),

                            )

                        SegmentedControl(items = daysNumbers, modifier = Modifier.weight(1f)) {
                            val (daysCurrentFind) = Regex("""(\d+)""").find(daysNumbers[it])!!.destructured
                            daysCurrent =
                                details!!.priceHistory.find { price -> price.days == daysCurrentFind.toInt() }!!

                        }

                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    val priceHistory: List<Price> = daysCurrent.history

                    val maxPrice = priceHistory.maxOfOrNull { it.bestPrice }!!

                    val numberOfParts = 8
                    val partSize = maxPrice / numberOfParts

                    // Create a list to store the parts
                    val parts = mutableListOf<Int>()

                    // Fill the list with parts
                    for (i in 1..numberOfParts) {
                        parts.add(partSize * i)
                    }

                    val lineChartData =
                        priceHistory.map { LineChartEntity(it.bestPrice.toFloat(), it.label) }

                    Row() {
                        LineChartVerticalTest(
                            lineChartData = lineChartData,
                            verticalAxisValues = parts.map { it.toFloat() },
                            isShowHorizontalLines = true,
                            isShowVerticalAxis = true,
                            strokeWidth = 2.dp,
                            lineColor = Color(0xFF00E676)
                        )

                        HorizontalAxisOnlyLineChart(
                            lineChartData = lineChartData,
                            verticalAxisValues = parts.map { it.toFloat() },
                            isShowHorizontalLines = true,
                            isShowVerticalAxis = true,
                            strokeWidth = 2.dp,
                            lineColor = Color(0xFF00E676)
//            axisColor = Color(0xFFA6A6A6),
//            verticalAxisLabelColor = Color(0xFFA6A6A6),
//            horizontalAxisLabelColor = Color(0xFF4F4F4F),
//            isShowVerticalAxis = false,
//            verticalAxisLabelFontSize = 20.sp,
//            horizontalAxisLabelFontSize = 30.sp,
                        )

                    }


                }

            }


        }


        if (details?.specifications?.isNotEmpty() != null) {
            println("details?.specifications :::: " + details?.specifications)

            item {
                Text(
                    text = "Características",
                    Modifier
                        .background(
                            color = Color.Black,
                            shape = RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp)
                        )
                        .padding(horizontal = 15.dp, vertical = 8.dp)
                        .fillMaxWidth(),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }

            itemsIndexed(details?.specifications!!) { index, item ->
                val rowColor = if (index % 2 != 0) {
                    Color(android.graphics.Color.parseColor("#DFDFDF"))
                } else {
                    Color.White
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(rowColor)
                        .padding(vertical = 10.dp, horizontal = 15.dp)
                ) {

                    Text(
                        text = item.first,
                        modifier = Modifier.weight(0.85f),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = item.second, modifier = Modifier.weight(1.15f))

                }
            }

        }


    }

}


//fun parseDate(dateString: String): LocalDate {
//    val formatter = DateTimeFormatter.ofPattern("d/M")
//    val currentYear = LocalDate.now().year
//    val formattedDateString = "$dateString/$currentYear"
//    return LocalDate.parse(formattedDateString, formatter)
//}

fun parseDate(dateString: String): Date {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val formattedDateString = "$dateString/$currentYear"
    val sdf = SimpleDateFormat("d/M/yyyy")
    return sdf.parse(formattedDateString)
}

fun formatDateToString(date: Date): String {
    val sdf = SimpleDateFormat("d/M aaaaa")
    return sdf.format(date)
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PrevChart() {

    val gson = Gson()
    val json =
        """[{"label":"27/8","bestPrice":4342},{"label":"28/8","bestPrice":4342},{"label":"29/8","bestPrice":4364},{"label":"30/8","bestPrice":4387},{"label":"31/8","bestPrice":4387},{"label":"1/9","bestPrice":4364},{"label":"2/9","bestPrice":4364},{"label":"3/9","bestPrice":4364},{"label":"4/9","bestPrice":4364},{"label":"5/9","bestPrice":4364},{"label":"6/9","bestPrice":4364},{"label":"7/9","bestPrice":4364},{"label":"8/9","bestPrice":4364},{"label":"9/9","bestPrice":4364},{"label":"10/9","bestPrice":4364},{"label":"11/9","bestPrice":4409},{"label":"12/9","bestPrice":4409},{"label":"13/9","bestPrice":4409},{"label":"14/9","bestPrice":4387},{"label":"15/9","bestPrice":4387},{"label":"16/9","bestPrice":4387},{"label":"17/9","bestPrice":4387},{"label":"18/9","bestPrice":4387},{"label":"19/9","bestPrice":4364},{"label":"20/9","bestPrice":2909},{"label":"21/9","bestPrice":2909},{"label":"22/9","bestPrice":2909},{"label":"23/9","bestPrice":2808},{"label":"24/9","bestPrice":2808},{"label":"25/9","bestPrice":2808}]"""
    val listType = object : TypeToken<List<Price>>() {}.type
    val priceHistory: List<Price> = gson.fromJson(json, listType)

    val data = priceHistory.associate { parseDate(it.label) to it.bestPrice.toFloat() }

    val xValuesToDates = data.keys.associateBy { it.time.toFloat() }

    //val chartEntryModel = entryModelOf(xValuesToDates.keys.zip(data.values, ::entryOf))


    val chartEntryModel =
        entryModelOf(xValuesToDates.keys.zip(data.values).map { (xValue, yValue) ->
            entryOf(xValue, yValue)
        })


    val horizontalAxisValueFormatter =
        AxisValueFormatter<AxisPosition.Horizontal.Bottom> { value, _ ->
            xValuesToDates[value]?.let { formatDateToString(it) }
                ?: formatDateToString(Date(value.toLong()))
        }


    FXCompybleTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            //val chartEntryModel = entryModelOf(0, 4, 2, 7, 1, 6, 3, 8)
            Chart(
                chart = lineChart(),
                model = chartEntryModel,
                modifier = Modifier.padding(16.dp),
                startAxis = rememberStartAxis(),
                bottomAxis = rememberBottomAxis(valueFormatter = horizontalAxisValueFormatter)

            )

        }
    }


}






