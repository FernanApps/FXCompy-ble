package pe.fernan.apps.compyble.utils


import androidx.compose.ui.graphics.Brush.Companion.horizontalGradient
import androidx.compose.ui.graphics.Color

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


val warmColors = listOf(
    Color(0xFFFFE4B5), // Moccasin
    Color(0xFFFFD700), // Gold
    Color(0xFFFFA500), // Orange
    Color(0xFFFF6347), // Tomato
    Color(0xFFFF4500), // Orange Red
    Color(0xFFE9967A), // Dark Salmon
    Color(0xFFE9967A), // Salmon
    Color(0xFFF08080), // Light Coral
    Color(0xFFCD5C5C), // Indian Red
    Color(0xFFD2691E), // Chocolate
    Color(0xFF8B4513), // Saddle Brown
    Color(0xFFA0522D), // Sienna
    Color(0xFFA52A2A), // Brown
    Color(0xFF800000), // Maroon
    Color(0xFFBC8F8F), // Rosy Brown
    Color(0xFFDAA520), // Golden Rod
    Color(0xFFB8860B), // Dark Golden Rod
    Color(0xFFCD853F), // Peru
    Color(0xFFD2691E), // Chocolate
    Color(0xFF8B4513), // Saddle Brown
    Color(0xFFA0522D), // Sienna
    Color(0xFFA52A2A), // Brown
    Color(0xFF800000), // Maroon
    Color(0xFFBC8F8F), // Rosy Brown
    Color(0xFFDAA520)  // Golden Rod
)


val warmGradients = listOf(
    horizontalGradient(
        colors = listOf(Color(0xFFE44D26), Color(0xFFF16529)),
    ),
    horizontalGradient(
        colors = listOf(Color(0xFFF7971E), Color(0xFFFFD200)),
    ),
    horizontalGradient(
        colors = listOf(Color(0xFFFF8008), Color(0xFFFFC837)),
    ),
    horizontalGradient(
        colors = listOf(Color(0xFFFF512F), Color(0xFFDD2476)),
    ),
    horizontalGradient(
        colors = listOf(Color(0xFFF9A03F), Color(0xFFF79824)),

        ),
    horizontalGradient(
        colors = listOf(Color(0xFFE83A0C), Color(0xFFE74C1E)),


        ),
    horizontalGradient(
        colors = listOf(Color(0xFFEF4026), Color(0xFFE74C1E)),


        ),
    horizontalGradient(
        colors = listOf(Color(0xFFF4511E), Color(0xFFF79824)),


        ),
    horizontalGradient(
        colors = listOf(Color(0xFFFF9A8B), Color(0xFFEF5A53)),


        ),
    horizontalGradient(
        colors = listOf(Color(0xFFF0642E), Color(0xFFF79824)),


        ),
    horizontalGradient(
        colors = listOf(Color(0xFFFFAA91), Color(0xFFFF8F71)),


        ),
    horizontalGradient(
        colors = listOf(Color(0xFFFEAA30), Color(0xFFFE762F)),


        ),
    horizontalGradient(
        colors = listOf(Color(0xFFF08E33), Color(0xFFF45F4E)),


        ),
    horizontalGradient(
        colors = listOf(Color(0xFFFFB75E), Color(0xFFFF9951)),


        ),
    horizontalGradient(
        colors = listOf(Color(0xFFF57F17), Color(0xFFFB8C00)),


        ),
    horizontalGradient(
        colors = listOf(Color(0xFFF48F26), Color(0xFFF9A825)),


        ),
    horizontalGradient(
        colors = listOf(Color(0xFFF48F26), Color(0xFFF9A825)),


        ),
    horizontalGradient(
        colors = listOf(Color(0xFFFCCF31), Color(0xFFF55555)),


        ),

    horizontalGradient(
        colors = listOf(Color(0xFFE67E22), Color(0xFFF1C40f)),


        ),
    horizontalGradient(
        colors = listOf(Color(0xFFFF6B6B), Color(0xFFFFA07A)),


        ),
    horizontalGradient(
        colors = listOf(Color(0xFFF78154), Color(0xFFFEAA30)),


        ),
    horizontalGradient(
        colors = listOf(Color(0xFFFF7F50), Color(0xFFFFD700)),


        ),
    horizontalGradient(
        colors = listOf(Color(0xFFF0E68C), Color(0xFFFFD700)),


        ),
    horizontalGradient(
        colors = listOf(Color(0xFFDDA0DD), Color(0xFFFF69B4)),


        ),
    horizontalGradient(
        colors = listOf(Color(0xFFFFA500), Color(0xFFFF4500)),


        )
)
