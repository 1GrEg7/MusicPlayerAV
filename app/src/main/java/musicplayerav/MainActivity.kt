package musicplayerav

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import data.db.DatabaseProvider
import data.db.TrackDbRepoImpl
import data.trackData.TracksRepoImpl
import musicplayerav.Navigation.Screen
import musicplayerav.Navigation.TopLevelRoute
import musicplayerav.downloadTracks.DownloadTracksScreen
import musicplayerav.player.MusicService
import presentation.apiTracks.ApiTracksScreen
import presentation.apiTracks.ApiTracksViewModel
import presentation.downloadTracks.DownloadTracksViewModel
import presentation.songScreen.SongScreen
import presentation.songScreen.SongScreenViewModel


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val trackDao = DatabaseProvider.provideTrackDao(this)
        val trackDbRepoImpl = TrackDbRepoImpl(trackDao)

        setContent {
            val downloadTracksViewModel = DownloadTracksViewModel(trackDbRepoImpl)
            val apiTracksViewModel = ApiTracksViewModel(TracksRepoImpl)
            val songScreenViewModel = SongScreenViewModel(apiTracksViewModel,TracksRepoImpl)
            LaunchedEffect(Unit) {
                apiTracksViewModel.getChartTracks()

                downloadTracksViewModel.fetchAllTracks()

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
                            viewModel = downloadTracksViewModel,
                            onItemClick ={ track ->
                                navController.navigate(
                                    "downloadDetails?id=${track.id}&cover_big=${track.cover}&preview=${track.preview}&indexTrack=${0}"
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
                            onItemClick = {track, indexTrack ->
                                navController.navigate(
                                    "downloadDetails?id=${track.id}&cover_big=${track.cover}&preview=${track.preview}&indexTrack=${indexTrack}"
                                )
                            }
                        )
                    }

                    composable(
                        route = "downloadDetails?id={id}&cover_big={cover_big}&preview={preview}&indexTrack={indexTrack}",
                        arguments = listOf(
                        navArgument("id") { type = NavType.LongType; defaultValue = -1 },
                        navArgument("cover_big") { type = NavType.StringType },
                        navArgument("preview") { type = NavType.StringType },
                        navArgument("indexTrack") { type = NavType.IntType }
                        ),


                    ) { backStackEntry ->
                    SongScreen(
                        toBackScreen = { navController.popBackStack() },
                        trackId = backStackEntry.arguments?.getLong("id") ?: -1,
                        cover = backStackEntry.arguments?.getString("cover_big")?: "",
                        preview = backStackEntry.arguments?.getString("preview") ?: "",
                        viewModel = songScreenViewModel,
                        onPlayMusic = { urlTrack,trackId  ->
                            startPlayMusic(urlTrack, trackId)
                        },
                        onPauseMusic = {
                            toPauseMusic()
                        },
                        onRewindTo = { timePosition ->
                            onRewindTrackTo(timePosition)
                        },
                        onDownloadTrack = {track ->
                            downloadTracksViewModel.addTrack(track)
                        } ,
                        trackIndexOfList = backStackEntry.arguments?.getInt("indexTrack") ?: -1
                        )
                    }


                }
            }

        }
    }

    fun startPlayMusic(urlTrack: String, trackId:Long){
        val mp3Url = urlTrack
        val intent = Intent(this, MusicService::class.java).apply {
            action = MusicService.ACTION_PLAY
            putExtra(MusicService.EXTRA_MP3_URL, mp3Url)
            putExtra(MusicService.CURRENT_TRACK_ID, trackId)
        }
        startService(intent)

    }
    fun toPauseMusic(){
        val intent = Intent(this, MusicService::class.java).apply {
            action = MusicService.ACTION_PAUSE
        }
        startService(intent)

    }
    fun onRewindTrackTo(timePosition:Int){
        val intent = Intent(this, MusicService::class.java).apply {
            action = MusicService.ACTION_REWIND
            putExtra(MusicService.REWIND_TIME, timePosition*1000)
        }
        startService(intent)

    }

    override fun onDestroy() {
        super.onDestroy()
        val intent = Intent(this, MusicService::class.java)
        stopService(intent)
    }
}



