package core

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import core.recycleTrackList.Song
import core.recycleTrackList.SongsList


@Composable
fun TracksScreen(
    modifier: Modifier = Modifier,
    context: Context,
    list:SnapshotStateList<Song>,
    showIcons:Boolean = true,
    onItemClick:  () -> Unit,
    onDeleteClick: ((Song,Int) -> Unit )? = null
) {

    val songs: SnapshotStateList<Song> =   list





    Column(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize().weight(1f)) {
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 10.dp),
                thickness = 3.dp,
                color = Color.Black.copy(alpha = 0.6f)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(14f)
                .padding(top = 20.dp)
                .clipToBounds()
        ) {
            SongsList(
                songs = songs,
                onItemClick = { clickedSong ->
                    onItemClick()
                    Toast.makeText(context, "Нажата песня: ${clickedSong.title} от ${clickedSong.author}",
                        Toast.LENGTH_SHORT).show()
                    Log.d("111111",songs.joinToString())
                },
                onDeleteClick = { clickedSong,
                    index ->
                    if (onDeleteClick != null) {
                        onDeleteClick(clickedSong,index)
                    }

                },
                showIcons = showIcons
            )
        }

    }
}