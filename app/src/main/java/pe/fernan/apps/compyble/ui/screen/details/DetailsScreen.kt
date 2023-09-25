package pe.fernan.apps.compyble.ui.screen.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.entryModelOf
import pe.fernan.apps.compyble.domain.model.Details
import pe.fernan.apps.compyble.domain.model.Product
import pe.fernan.apps.compyble.ui.composables.HeaderTitle
import pe.fernan.apps.compyble.ui.screen.home.ProductCard
import java.time.LocalDate

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
        verticalArrangement = Arrangement.Center
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

