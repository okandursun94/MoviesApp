package com.od.moviesapp.View.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.od.moviesapp.Model.Video
import com.od.moviesapp.R
import com.od.moviesapp.util.getProgressDrawable
import com.od.moviesapp.util.loadImage
import kotlinx.android.synthetic.main.item_movie.view.*

class VideoListAdapter (var videos: ArrayList<Video>): RecyclerView.Adapter<VideoListAdapter.VideoViewHolder>() {


    var onItemClick: ((Video) -> Unit)? = null

    fun updateMovies(newVideos: List<Video>){
        videos.clear()
        videos.addAll(newVideos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VideoViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false )
    )

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videos[position])
    }

    override fun getItemCount() = videos.size

    inner class VideoViewHolder(view: View): RecyclerView.ViewHolder(view){

        private val movieName = view.name
        private val imageView = view.imageView
        private val progressDrawable = getProgressDrawable(view.context)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(videos[adapterPosition])
            }
        }

        fun bind(video: Video){
            movieName.text = video.name
            val s = "https://image.tmdb.org/t/p/w342${video.id}"  //is how you fetch a poster of a movie from TMDb
            imageView.loadImage(video.trailerImagePath,progressDrawable)
        }
    }
}