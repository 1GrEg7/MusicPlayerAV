package musicplayerav.songScreen

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import musicplayerav.R

//@Preview(showBackground = true)
//@Composable
//fun PreviewSongScreen(){
//    SongScreen()
//}

@Composable
fun SongScreen(
    toBackScreen: ()->Unit,
    songName: String = "",
    singerName: String = "",
    albumName:String = "",
    cover: Int? = null

){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray.copy(alpha = 0.5f))
    ) {

        Row(modifier = Modifier.fillMaxSize().weight(1f)) {
            Icon(
                modifier = Modifier.padding(10.dp).clickable {
                    toBackScreen()
                },
                painter = painterResource(R.drawable.arrow_back_icon),
                contentDescription = "На главный экран"
            )
        }
        Column(modifier = Modifier.fillMaxSize().weight(6f)) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(2.dp, Color.Gray, shape = RoundedCornerShape(16.dp))
            ){
                Image(
                    modifier = Modifier.fillMaxSize(),
//                    painter = if (cover!=null){
//                        painterResource(cover)
//                    }else{
//                        painterResource(core.R.drawable.note)
//                    },
                    painter = painterResource(core.R.drawable.note),
                    contentDescription = "Обложка трека"
                )
            }
        }
        Row( modifier = Modifier.fillMaxSize().weight(2f)) {
            Box(modifier = Modifier.fillMaxSize().weight(1f) )

            Column(
                modifier = Modifier.fillMaxSize().weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = songName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.padding(top = 5.dp),
                    text =  singerName,
                    fontSize = 16.sp,
                    color = Color.Gray.copy(alpha = 0.8f)
                )
                Text(
                    modifier = Modifier.padding(top = 5.dp),
                    text = albumName,
                    fontSize = 16.sp,
                    color = Color.Gray.copy(alpha = 0.8f)
                )
            }
            Box(
                modifier = Modifier.fillMaxSize().weight(1f),
                contentAlignment = Alignment.Center,

            ){
                Icon(
                    painter = painterResource(R.drawable.download_track_icon),
                    contentDescription = "Кнопка скачивания песни",
                    tint = Color.Black.copy(alpha = 0.7f)
                )
            }
        }


        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(2f)
        ) {
            Box(
                modifier = Modifier.fillMaxSize().weight(1f),
                contentAlignment = Alignment.Center
                ){
                Text("00:00")
            }
            Box(
                modifier = Modifier.fillMaxSize().weight(6f),
                contentAlignment = Alignment.Center
            ){
                SongProgressBar(20)
            }
            Box(
                modifier = Modifier.fillMaxSize().weight(1f),
                contentAlignment = Alignment.Center
            ){
                Text("03:11")
            }
        }

        Row(
            modifier = Modifier.fillMaxSize().weight(2f),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(
                modifier = Modifier.wrapContentSize(),

            ){
               Icon(
                   painter = painterResource(R.drawable.previous_track_icon),
                   contentDescription = "Предыдущий трек"
               )
            }
            Box(
                modifier = Modifier.wrapContentSize(),

            ){
                Icon(
                    painter = painterResource(R.drawable.play_music_icon),
                    contentDescription = "Предыдущий трек"
                )
            }
            Box(
                modifier = Modifier.wrapContentSize(),

            ){
                Icon(
                    painter = painterResource(R.drawable.next_track_icon),
                    contentDescription = "Предыдущий трек"
                )
            }
        }


    }
}