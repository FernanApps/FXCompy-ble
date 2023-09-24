package pe.fernan.apps.compyble.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ehsanmsz.mszprogressindicator.progressindicator.BallClipRotateMultipleProgressIndicator


@Composable
fun PageLoader(size: Dp = 100.dp) {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        BallClipRotateMultipleProgressIndicator(
            modifier = Modifier.size(size),
            color = MaterialTheme.colorScheme.primary
        )
    }
}