package com.thiago.tinderclone.swipecards

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs

/**
 * Este pacote contém definições e funções relacionadas à implementação de cartões deslizantes
 * usados em uma interface de usuário similar ao Tinder.
 */

// Extensão do Modificador para criar cartões deslizantes
@ExperimentalSwipeableCardApi
fun Modifier.swipableCard(
    state: SwipeableCardState,
    onSwiped: (Direction) -> Unit,
    onSwipeCancel: () -> Unit = {},
    blockedDirections: List<Direction> = listOf(Direction.Up, Direction.Down),
) = pointerInput(Unit) {
    coroutineScope {
        detectDragGestures(
            onDragCancel = {
                launch {
                    state.reset()
                    onSwipeCancel()
                }
            },
            onDrag = { change, dragAmount ->
                launch {
                    val original = state.offset.targetValue
                    val summed = original + dragAmount
                    val newValue = Offset(
                        x = summed.x.coerceIn(-state.maxWidth, state.maxWidth),
                        y = summed.y.coerceIn(-state.maxHeight, state.maxHeight)
                    )
                    if (change.positionChange() != Offset.Zero) change.consume()
                    state.drag(newValue.x, newValue.y)
                }
            },
            onDragEnd = {
                launch {
                    val coercedOffset = state.offset.targetValue
                        .coerceIn(blockedDirections,
                            maxHeight = state.maxHeight,
                            maxWidth = state.maxWidth)

                    if (hasNotTravelledEnough(state, coercedOffset)) {
                        state.reset()
                        onSwipeCancel()
                    } else {
                        val horizontalTravel = abs(state.offset.targetValue.x)
                        val verticalTravel = abs(state.offset.targetValue.y)

                        if (horizontalTravel > verticalTravel) {
                            if (state.offset.targetValue.x > 0) {
                                state.swipe(Direction.Right)
                                onSwiped(Direction.Right)
                            } else {
                                state.swipe(Direction.Left)
                                onSwiped(Direction.Left)
                            }
                        } else {
                            if (state.offset.targetValue.y < 0) {
                                state.swipe(Direction.Up)
                                onSwiped(Direction.Up)
                            } else {
                                state.swipe(Direction.Down)
                                onSwiped(Direction.Down)
                            }
                        }
                    }
                }
            }
        )
    }
}.graphicsLayer {
    translationX = state.offset.value.x
    translationY = state.offset.value.y
    rotationZ = (state.offset.value.x / 60).coerceIn(-40f, 40f)
}
// Limita a posição do cartão com base nas direções bloqueadas, altura e largura máximas
private fun Offset.coerceIn(
    blockedDirections: List<Direction>,
    maxHeight: Float,
    maxWidth: Float,
): Offset {
    return copy(
        x = x.coerceIn(
            if (blockedDirections.contains(Direction.Left)) {
                0f
            } else {
                -maxWidth
            },
            if (blockedDirections.contains(Direction.Right)) {
                0f
            } else {
                maxWidth
            }
        ),
        y = y.coerceIn(if (blockedDirections.contains(Direction.Up)) {
            0f
        } else {
            -maxHeight
        },
            if (blockedDirections.contains(Direction.Down)) {
                0f
            } else {
                maxHeight
            }
        )
    )
}

// Verifica se o cartão não foi movido o suficiente para ser considerado um gesto de arrastar efetivo
private fun hasNotTravelledEnough(
    state: SwipeableCardState,
    offset: Offset
): Boolean {
    return abs(offset.x) < state.maxWidth / 4 &&
            abs(offset.y) < state.maxHeight / 4
}