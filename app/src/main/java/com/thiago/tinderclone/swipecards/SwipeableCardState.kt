package com.thiago.tinderclone.swipecards

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

// Enum que representa as direções possíveis para deslizar um cartão
enum class Direction {
    Left, Right, Up, Down
}

@Composable
// Cria e lembra do estado de um cartão deslizante
fun rememberSwipeableCardState(): SwipeableCardState {
    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
    }
    val screenHeight = with(LocalDensity.current) {
        LocalConfiguration.current.screenHeightDp.dp.toPx()
    }
    return remember {
        SwipeableCardState(screenWidth, screenHeight)
    }
}

// Classe que mantém o estado de um cartão deslizante
class SwipeableCardState(
    internal val maxWidth: Float,
    internal val maxHeight: Float,
) {
    val offset = Animatable(offset(0f, 0f), Offset.VectorConverter)

    /**
     * A [Direction] na qual o cartão foi deslizado.
     *
     * O valor nulo significa que o cartão ainda não foi completamente deslizado.
     */
    var swipedDirection: Direction? by mutableStateOf(null)
        private set

    // Redefine o cartão para sua posição inicial
    internal suspend fun reset() {
        offset.animateTo(offset(0f, 0f), tween(400))
    }

    // Move o cartão na direção especificada com animação
    suspend fun swipe(direction: Direction, animationSpec: AnimationSpec<Offset> = tween(400)) {
        val endX = maxWidth * 1.5f
        val endY = maxHeight
        when (direction) {
            Direction.Left -> offset.animateTo(offset(x = -endX), animationSpec)
            Direction.Right -> offset.animateTo(offset(x = endX), animationSpec)
            Direction.Up -> offset.animateTo(offset(y = -endY), animationSpec)
            Direction.Down -> offset.animateTo(offset(y = endY), animationSpec)
        }
        this.swipedDirection = direction
    }

    // Cria um objeto Offset com os valores fornecidos
    private fun offset(x: Float = offset.value.x, y: Float = offset.value.y): Offset {
        return Offset(x, y)
    }

    // Move o cartão para uma nova posição com animação
    internal suspend fun drag(x: Float, y: Float) {
        offset.animateTo(offset(x, y))
    }
}
