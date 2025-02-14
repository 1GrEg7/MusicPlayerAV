package data.mappers

import core.recycleTrackList.Track
import data.trackData.TrackDTO

fun TrackDTO.toDomain(): Track {
    return Track(
        cover = this.album.cover,
        title = this.title,
        author = artist.name
    )
}