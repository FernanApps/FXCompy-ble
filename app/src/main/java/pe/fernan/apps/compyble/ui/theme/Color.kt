package pe.fernan.apps.compyble.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)


@Composable
fun ListColorSchemeColors() {
    val colorScheme = MaterialTheme.colorScheme

    LazyColumn(Modifier.fillMaxSize().background(Color.Black)) {
        item {
            Text(
                text = "ColorScheme Colors:",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp),
                color = Color.White
            )
        }

        item {
            ColorItem(name = "Primary", color = colorScheme.primary)
        }
        item {
            ColorItem(name = "surfaceVariant", color = colorScheme.surfaceVariant)
        }
        item {
            ColorItem(name = "Secondary", color = colorScheme.secondary)
        }
        item {
            ColorItem(name = "onSurfaceVariant", color = colorScheme.onSurfaceVariant)
        }
        item {
            ColorItem(name = "Background", color = colorScheme.background)
        }
        item {
            ColorItem(name = "Surface", color = colorScheme.surface)
        }
        item {
            ColorItem(name = "Error", color = colorScheme.error)
        }
        item {
            ColorItem(name = "OnPrimary", color = colorScheme.onPrimary)
        }
        item {
            ColorItem(name = "OnSecondary", color = colorScheme.onSecondary)
        }
        item {
            ColorItem(name = "OnBackground", color = colorScheme.onBackground)
        }
        item {
            ColorItem(name = "OnSurface", color = colorScheme.onSurface)
        }
        item {
            ColorItem(name = "OnError", color = colorScheme.onError)
        }
    }
}

@Composable
fun ColorItem(name: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Hacer algo cuando se hace clic en un color */ }
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(color)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
    }
}

@Preview
@Composable
fun ListColorSchemeColorsPreview() {
    FXCompybleTheme {
        ListColorSchemeColors()
    }
}