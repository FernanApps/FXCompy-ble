package pe.fernan.apps.compyble.ui.composables


import android.graphics.Paint
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import pe.fernan.apps.compyble.domain.model.Price
import pe.fernan.apps.compyble.ui.theme.FXCompybleTheme
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun dpToPx(value: Dp): Float = LocalDensity.current.run { value.toPx() }

@Composable
fun spToPx(value: TextUnit): Float = LocalDensity.current.run { value.toPx() }

@Composable
fun Dp.toPx() = dpToPx(this)


@Composable
fun TextUnit.toPx() = spToPx(this)


@Composable
fun textUnitToPx(value: TextUnit): Float = LocalDensity.current.run { value.toPx() }

data class LineChartEntity(
    val value: Float,
    val label: String? = ""
)

val DefaultGridLinesColor = Color(0xFFd9d9d9)
val DefaultAxisColor = Color(0xFFd9d9d9)
val DefaultAxisLabelColor = Color(0xFF5B5E5B)

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val DefaultAxisLabelFontSize = 16.sp
val DefaultAxisThickness = 1.dp
val DefaultBarWidth = 14.dp


@Composable
fun LineChart(
    modifier: Modifier? = Modifier.padding(top = 16.dp, bottom = 16.dp),
    lineChartData: List<LineChartEntity>,
    verticalAxisValues: List<Float>,
    axisColor: Color = Color.Gray,
    horizontalAxisLabelColor: Color = Color.Black,
    horizontalAxisLabelFontSize: Float = 12.sp.toPx(),
    verticalAxisLabelColor: Color = Color.Black,
    verticalAxisLabelFontSize: Float = 12.sp.toPx(),
    isShowVerticalAxis: Boolean = false,
    isShowHorizontalLines: Boolean = true,
    strokeWidth: Dp = 4.dp,
    lineColor: Color = Color.Blue,
) {


    val strokeWidthPx = strokeWidth.toPx()
    val axisThicknessPx = 2f // Grosor del eje

    Canvas(
        modifier = modifier!!.aspectRatio(1f)
    ) {
        val bottomAreaHeight = horizontalAxisLabelFontSize
        val leftAreaWidth = 2 * horizontalAxisLabelFontSize // Ancho del área izquierda

        val verticalAxisLength = size.height - bottomAreaHeight
        val horizontalAxisLength = size.width - leftAreaWidth

        val distanceBetweenVerticalAxisValues = verticalAxisLength / (verticalAxisValues.size - 1)

        // Dibujar eje horizontal
        if (!isShowHorizontalLines)
            drawRect(
                color = axisColor,
                topLeft = Offset(leftAreaWidth, verticalAxisLength),
                size = Size(horizontalAxisLength, axisThicknessPx)
            )

        // Dibujar eje vertical
        if (isShowVerticalAxis)
            drawRect(
                color = axisColor,
                topLeft = Offset(leftAreaWidth, 0.0f),
                size = Size(axisThicknessPx, verticalAxisLength)
            )

        // Dibujar valores del eje vertical y líneas horizontales
        for (index in verticalAxisValues.indices) {
            val x = leftAreaWidth / 2
            val y = verticalAxisLength - (distanceBetweenVerticalAxisValues * index)

            // Dibujar valor del eje vertical
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    verticalAxisValues[index].toString(),
                    x,
                    y + verticalAxisLabelFontSize / 2,
                    Paint().apply {
                        textSize = verticalAxisLabelFontSize
                        color = verticalAxisLabelColor.toArgb()
                        textAlign = Paint.Align.CENTER
                    }
                )
            }

            // Dibujar línea horizontal
            if (isShowHorizontalLines)
                drawRect(
                    color = axisColor,
                    topLeft = Offset(leftAreaWidth, y),
                    size = Size(horizontalAxisLength, axisThicknessPx)
                )
        }

        // Dibujar líneas curvas
        val maxAxisValue = verticalAxisValues.maxOrNull() ?: 0f

        val path = Path()
        var prevX = leftAreaWidth
        var prevY = verticalAxisLength

        for ((index, entity) in lineChartData.withIndex()) {
            val x = leftAreaWidth + (horizontalAxisLength / (lineChartData.size - 1)) * index
            val y = verticalAxisLength - (verticalAxisLength * entity.value / maxAxisValue)

            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.cubicTo(
                    prevX + (x - prevX) / 2, prevY,
                    prevX + (x - prevX) / 2, y,
                    x, y
                )
            }

            prevX = x
            prevY = y

            // Dibujar etiqueta del eje horizontal
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    entity.label ?: "",
                    x,
                    verticalAxisLength + horizontalAxisLabelFontSize,
                    Paint().apply {
                        textSize = horizontalAxisLabelFontSize
                        color = horizontalAxisLabelColor.toArgb()
                        textAlign = Paint.Align.CENTER
                    }
                )
            }
        }

        // Dibujar líneas curvas
        //drawPath(path, color = lineColor, strokeWidth = strokeWidthPx)

        drawPath(
            path, color = lineColor,
            style = Stroke(
                width = strokeWidthPx,
                //pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
            )
        )
    }
}

