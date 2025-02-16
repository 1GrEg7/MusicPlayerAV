package musicplayerav.Navigation

data class TopLevelRoute<T:Any>(
    val name: String,
    val route: T,
    val icon: Int
)