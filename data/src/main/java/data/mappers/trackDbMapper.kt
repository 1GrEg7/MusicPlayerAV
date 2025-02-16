package data.mappers

import core.recycleTrackList.Track
import data.db.TrackDb

fun Track.toTrackDb(): TrackDb {
    return TrackDb(
        id = this.id,
        cover = this.cover,
        title = this.title,
        author = this.author,
        albumName = this.albumName,
        preview = this.preview
    )
}

fun TrackDb.toDomain(): Track {
    return Track(
        id = this.id,
        cover = this.cover,
        title = this.title,
        author = this.author,
        albumName = this.albumName,
        preview = this.preview
    )
}