@Composable
fun LineChart2(
    modifier: Modifier? = Modifier.padding(top = 16.dp, bottom = 16.dp),
    lineChartData: List<LineChartEntity>,
    verticalAxisValues: List<Float>,
    axisColor: Color = DefaultAxisColor,
    horizontalAxisLabelColor: Color = DefaultAxisLabelColor,
    horizontalAxisLabelFontSize: TextUnit = DefaultAxisLabelFontSize,
    verticalAxisLabelColor: Color = DefaultAxisLabelColor,
    verticalAxisLabelFontSize: TextUnit = DefaultAxisLabelFontSize,
    isShowVerticalAxis: Boolean = false,
    isShowHorizontalLines: Boolean = true,
    strokeWidth: Dp = 4.dp,
    lineColor: Color = Color.Blue,
) {
    val strokeWidthPx = dpToPx(strokeWidth)
    val axisThicknessPx = dpToPx(DefaultAxisThickness)


    Row(
        Modifier
            .height(350.dp)
            .horizontalScroll(rememberScrollState())
    ) {

        Canvas(
            modifier = modifier!!.aspectRatio(4f / 1f)
        ) {

            val bottomAreaHeight = horizontalAxisLabelFontSize.toPx()
            val leftAreaWidth =
                (verticalAxisValues[verticalAxisValues.size - 1].toString().length * verticalAxisLabelFontSize.toPx()
                    .div(1.75)).toInt()

            val verticalAxisLength = (size.height - bottomAreaHeight)
            val horizontalAxisLength = size.width - leftAreaWidth

            val distanceBetweenVerticalAxisValues =
                (verticalAxisLength / (verticalAxisValues.size - 1))

            // Draw horizontal axis
            if (isShowHorizontalLines.not())
                drawRect(
                    color = axisColor,
                    topLeft = Offset(leftAreaWidth.toFloat(), verticalAxisLength),
                    size = Size(horizontalAxisLength, axisThicknessPx)
                )

            // Draw vertical axis
            if (isShowVerticalAxis)
                drawRect(
                    color = axisColor,
                    topLeft = Offset(leftAreaWidth.toFloat(), 0.0f),
                    size = Size(axisThicknessPx, verticalAxisLength)
                )


            // Draw vertical axis values & horizontal lines
            for (index in verticalAxisValues.indices) {

                val x = (leftAreaWidth / 2).toFloat()
                val y = verticalAxisLength - (distanceBetweenVerticalAxisValues).times(index)

                // Draw vertical axis value
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        verticalAxisValues[index].toString(),
                        x,
                        y + verticalAxisLabelFontSize.toPx() / 2,
                        Paint().apply {
                            textSize = verticalAxisLabelFontSize.toPx()
                            color = verticalAxisLabelColor.toArgb()
                            textAlign = Paint.Align.CENTER
                        }
                    )
                }

                // Draw horizontal line
                if (isShowHorizontalLines)
                    drawRect(
                        color = axisColor,
                        topLeft = Offset(leftAreaWidth.toFloat(), y),
                        size = Size(horizontalAxisLength, axisThicknessPx)
                    )
            }

            // Draw lines and it's labels
            val barWidth =
                (drawContext.size.width - leftAreaWidth) / lineChartData.size

            val maxAxisValue = verticalAxisValues[verticalAxisValues.size - 1]

            var previousOffset: Offset? = null

            for (index in lineChartData.indices) {
                val entity = lineChartData[index]

                // Draw line
                val currentOffset = calculateOffset(
                    entity.value,
                    index,
                    maxAxisValue,
                    barWidth,
                    leftAreaWidth,
                    verticalAxisLength
                )

                val end = Offset(currentOffset.x + barWidth.div(2), currentOffset.y)

                drawCircle(
                    color = lineColor,
                    center = end,
                    radius = strokeWidthPx.times(1.5f)
                )

                if (previousOffset != null) {
                    val start = Offset(previousOffset.x + barWidth.div(2), previousOffset.y)
                    drawLine(
                        start = start,
                        end = end,
                        color = lineColor,
                        strokeWidth = strokeWidthPx
                    )
                }

                previousOffset = currentOffset

                // Draw horizontal axis label
                if (lineChartData[index].label?.isNotEmpty() == true) {
                    drawContext.canvas.nativeCanvas.apply {
                        drawText(
                            lineChartData[index].label!!,
                            currentOffset.x + barWidth.div(2),
                            verticalAxisLength + horizontalAxisLabelFontSize.toPx(),
                            Paint().apply {
                                textSize = bottomAreaHeight
                                color = horizontalAxisLabelColor.toArgb()
                                textAlign = Paint.Align.CENTER
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun LineChartVerticalTest(
    modifier: Modifier? = Modifier.padding(top = 16.dp, bottom = 16.dp),
    lineChartData: List<LineChartEntity>,
    verticalAxisValues: List<Float>,
    axisColor: Color = DefaultAxisColor,
    horizontalAxisLabelColor: Color = DefaultAxisLabelColor,
    horizontalAxisLabelFontSize: TextUnit = DefaultAxisLabelFontSize,
    verticalAxisLabelColor: Color = DefaultAxisLabelColor,
    verticalAxisLabelFontSize: TextUnit = DefaultAxisLabelFontSize,
    isShowVerticalAxis: Boolean = false,
    isShowHorizontalLines: Boolean = true,
    strokeWidth: Dp = 4.dp,
    lineColor: Color = Color.Blue,
) {
    val strokeWidthPx = dpToPx(strokeWidth)
    val axisThicknessPx = dpToPx(DefaultAxisThickness)

    Row(
        Modifier.height(350.dp)
    ) {

        Canvas(
            modifier = modifier!!
                .fillMaxHeight()
                .aspectRatio(
                    1f / (verticalAxisValues.size / 2).toFloat()
                )
        ) {

            val bottomAreaHeight = horizontalAxisLabelFontSize.toPx()
            val leftAreaWidth =
                (verticalAxisValues[verticalAxisValues.size - 1].toString().length * verticalAxisLabelFontSize.toPx()
                    .div(1.75)).toInt()

            val verticalAxisLength = (size.height - bottomAreaHeight)

            val distanceBetweenVerticalAxisValues =
                (verticalAxisLength / (verticalAxisValues.size - 1))

            // Draw vertical axis
            drawRect(
                color = axisColor,
                topLeft = Offset(leftAreaWidth.toFloat(), 0.0f),
                size = Size(axisThicknessPx, verticalAxisLength)
            )

            // Draw vertical axis values
            for (index in verticalAxisValues.indices) {

                val x = (leftAreaWidth / 2).toFloat()
                val y = verticalAxisLength - (distanceBetweenVerticalAxisValues).times(index)

                // Draw vertical axis value
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        verticalAxisValues[index].toString(),
                        x,
                        y + verticalAxisLabelFontSize.toPx() / 2,
                        Paint().apply {
                            textSize = verticalAxisLabelFontSize.toPx()
                            color = verticalAxisLabelColor.toArgb()
                            textAlign = Paint.Align.CENTER
                        }
                    )
                }
            }
        }
    }
}


private fun calculateOffset(
    value: Float,
    index: Int,
    maxAxisValue: Float,
    barWidth: Float,
    leftAreaWidth: Int,
    verticalAxisLength: Float
): Offset {
    var x = barWidth * index
    x += leftAreaWidth

    val barHeightPercentage = (value / maxAxisValue)
    val barHeightInPixel = barHeightPercentage * verticalAxisLength
    val y = verticalAxisLength - barHeightInPixel

    return Offset(x, y)
}


@Composable
fun HorizontalAxisOnlyLineChart(
    modifier: Modifier? = Modifier.padding(top = 16.dp, bottom = 16.dp),
    lineChartData: List<LineChartEntity>,
    verticalAxisValues: List<Float>,
    axisColor: Color = DefaultAxisColor,
    horizontalAxisLabelColor: Color = DefaultAxisLabelColor,
    horizontalAxisLabelFontSize: TextUnit = DefaultAxisLabelFontSize,
    verticalAxisLabelColor: Color = DefaultAxisLabelColor,
    verticalAxisLabelFontSize: TextUnit = DefaultAxisLabelFontSize,
    isShowVerticalAxis: Boolean = false,
    isShowHorizontalLines: Boolean = true,
    strokeWidth: Dp = 4.dp,
    lineColor: Color = Color.Blue,
) {
    val strokeWidthPx = dpToPx(strokeWidth)
    val axisThicknessPx = dpToPx(DefaultAxisThickness)

    val bottomAreaHeight = horizontalAxisLabelFontSize.toPx()
    val leftAreaWidth =
        (verticalAxisValues[verticalAxisValues.size - 1].toString().length * verticalAxisLabelFontSize.toPx()
                / 1.75).toInt()

    val verticalAxisLength = 350.dp.toPx() - bottomAreaHeight
    val horizontalAxisLength = 3f / 1f - leftAreaWidth

    val distanceBetweenVerticalAxisValues = verticalAxisLength / (verticalAxisValues.size - 1)

    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        delay(500)
        //scrollState.animateScrollTo(scrollState.maxValue)
        scrollState.animateScrollTo(
            scrollState.maxValue,
            animationSpec = tween(durationMillis = 1000)
        )
    }
    Row(
        Modifier
            .height(350.dp)
            .horizontalScroll(scrollState)) {

        Canvas(
            modifier = modifier!!.aspectRatio(4f / 1f)
        ) {

            val bottomAreaHeight = horizontalAxisLabelFontSize.toPx()

            val barWidth =
                (drawContext.size.width) / lineChartData.size

            val maxAxisValue = lineChartData.map { it.value }.maxOrNull() ?: 0f

            var previousOffset: Offset? = null

            for (index in lineChartData.indices) {
                val entity = lineChartData[index]

                // Draw line
                val currentOffset = calculateOffset(
                    entity.value,
                    index,
                    maxAxisValue,
                    barWidth,
                    0, // No hay eje vertical
                    size.height - bottomAreaHeight // Resta el área de la etiqueta horizontal
                )

                val end = Offset(currentOffset.x + barWidth.div(2), currentOffset.y)

                drawCircle(
                    color = lineColor,
                    center = end,
                    radius = strokeWidthPx.times(1.5f)
                )

                if (previousOffset != null) {
                    val start = Offset(previousOffset.x + barWidth.div(2), previousOffset.y)
                    drawLine(
                        start = start,
                        end = end,
                        color = lineColor,
                        strokeWidth = strokeWidthPx
                    )
                }

                previousOffset = currentOffset

                // Draw horizontal axis label
                if (entity.label?.isNotEmpty() == true) {
                    drawContext.canvas.nativeCanvas.apply {
                        drawText(
                            entity.label!!,
                            currentOffset.x + barWidth.div(2),
                            size.height - bottomAreaHeight + horizontalAxisLabelFontSize.toPx(),
                            Paint().apply {
                                textSize = horizontalAxisLabelFontSize.toPx()
                                color = horizontalAxisLabelColor.toArgb()
                                textAlign = Paint.Align.CENTER
                            }
                        )
                    }
                }
            }

        }
    }
}





@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    FXCompybleTheme {

        val gson = Gson()
        val json =
            """[{"label":"27/8","bestPrice":4342},{"label":"28/8","bestPrice":4342},{"label":"29/8","bestPrice":4364},{"label":"30/8","bestPrice":4387},{"label":"31/8","bestPrice":4387},{"label":"1/9","bestPrice":4364},{"label":"2/9","bestPrice":4364},{"label":"3/9","bestPrice":4364},{"label":"4/9","bestPrice":4364},{"label":"5/9","bestPrice":4364},{"label":"6/9","bestPrice":4364},{"label":"7/9","bestPrice":4364},{"label":"8/9","bestPrice":4364},{"label":"9/9","bestPrice":4364},{"label":"10/9","bestPrice":4364},{"label":"11/9","bestPrice":4409},{"label":"12/9","bestPrice":4409},{"label":"13/9","bestPrice":4409},{"label":"14/9","bestPrice":4387},{"label":"15/9","bestPrice":4387},{"label":"16/9","bestPrice":4387},{"label":"17/9","bestPrice":4387},{"label":"18/9","bestPrice":4387},{"label":"19/9","bestPrice":4364},{"label":"20/9","bestPrice":2909},{"label":"21/9","bestPrice":2909},{"label":"22/9","bestPrice":2909},{"label":"23/9","bestPrice":2808},{"label":"24/9","bestPrice":2808},{"label":"25/9","bestPrice":2808}]"""
        val listType = object : TypeToken<List<Price>>() {}.type
        val priceHistory: List<Price> = gson.fromJson(json, listType)

        val maxPrice = priceHistory.maxOfOrNull { it.bestPrice }!!

        val numberOfParts = 8
        val partSize = maxPrice / numberOfParts

        // Create a list to store the parts
        val parts = mutableListOf<Int>()

        // Fill the list with parts
        for (i in 1..numberOfParts) {
            parts.add(partSize * i)
        }

        // Print the list of parts
        println("List of parts:")
        for (part in parts) {
            println(part)
        }


        val lineChartData = priceHistory.map { LineChartEntity(it.bestPrice.toFloat(), it.label) }

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




