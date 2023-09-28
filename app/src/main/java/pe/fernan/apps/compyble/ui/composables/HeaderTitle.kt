package pe.fernan.apps.compyble.ui.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun HeaderTitle(
    title: String, @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 25.dp), style: TextStyle = MaterialTheme.typography.headlineSmall
) {
    val formattedTitle = title.take(1).uppercase() + title.lowercase().substring(1)
    Text(
        text = formattedTitle,
        style = style,
        fontWeight = FontWeight.Bold,
        modifier = modifier,
        textAlign = TextAlign.Start
    )
}