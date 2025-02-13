package musicplayerav

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import core.recycleTrackList.Song
import core.TracksScreen
import musicplayerav.Navigation.Screen
import musicplayerav.Navigation.TopLevelRoute
import musicplayerav.apiTracks.ApiTracksScreen
import musicplayerav.apiTracks.ApiTracksViewModel
import musicplayerav.downloadTracks.DownloadTracksScreen
import musicplayerav.downloadTracks.DownloadTracksViewModel
import musicplayerav.ui.theme.MusicPlayerAVTheme


val list = listOf(
    Song("https://images.wallpaperscraft.com/image/single/lake_mountain_tree_36589_2650x1600.jpg", "Песня 1", "Автор 1"),
    Song("https://ya.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fimages.wallpaperscraft.com%2Fimage%2Fsingle%2Flake_mountain_tree_36589_2650x1600.jpg&lr=213&pos=0&rpt=simage&text=%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B8", "Песня 2", "Автор 2"),
    Song("https://ya.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fimages.wallpaperscraft.com%2Fimage%2Fsingle%2Flake_mountain_tree_36589_2650x1600.jpg&lr=213&pos=0&rpt=simage&text=%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B8", "Песня 1", "Автор 3"),
    Song("https://ya.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fimages.wallpaperscraft.com%2Fimage%2Fsingle%2Flake_mountain_tree_36589_2650x1600.jpg&lr=213&pos=0&rpt=simage&text=%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B8", "Песня 2", "Автор 4"),
    Song("https://ya.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fimages.wallpaperscraft.com%2Fimage%2Fsingle%2Flake_mountain_tree_36589_2650x1600.jpg&lr=213&pos=0&rpt=simage&text=%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B8", "Песня 1", "Автор 5"),
    Song("https://ya.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fimages.wallpaperscraft.com%2Fimage%2Fsingle%2Flake_mountain_tree_36589_2650x1600.jpg&lr=213&pos=0&rpt=simage&text=%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B8", "Песня 2", "Автор 6"),
    Song("https://ya.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fimages.wallpaperscraft.com%2Fimage%2Fsingle%2Flake_mountain_tree_36589_2650x1600.jpg&lr=213&pos=0&rpt=simage&text=%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B8", "Песня 1", "Автор 1"),
    Song("https://ya.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fimages.wallpaperscraft.com%2Fimage%2Fsingle%2Flake_mountain_tree_36589_2650x1600.jpg&lr=213&pos=0&rpt=simage&text=%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B8", "Песня 2", "Автор 2"),
    Song("https://ya.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fimages.wallpaperscraft.com%2Fimage%2Fsingle%2Flake_mountain_tree_36589_2650x1600.jpg&lr=213&pos=0&rpt=simage&text=%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B8", "Песня 1", "Автор 3"),
    Song("https://ya.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fimages.wallpaperscraft.com%2Fimage%2Fsingle%2Flake_mountain_tree_36589_2650x1600.jpg&lr=213&pos=0&rpt=simage&text=%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B8", "Песня 2", "Автор 4"),
    Song("https://ya.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fimages.wallpaperscraft.com%2Fimage%2Fsingle%2Flake_mountain_tree_36589_2650x1600.jpg&lr=213&pos=0&rpt=simage&text=%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B8", "Песня 1", "Автор 5"),
    Song("https://images.wallpaperscraft.com/image/single/lake_mountain_tree_36589_2650x1600.jpg", "Песня 2", "Автор 6")
)



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {


           // MusicPlayerAVTheme {

            val downloadTracksViewModel: DownloadTracksViewModel = viewModel()
            val apiTracksViewModel: ApiTracksViewModel = viewModel()


            val navController = rememberNavController()
            val topLevelRoutes = listOf(
                TopLevelRoute("Скаченное", Screen.DownloadTracksScreen.route, R.drawable.music_list_icon),
                TopLevelRoute("Поиск", Screen.ApiTracksScreen.route, R.drawable.manage_search_icon)
            )
            Scaffold(
                bottomBar = {
                    BottomNavigation(
                        backgroundColor = colorResource(id = R.color.lightBlue)
                    ){
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        topLevelRoutes.forEach{ topLevelRoute ->
                            val isSelected = currentDestination?.route == topLevelRoute.route
                            BottomNavigationItem(
                                icon = {
                                    Icon(
                                        painter = painterResource(topLevelRoute.icon),
                                        contentDescription = topLevelRoute.name,
                                        tint = if (isSelected){
                                            Color.White
                                        }else{
                                            Color.Black
                                        }
                                    )
                                },
                                label = { Text(topLevelRoute.name) },
                                selected = isSelected,
                                onClick = {
                                    navController.navigate(topLevelRoute.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )

                        }
                    }
                }
            ){ innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = Screen.DownloadTracksScreen.route,
                    modifier = Modifier.padding(innerPadding)
                ){
                    composable(
                        Screen.DownloadTracksScreen.route,
                        popEnterTransition = { EnterTransition.None },
                        popExitTransition = { ExitTransition.None },
                        enterTransition = { EnterTransition.None },
                        exitTransition = { ExitTransition.None }
                    ){
                        DownloadTracksScreen(context = baseContext, list =  list, viewModel = downloadTracksViewModel)
                    }
                    composable(
                        Screen.ApiTracksScreen.route,
                        popEnterTransition = { EnterTransition.None },
                        popExitTransition = { ExitTransition.None },
                        enterTransition = { EnterTransition.None },
                        exitTransition = { ExitTransition.None }
                    ){
                        ApiTracksScreen(context = baseContext, list = mutableStateListOf<Song>(), viewModel = apiTracksViewModel)
                    }
                }
            }
            //}
        }
    }
}



