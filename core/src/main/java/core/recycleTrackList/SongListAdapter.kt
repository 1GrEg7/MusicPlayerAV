package core.recycleTrackList


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import core.R

class SongListAdapter(
    private var songs: List<Song>,
    private val onItemClick: ((Song) -> Unit)? = null  // Лямбда для обработки клика
) : RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {

    inner class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgCover: ImageView = itemView.findViewById(R.id.imgCover)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvAuthor: TextView = itemView.findViewById(R.id.tvAuthor)

        init {
            itemView.setOnClickListener {
                // Вызываем лямбду, если она задана, передавая выбранную песню
                onItemClick?.invoke(songs[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_songs_list, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]

        Glide.with(holder.itemView.context)
            .load(song.cover)
            .placeholder(R.drawable.note)
            .into(holder.imgCover)

        holder.tvTitle.text = song.title
        holder.tvAuthor.text = song.author
    }

    override fun getItemCount(): Int = songs.size

    fun updateSongs(newSongs: List<Song>) {
        songs = newSongs
        notifyDataSetChanged()
    }
}
