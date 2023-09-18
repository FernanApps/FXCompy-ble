package pe.fernan.apps.compyble.ui.splash

import android.content.Intent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import pe.fernan.apps.compyble.R
import pe.fernan.apps.compyble.ui.MainActivity
import pe.fernan.apps.compyble.ui.theme.FXCompybleTheme

@Composable
fun SplashScreen(name: String, modifier: Modifier = Modifier) {

    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        )
    )
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(4000)
        context.startActivity(Intent(context, MainActivity::class.java))
        //navController.popBackStack()
        //navController.navigate(Screen.Home.route)
    }
    Splash(name, modifier, alpha = alphaAnim.value)


    
}

@Composable
fun Splash(title: String, modifier: Modifier, alpha: Float) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.very_dark_gray)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.size(100.dp))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            modifier = Modifier.size(60.dp).alpha(alpha)
        )
        Spacer(Modifier.size(10.dp))
        Text(
            text = title,
            modifier = modifier.alpha(alpha),
            color = Color.White
        )

    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    FXCompybleTheme {
        SplashScreen(stringResource(id = R.string.app_name))
    }
}