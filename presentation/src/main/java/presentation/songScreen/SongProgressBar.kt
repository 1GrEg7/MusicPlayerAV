package presentation.songScreen

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
    modifier: Modifier = Modifier,
    onRewindTo: (Int)->Unit,
    isPlaying: Boolean
) {

    var sliderPosition by remember { mutableStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }
    var totalMillis = duration * 1000

    // Если пользователь не двигает ползунок и ещё не достигнут конец, анимировано заполняем прогресс
    if (!isPlaying){
        LaunchedEffect(isDragging, sliderPosition) {
            if (!isDragging && sliderPosition < 1f) {

                // Вычисляем шаг обновления в миллисекундах
                // Интервал для 60 кадров в секунду
                val frameDelay = 16L


                // На сколько увеличивается значение за один кадр
                val increment = frameDelay.toFloat() / totalMillis

                sliderPosition = (sliderPosition + increment).coerceAtMost(1f)
            }
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
            // Как только пользователь закончил перетаскивать, можно продолжить анимацию, если не достигли конца
            onRewindTo( (sliderPosition*duration).toInt())
            isDragging = false
        },
        colors = SliderDefaults.colors(
            thumbColor = Color.White,
            activeTrackColor = Color.White,
            inactiveTrackColor = Color.Gray
        ),

    )
}