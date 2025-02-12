package feature.DownloadTracksScreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import core.recycleTrackList.Song
import core.recycleTrackList.SongsList
import feature.R

@Composable
fun DownloadTrackScreen(context: Context, list: List<Song>) {

    val songs: SnapshotStateList<Song> = remember {  list.toMutableStateList() }

    fun addPicture(){
        songs.add(
            Song("https://i.pinimg.com/originals/36/76/99/36769945f37cb48d1cc24ba4dc724d94.jpg","Новая песня", "Новый автор")
        )
    }
    fun removePicture(index:Int){
        songs.removeAt(index)
    }


    Column() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(4f)
                .padding(top = 20.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Скаченные песни",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold

            )
        }
        Column(modifier = Modifier.fillMaxSize().weight(2f)) {
            Row(
                modifier = Modifier.fillMaxSize().weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                OutlinedTextField(
                    value = "",
                    onValueChange = { },
                    label = { Text(text = "") },
                    modifier = Modifier.padding(start = 20.dp)
                )
                Icon(
                    modifier = Modifier.padding(start = 10.dp,top = 5.dp, end = 30.dp),
                    painter = painterResource(id = R.drawable.search_icon),
                    contentDescription = "Описание изображения",
                    tint = Color.Gray
                )

            }
        }
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
                    addPicture()
                    Toast.makeText(context, "Нажата песня: ${clickedSong.title} от ${clickedSong.author}",
                        Toast.LENGTH_SHORT).show()
                    //println("Нажата песня: ${clickedSong.title} от ${clickedSong.author}")
                },
                onDeleteClick = { clickedSong,
                    index -> removePicture(index)

                }
            )
        }

    }
}