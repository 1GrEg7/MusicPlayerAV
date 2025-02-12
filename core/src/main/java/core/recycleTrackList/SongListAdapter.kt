package core.recycleTrackList


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import core.R

class SongListAdapter(
    private var songs: List<Song>,
    private val onItemClick: ((Song) -> Unit)? = null,  // Лямбда для обработки клика по всему элементу
    private val onDeleteClick: ((Song, Int) -> Unit)? = null, // Лямбда для обработки клика по трем точкам
    private val showDots: Boolean = true  // Добавляем параметр для управления отображением иконки
) : RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {

    inner class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgCover: ImageView = itemView.findViewById(R.id.imgCover)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvAuthor: TextView = itemView.findViewById(R.id.tvAuthor)
        val iconDots: ImageView = itemView.findViewById(R.id.iconDots)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(songs[adapterPosition])
            }

            // Если нужно отображать иконку, назначаем обработчик клика
            if (showDots) {
                iconDots.setOnClickListener {
                    val popupView = LayoutInflater.from(itemView.context)
                        .inflate(R.layout.popup_delete_item_button, null)

                    val popupWindow = PopupWindow(
                        popupView,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        true
                    )

                    popupWindow.elevation = 10f

                    val tvDelete = popupView.findViewById<TextView>(R.id.tvDelete)
                    tvDelete.setOnClickListener {
                        val position = adapterPosition
                        if (position != RecyclerView.NO_POSITION) {
                            onDeleteClick?.invoke(songs[position], position)
                            notifyItemRemoved(position)
                            notifyItemRangeChanged(position, itemCount - position)
                        }
                        popupWindow.dismiss()
                    }

                    popupWindow.showAsDropDown(iconDots, -iconDots.width, 0)
                }
            } else {
                iconDots.visibility = View.GONE
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
