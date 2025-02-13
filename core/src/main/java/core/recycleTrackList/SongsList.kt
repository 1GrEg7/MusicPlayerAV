package core.recycleTrackList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun SongsList(
    modifier: Modifier = Modifier,
    songs: SnapshotStateList<Song>,
    onItemClick: ((Song) -> Unit)? = null,   // Передаём обработчик клика по всей кнопки
    onDeleteClick: ((Song,Int) -> Unit)? = null, // Передаём обработчик клика по удалению песни
    showIcons: Boolean = true
) {
    val context = LocalContext.current

    // Создаем и запоминаем адаптер
    val songAdapter = remember {
        SongListAdapter(songs, onItemClick, onDeleteClick,showIcons)
    }

    // При изменении списка обновляем адаптер
    LaunchedEffect(songs.toList()) {
        songAdapter.updateSongs(songs)
    }
        AndroidView(
            factory = { ctx ->
                RecyclerView(ctx).apply {
                    layoutManager = LinearLayoutManager(ctx)
                    adapter = songAdapter
                }
            },
            modifier = modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Нет скаченных песен")

        }


}
