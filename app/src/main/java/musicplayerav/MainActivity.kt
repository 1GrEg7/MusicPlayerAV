package musicplayerav

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import core.recycleTrackList.Track
import data.trackData.TracksRepoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import musicplayerav.Navigation.Screen
import musicplayerav.Navigation.TopLevelRoute
import musicplayerav.downloadTracks.DownloadTracksScreen
import presentation.songScreen.SongScreen
import presentation.apiTracks.ApiTracksScreen
import presentation.apiTracks.ApiTracksViewModel



val list = listOf(
    Track("https://images.wallpaperscraft.com/image/single/lake_mountain_tree_36589_2650x1600.jpg", "Песня 1", "Автор 1"),
    Track("https://ya.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fimages.wallpaperscraft.com%2Fimage%2Fsingle%2Flake_mountain_tree_36589_2650x1600.jpg&lr=213&pos=0&rpt=simage&text=%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B8", "Песня 2", "Автор 2"),
    Track("https://ya.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fimages.wallpaperscraft.com%2Fimage%2Fsingle%2Flake_mountain_tree_36589_2650x1600.jpg&lr=213&pos=0&rpt=simage&text=%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B8", "Песня 1", "Автор 3"),
    Track("https://ya.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fimages.wallpaperscraft.com%2Fimage%2Fsingle%2Flake_mountain_tree_36589_2650x1600.jpg&lr=213&pos=0&rpt=simage&text=%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B8", "Песня 2", "Автор 4"),
    Track("https://ya.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fimages.wallpaperscraft.com%2Fimage%2Fsingle%2Flake_mountain_tree_36589_2650x1600.jpg&lr=213&pos=0&rpt=simage&text=%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B8", "Песня 1", "Автор 5"),
    Track("https://ya.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fimages.wallpaperscraft.com%2Fimage%2Fsingle%2Flake_mountain_tree_36589_2650x1600.jpg&lr=213&pos=0&rpt=simage&text=%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B8", "Песня 2", "Автор 6"),
    Track("https://ya.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fimages.wallpaperscraft.com%2Fimage%2Fsingle%2Flake_mountain_tree_36589_2650x1600.jpg&lr=213&pos=0&rpt=simage&text=%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B8", "Песня 1", "Автор 1"),
    Track("https://ya.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fimages.wallpaperscraft.com%2Fimage%2Fsingle%2Flake_mountain_tree_36589_2650x1600.jpg&lr=213&pos=0&rpt=simage&text=%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B8", "Песня 2", "Автор 2"),
    Track("https://ya.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fimages.wallpaperscraft.com%2Fimage%2Fsingle%2Flake_mountain_tree_36589_2650x1600.jpg&lr=213&pos=0&rpt=simage&text=%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B8", "Песня 1", "Автор 3"),
    Track("https://ya.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fimages.wallpaperscraft.com%2Fimage%2Fsingle%2Flake_mountain_tree_36589_2650x1600.jpg&lr=213&pos=0&rpt=simage&text=%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B8", "Песня 2", "Автор 4"),
    Track("https://ya.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fimages.wallpaperscraft.com%2Fimage%2Fsingle%2Flake_mountain_tree_36589_2650x1600.jpg&lr=213&pos=0&rpt=simage&text=%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B8", "Песня 1", "Автор 5"),
    Track("https://images.wallpaperscraft.com/image/single/lake_mountain_tree_36589_2650x1600.jpg", "Песня 2", "Автор 6")
)



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            val downloadTracksViewModel: presentation.downloadTracks.DownloadTracksViewModel = viewModel()
            val apiTracksViewModel = ApiTracksViewModel(TracksRepoImpl)
            LaunchedEffect(Unit) {
                apiTracksViewModel.getChartTracks()
            }



            val navController = rememberNavController()
            val topLevelRoutes = listOf(
                TopLevelRoute("Скаченное", Screen.DownloadTracksScreen.route, R.drawable.music_list_icon),
                TopLevelRoute("Поиск", Screen.ApiTracksScreen.route, R.drawable.manage_search_icon)
            )

            val routesWithBottomBar = listOf(
                Screen.DownloadTracksScreen.route,
                Screen.ApiTracksScreen.route
            )

            Scaffold(
                bottomBar = {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    val currentRoute = currentDestination?.route

                    if (currentRoute in routesWithBottomBar){
                        BottomNavigation(
                            backgroundColor = colorResource(id = R.color.lightBlue)
                        ){

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
                        DownloadTracksScreen(
                            list =  list, viewModel = downloadTracksViewModel,
                            onItemClick ={ track ->

                                navController.navigate(
                                    "downloadDetails?songName=${track.title}&singerName=${track.author}&albumName=${track.albumName}&cover=${track.cover}"
                                )
                        })
                    }
                    composable(
                        Screen.ApiTracksScreen.route,
                        popEnterTransition = { EnterTransition.None },
                        popExitTransition = { ExitTransition.None },
                        enterTransition = { EnterTransition.None },
                        exitTransition = { ExitTransition.None }
                    ){
                        ApiTracksScreen(
                            context = baseContext,
                            viewModel = apiTracksViewModel,
                            onItemClick = {track ->
                                navController.navigate(
                                    "downloadDetails?songName=${track.title}&singerName=${track.author}&albumName=${track.albumName}&cover=${track.cover}"
                                )
                            }
                        )
                    }

                    composable(
                        route = "downloadDetails?songName={songName}&singerName={singerName}&albumName={albumName}&cover={cover}",
                        arguments = listOf(
                        navArgument("songName") { type = NavType.StringType },
                        navArgument("singerName") { type = NavType.StringType },
                        navArgument("albumName") { type = NavType.StringType },
                        navArgument("cover") { type = NavType.IntType; defaultValue = 0 },
                        ),


                    ) { backStackEntry ->
                    SongScreen(
                        toBackScreen = { navController.popBackStack() },
                        songName = backStackEntry.arguments?.getString("songName") ?: "",
                        singerName = backStackEntry.arguments?.getString("singerName") ?: "",
                        albumName = backStackEntry.arguments?.getString("albumName") ?: "",
                        cover = backStackEntry.arguments?.getInt("cover")
                        )
                    }


                }
            }

        }
    }
}



