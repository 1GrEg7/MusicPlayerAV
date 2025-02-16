package presentation.songScreen

import android.media.Image
import android.util.Log
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import kotlinx.coroutines.launch
import com.skydoves.landscapist.glide.GlideImage
import presentation.R

//@Preview(showBackground = true)
//@Composable
//fun PreviewSongScreen(){
//    SongScreen()
//}

@Composable
fun SongScreen(
    trackId:Long,
    toBackScreen: ()->Unit,
    songName: String = "",
    singerName: String = "",
    albumName:String = "",
    preview:String = "",
    viewModel: SongScreenViewModel = viewModel(),
    cover: String = "",
    onPlayMusic: (String,Long)->Unit,
    onPauseMusic: ()-> Unit,
    onRewindTo: (Int)->Unit

){
    val isPlayIcon = remember { mutableStateOf(true) }

    LaunchedEffect(key1 = preview) {
        viewModel.getMp3Duration(preview)
        viewModel.currentPreview.value = preview
        launch{
            viewModel.getTrackInfoById(trackId)
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3E3E3))
            //.background(Color.LightGray.copy(alpha = 0.5f))
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
//                Image(
//                    modifier = Modifier.fillMaxSize(),
////                    painter = if (cover!=null){
////                        painterResource(cover)
////                    }else{
////                        painterResource(core.R.drawable.note)
////                    },
//                    painter = painterResource(core.R.drawable.note),
//                    contentScale = ContentScale.Crop,
//                    contentDescription = "Обложка трека"
//                )
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(cover)
                        .size(Size.ORIGINAL)
                        .crossfade(true) // Эффект плавного появления
                        .build(),
                    placeholder = painterResource(core.R.drawable.note),
                    contentDescription = "Описание изображения",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )



            }
        }
        Row( modifier = Modifier.fillMaxSize().weight(2f)) {


            Column(
                modifier = Modifier.fillMaxSize().weight(3f).padding(start = 30.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = viewModel.trackInfo.collectAsState().value.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.padding(top = 5.dp),
                    text =  viewModel.trackInfo.collectAsState().value.author,
                    fontSize = 16.sp,
                    color = Color.Gray.copy(alpha = 0.8f)
                )
                Text(
                    modifier = Modifier.padding(top = 5.dp),
                    text = viewModel.trackInfo.collectAsState().value.albumName,
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
                SongProgressBar(duration = 30,
                    onRewindTo = { timePosition ->
                    onRewindTo(timePosition)
                },
                isPlaying = isPlayIcon.value)
            }
            Box(
                modifier = Modifier.fillMaxSize().weight(1f),
                contentAlignment = Alignment.Center
            ){
                Text(text = viewModel.endTimeTrack.value)
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

                    modifier = Modifier.clickable {


                        if (isPlayIcon.value){
                            onPlayMusic(viewModel.trackInfo.value.preview, viewModel.trackInfo.value.id)
                        }else{
                            onPauseMusic()
                        }
                        isPlayIcon.value = !isPlayIcon.value
                    },
                    painter = if(isPlayIcon.value){
                        painterResource(R.drawable.play_music_icon)
                    }else{
                        painterResource(R.drawable.pause_music_icon)
                    },
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