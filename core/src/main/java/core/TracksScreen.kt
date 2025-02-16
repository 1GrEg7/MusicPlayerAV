package core

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.recycleTrackList.Track
import core.recycleTrackList.SongsList


@Composable
fun TracksScreen(
    modifier: Modifier = Modifier,
    context: Context,
    list:SnapshotStateList<Track>,
    showIcons:Boolean = true,
    onItemClick:  (Track, Int) -> Unit,
    onDeleteClick: ((Track) -> Unit )? = null
) {

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
                tracks = list,
                onItemClick = { clickedSong, indexTrack ->
                    onItemClick(clickedSong, indexTrack)
                },
                onDeleteClick = { clickedSong ->
                    if (onDeleteClick != null) {
                        onDeleteClick(clickedSong)
                    }
                },
                showIcons = showIcons
            )
        }
    }
}