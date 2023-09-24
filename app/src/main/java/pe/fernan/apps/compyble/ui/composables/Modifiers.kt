package pe.fernan.apps.compyble.ui.composables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


// Thanks You https://blog.canopas.com/jetpack-compose-cool-button-click-effects-c6bbecec7bcb
enum class ButtonState { Pressed, Idle }


fun Modifier.bounceClick(scalePressed: Float = 0.70f, onClick: () -> Unit = {}) = composed {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val scale by animateFloatAsState(if (buttonState == ButtonState.Pressed) scalePressed else 1f)

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick
        )
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ButtonState.Pressed) {
                    waitForUpOrCancellation()
                    ButtonState.Idle
                } else {
                    awaitFirstDown(false)
                    ButtonState.Pressed
                }
            }
        }
}

fun Modifier.pressClickEffect(onClick: () -> Unit = {}) = composed {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val ty by animateFloatAsState(if (buttonState == ButtonState.Pressed) 0f else -20f)

    this
        .graphicsLayer {
            translationY = ty
        }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick
        )
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ButtonState.Pressed) {
                    waitForUpOrCancellation()
                    ButtonState.Idle
                } else {
                    awaitFirstDown(false)
                    ButtonState.Pressed
                }
            }
        }
}


fun Modifier.shakeClickEffect() = composed {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }

    val tx by animateFloatAsState(
        targetValue = if (buttonState == ButtonState.Pressed) 0f else -50f,
        animationSpec = repeatable(
            iterations = 2,
            animation = tween(durationMillis = 50, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    this
        .graphicsLayer {
            translationX = tx
        }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = { }
        )
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ButtonState.Pressed) {
                    waitForUpOrCancellation()
                    ButtonState.Idle
                } else {
                    awaitFirstDown(false)
                    ButtonState.Pressed
                }
            }
        }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TestPulsateEffect() {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {

        Button(
            onClick = {
                // clicked
            },
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.bounceClick()
        ) {
            Text(text = "Click me - bounceClick")
        }
        Spacer(modifier = Modifier.size(20.dp))

        Button(
            onClick = {
                //Clicked
            }, shape = RoundedCornerShape(12.dp), contentPadding = PaddingValues(16.dp),
            modifier = Modifier.pressClickEffect()
        ) {
            Text(text = "Click me - pressClickEffect")
        }

        Spacer(modifier = Modifier.size(20.dp))

        Button(
            onClick = {
                //Clicked
            }, shape = RoundedCornerShape(12.dp), contentPadding = PaddingValues(16.dp),
            modifier = Modifier.shakeClickEffect()
        ) {
            Text(text = "Click me - shakeClickEffect")
        }

    }

}