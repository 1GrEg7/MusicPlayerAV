package musicplayerav.Navigation

sealed class Screen(val route: String) {
    object ApiTracksScreen : Screen("apiTracksScreen")
    object DownloadTracksScreen : Screen("downloadTracksScreen")
}
