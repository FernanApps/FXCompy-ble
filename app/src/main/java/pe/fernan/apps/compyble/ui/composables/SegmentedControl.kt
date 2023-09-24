package pe.fernan.apps.compyble.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex


// Thank You https://medium.com/@manojbhadane/hello-everyone-558290eb632e
/**
 * items : list of items to be render
 * defaultSelectedItemIndex : to highlight item by default (Optional)
 * useFixedWidth : set true if you want to set fix width to item (Optional)
 * itemWidth : Provide item width if useFixedWidth is set to true (Optional)
 * cornerRadius : To make control as rounded (Optional)
 * color : Set color to control (Optional)
 * onItemSelection : Get selected item index
 */
@Composable
fun SegmentedControl(
    items: List<String>,
    modifier: Modifier = Modifier,
    defaultSelectedItemIndex: Int = 0,
    cornerRadius: Int = 10,
    color: Color = MaterialTheme.colorScheme.primary,
    fontSize: TextUnit = TextUnit.Unspecified,
    onItemSelection: (selectedItemIndex: Int) -> Unit
) {

    val selectedIndex = remember { mutableStateOf(defaultSelectedItemIndex) }


    val buttonHorizontalPadding = 16.dp
    val buttonVerticalPadding = 8.dp

    val contentPadding =
        PaddingValues(
            start = buttonHorizontalPadding,
            top = buttonVerticalPadding,
            end = buttonHorizontalPadding,
            bottom = buttonVerticalPadding
        )

    Row(
        modifier = modifier
    ) {
        items.forEachIndexed { index, item ->
            OutlinedButton(
                modifier = modifier
                    .weight(1f / items.size)
                    .offset((-1 * index).dp, 0.dp)
                    .zIndex(if (selectedIndex.value == index) 1f else 0f),
                onClick = {
                    selectedIndex.value = index
                    onItemSelection(selectedIndex.value)
                },
                contentPadding = contentPadding,
                shape = when (index) {
                    /**
                     * left outer button
                     */
                    0 -> RoundedCornerShape(
                        topStartPercent = cornerRadius,
                        topEndPercent = 0,
                        bottomStartPercent = cornerRadius,
                        bottomEndPercent = 0
                    )
                    /**
                     * right outer button
                     */
                    items.size - 1 -> RoundedCornerShape(
                        topStartPercent = 0,
                        topEndPercent = cornerRadius,
                        bottomStartPercent = 0,
                        bottomEndPercent = cornerRadius
                    )
                    /**
                     * middle button
                     */
                    else -> RoundedCornerShape(
                        topStartPercent = 0,
                        topEndPercent = 0,
                        bottomStartPercent = 0,
                        bottomEndPercent = 0
                    )
                },
                border = BorderStroke(
                    1.dp, if (selectedIndex.value == index) {
                        color
                    } else {
                        color.copy(alpha = 0.75f)
                    }
                ),
                colors = if (selectedIndex.value == index) {
                    /**
                     * selected colors
                     */
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = color
                    )
                } else {
                    /**
                     * not selected colors
                     */
                    ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent)
                },
            ) {
                Text(
                    text = item,
                    fontSize = fontSize,
                    fontWeight = FontWeight.Normal,
                    color = if (selectedIndex.value == index) {
                        Color.White
                    } else {
                        color.copy(alpha = 0.9f)
                    },
                )
            }
        }
    }
}



@Composable
fun SegmentedControlLazy(
    items: Map<String, String>,
    modifier: Modifier = Modifier,
    defaultSelectedItemIndex: Int = 0,
    cornerRadius: Int = 10,
    color: Color = MaterialTheme.colorScheme.primary,
    fontSize: TextUnit = TextUnit.Unspecified,
    onItemSelection: (Pair<String, String>) -> Unit
) {
    val selectedIndex = remember { mutableIntStateOf(defaultSelectedItemIndex) }

    val itemsList = remember {
        mutableStateListOf<Pair<String, String>>().apply {
            items.forEach { item ->
                add(item.toPair())
            }
        }
    }

    LazyRow(
        modifier = modifier
    ) {
        itemsList.forEachIndexed { index, item ->
            item {
                val shape = when (index) {
                    0 -> RoundedCornerShape(
                        topStartPercent = cornerRadius,
                        topEndPercent = 0,
                        bottomStartPercent = cornerRadius,
                        bottomEndPercent = 0
                    )
                    items.size - 1 -> RoundedCornerShape(
                        topStartPercent = 0,
                        topEndPercent = cornerRadius,
                        bottomStartPercent = 0,
                        bottomEndPercent = cornerRadius
                    )
                    else -> RoundedCornerShape(0)
                }

                val borderColor = if (selectedIndex.intValue == index) color else color.copy(alpha = 0.75f)

                OutlinedButton(
                    onClick = {
                        selectedIndex.intValue = index
                        onItemSelection(itemsList[selectedIndex.intValue]  )
                    },
                    shape = shape,
                    border = BorderStroke(1.dp, borderColor),
                    colors = if (selectedIndex.value == index) {
                        ButtonDefaults.outlinedButtonColors(
                            containerColor = color
                        )
                    } else {
                        ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent)
                    },
                    modifier = Modifier.padding(horizontal = 0.dp),
                ) {
                    Text(
                        text = item.second,
                        fontSize = fontSize,
                        fontWeight = FontWeight.Normal,
                        color = if (selectedIndex.intValue == index) Color.White else color.copy(alpha = 0.9f),
                    )
                }
            }
        }
    }
}
