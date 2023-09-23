package pe.fernan.apps.compyble.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import pe.fernan.apps.compyble.R

// Colors in https://compy.pe/app.css


fun colorsMap(): Map<String, Color> {
    val colorList = listOf(
        "-black" to Color(0xFF000000),
        "-white" to Color(0xFFFFFFFF),
        "-bluegray" to Color(0xFF3289A1),
        "-orange-1" to Color(0xFFFD754A),
        "-pink" to Color(0xFFE05DB1),
        "-gold" to Color(0xFFB7985B),
        "-orange-2" to Color(0xFFFF6047),
        "-blue-1" to Color(0xFF004CFF),
        "-blue-2" to Color(0xFF007BFF),
        "-gray-1" to Color(0xFFF5F5F7),
        "-orange-3" to Color(0xFFFF4C3B),
        "-gray-2" to Color(0xFF8E8E91),
        "-purple" to Color(0xFF4D31B9),
        "-gray-3" to Color(0xFF85878B),
        "-gray-4" to Color(0xFFDFDFDF),
        "-red" to Color(0xFFDE2641),
        "-gray-5" to Color(0xFF727B8C),
        "-gray-6" to Color(0xFF70798B),
        "-gray-7" to Color(0xFF707070),
        "-gray-8" to Color(0xFFACACAC),
        "-gray-9" to Color(0xFFF3F4F6),
        "-gray-10" to Color(0xFF2F2F2F),
        "-orange-4" to Color(0xFFFE4327),
        "-gray-11" to Color(0xFF474747),
        "-gray-12" to Color(0xFFE8E8E8),
        "-gray-13" to Color(0xFFFAFAFA),
        "-gray-14" to Color(0xFF2C2C2C),
        "-gray-15" to Color(0xFFF6F8FA),
        "-orange-5" to Color(0xFFFF6600),
        "-cyan" to Color(0xFF13C9CA)
    )
    return colorList.toMap()
}


fun getColorByNameOrDefault(colorName: String): Color? {
    println("Checking :::: $colorName")
    val colorMap = colorsMap()
    val foundColor = colorMap[colorName]

    if (foundColor != null) {
        return foundColor
    } else {
        for (key in colorMap.keys) {
            if (colorName in key) {
                return colorMap[key]
            }
        }
    }

    return null
}