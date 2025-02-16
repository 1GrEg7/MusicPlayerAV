package data.mappers

import core.recycleTrackList.Track
import data.trackData.TrackDTO

fun TrackDTO.toDomain(): Track {
    return Track(
        id = this.id,
        cover = this.album.cover_big,
        title = this.title,
        author = artist.name,
        preview = this.preview
    )
}