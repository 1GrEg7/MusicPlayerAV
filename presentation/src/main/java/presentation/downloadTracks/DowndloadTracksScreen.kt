package musicplayerav.downloadTracks


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import core.TracksScreen
import core.recycleTrackList.Track
import presentation.R
import presentation.downloadTracks.DownloadTracksViewModel

@Composable
fun DownloadTracksScreen(
    modifier: Modifier = Modifier,
    onItemClick: (Track)->Unit,
    viewModel: DownloadTracksViewModel = viewModel()
)
{
    Column(modifier = modifier.background(Color.White)) {

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
                text =  stringResource(R.string.download_tracks),
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
                Box(
                    modifier = Modifier.fillMaxSize().weight(7f),
                    contentAlignment = Alignment.Center
                ){
                    OutlinedTextField(
                        value = viewModel.searchField.value,
                        onValueChange = {
                            viewModel.searchField.value = it
                            viewModel.filterItems(it)
                                        },
                        label = { Text(text = "") },
                        modifier = Modifier.fillMaxWidth().padding(start = 20.dp)
                    )
                }
                Box(
                    modifier = Modifier.fillMaxSize().weight(2f),
                    contentAlignment = Alignment.TopCenter
                ){
                    Icon(
                        modifier = Modifier.fillMaxSize().padding(start = 10.dp,top = 5.dp, end = 30.dp),
                        painter = painterResource(id = core.R.drawable.search_icon),
                        contentDescription = "Описание изображения",
                        tint = Color.Gray
                    )
                }
            }
        }

        TracksScreen(
            modifier = Modifier.fillMaxSize().weight(14f),
            context = LocalContext.current,
            list = viewModel.filteredTracks,
            onItemClick = { song,trackIndex -> onItemClick(song) },
            onDeleteClick = { track ->
                viewModel.removeTrack(track)
            }
        )
    }



}