package musicplayerav.songScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay


@Composable
fun SongProgressBar(
    duration: Int,
    modifier: Modifier = Modifier
) {
    // Состояние для положения ползунка от 0f до 1f
    var sliderPosition by remember { mutableStateOf(0f) }
    // Флаг, сигнализирующий о том, что пользователь перетаскивает ползунок
    var isDragging by remember { mutableStateOf(false) }

    // Если пользователь не двигает ползунок и ещё не достигнут конец, анимировано заполняем прогресс
    LaunchedEffect(isDragging, sliderPosition) {
        if (!isDragging && sliderPosition < 1f) {
            // Вычисляем шаг обновления в миллисекундах
            // Интервал для 60 кадров в секунду
            val frameDelay = 16L
            // Общее время в миллисекундах
            val totalMillis = duration * 1000
            // На сколько увеличивается значение за один кадр
            val increment = frameDelay.toFloat() / totalMillis
            delay(frameDelay) // задержка перед следующим обновлением
            sliderPosition = (sliderPosition + increment).coerceAtMost(1f)
        }
    }

    Slider(
        modifier = modifier.fillMaxSize(),
        value = sliderPosition,
        onValueChange = { value ->
            sliderPosition = value
            isDragging = true
        },
        onValueChangeFinished = {
            // Как только пользователь закончил перетаскивать, можно продолжить автоматическую анимацию, если не достигнут конец
            isDragging = false
        },
        colors = SliderDefaults.colors(
            thumbColor = Color.White,
            activeTrackColor = Color.White,
            inactiveTrackColor = Color.Gray
        ),

    )
}