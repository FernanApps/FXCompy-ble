package pe.fernan.apps.compyble.ui.screen.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
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
import pe.fernan.apps.compyble.domain.model.Details
import pe.fernan.apps.compyble.domain.model.Price
import pe.fernan.apps.compyble.domain.model.Product
import pe.fernan.apps.compyble.ui.composables.HeaderTitle
import pe.fernan.apps.compyble.ui.screen.home.ProductCard
import pe.fernan.apps.compyble.ui.theme.FXCompybleTheme
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

    val details: Details? by viewModel.details.collectAsStateWithLifecycle(initialValue = null)

    println("DetailsScreen :::: $product")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        HeaderTitle(title = "DetailsScreen")
        if (details != null) {
            Text(
                text = details!!.label, Modifier
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(10.dp))
                    .padding(horizontal = 15.dp, vertical = 8.dp),
                color = Color.White,
                fontWeight = FontWeight.SemiBold

            )
        }
        ProductCard(product = product, modifier = Modifier.fillMaxWidth())
        if (details != null) {

            val chartEntryModel = entryModelOf(0, 4, 2, 7, 1, 6, 3, 8)
            Chart(
                chart = lineChart(),
                model = chartEntryModel,
                modifier = Modifier.padding(16.dp),
                startAxis = rememberStartAxis(),
                bottomAxis = rememberBottomAxis()

            )
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


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PrevOther() {

}



