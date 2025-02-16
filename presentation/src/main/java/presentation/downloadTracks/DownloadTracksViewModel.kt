package presentation.downloadTracks

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.recycleTrackList.Track
import kotlinx.coroutines.launch

class DownloadTracksViewModel:ViewModel() {

    val searchField = mutableStateOf("")

    val list = listOf(
        Track(1,"https://images.wallpaperscraft.com/image/single/lake_mountain_tree_36589_2650x1600.jpg", "Песня 1", "Автор 1"),
        Track(2,"https://ya.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fimages.wallpaperscraft.com%2Fimage%2Fsingle%2Flake_mountain_tree_36589_2650x1600.jpg&lr=213&pos=0&rpt=simage&text=%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B8", "Песня 2", "Автор 2"),
        Track(3,"https://ya.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fimages.wallpaperscraft.com%2Fimage%2Fsingle%2Flake_mountain_tree_36589_2650x1600.jpg&lr=213&pos=0&rpt=simage&text=%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B8", "Песня 1", "Автор 3"),
        Track(4,"https://ya.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fimages.wallpaperscraft.com%2Fimage%2Fsingle%2Flake_mountain_tree_36589_2650x1600.jpg&lr=213&pos=0&rpt=simage&text=%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B8", "Песня 2", "Автор 4"),
        Track(5,"https://ya.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fimages.wallpaperscraft.com%2Fimage%2Fsingle%2Flake_mountain_tree_36589_2650x1600.jpg&lr=213&pos=0&rpt=simage&text=%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B8", "Песня 1", "Автор 5"),
        Track(6,"https://ya.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fimages.wallpaperscraft.com%2Fimage%2Fsingle%2Flake_mountain_tree_36589_2650x1600.jpg&lr=213&pos=0&rpt=simage&text=%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B8", "Песня 2", "Автор 6"),
        Track(7,"https://ya.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fimages.wallpaperscraft.com%2Fimage%2Fsingle%2Flake_mountain_tree_36589_2650x1600.jpg&lr=213&pos=0&rpt=simage&text=%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B8", "Песня 1", "Автор 1"),
        Track(8,"https://ya.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fimages.wallpaperscraft.com%2Fimage%2Fsingle%2Flake_mountain_tree_36589_2650x1600.jpg&lr=213&pos=0&rpt=simage&text=%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B8", "Песня 2", "Автор 2"),
        Track(9,"https://ya.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fimages.wallpaperscraft.com%2Fimage%2Fsingle%2Flake_mountain_tree_36589_2650x1600.jpg&lr=213&pos=0&rpt=simage&text=%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B8", "Песня 1", "Автор 3"),
        Track(10,"https://ya.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fimages.wallpaperscraft.com%2Fimage%2Fsingle%2Flake_mountain_tree_36589_2650x1600.jpg&lr=213&pos=0&rpt=simage&text=%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B8", "Песня 2", "Автор 4"),
        Track(11,"https://ya.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fimages.wallpaperscraft.com%2Fimage%2Fsingle%2Flake_mountain_tree_36589_2650x1600.jpg&lr=213&pos=0&rpt=simage&text=%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B8", "Песня 1", "Автор 5"),
        Track(12,"https://images.wallpaperscraft.com/image/single/lake_mountain_tree_36589_2650x1600.jpg", "Песня 2", "Автор 6")
    )



    val songs = list.toMutableStateList()

    val filteredTracks =  mutableStateListOf<Track>()

    fun deleteSong(index: Int){
        songs.removeAt(index)
        filterItems(searchField.value)
    }

//    fun addSong(){
//        songs.add(
//            Track("https://i.pinimg.com/originals/36/76/99/36769945f37cb48d1cc24ba4dc724d94.jpg","Новая песня", "Новый автор")
//        )
//        filterItems(searchField.value)
//    }

    fun filterItems(query: String) {
        filteredTracks.clear()
        viewModelScope.launch {
            songs.forEach {
                if (it.title.contains(query, true) || it.author.contains(query, true)) filteredTracks.add(it)
            }
            Log.d("1111111",  filteredTracks.joinToString())
        }
    }

}