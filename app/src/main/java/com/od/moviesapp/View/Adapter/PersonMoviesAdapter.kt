package com.od.moviesapp.View.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.od.moviesapp.Model.Cast
import com.od.moviesapp.R
import com.od.moviesapp.util.getProgressDrawable
import com.od.moviesapp.util.loadImage
import kotlinx.android.synthetic.main.item_movie.view.*

class PersonMoviesAdapter(var casts: ArrayList<Cast>): RecyclerView.Adapter<PersonMoviesAdapter.MovieViewHolder>() {

    var onItemClick: ((Cast) -> Unit)? = null

    fun updateMovies(newCast: List<Cast>){
        casts.clear()
        casts.addAll(newCast)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_cast, parent, false )
    )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(casts[position])
    }

    override fun getItemCount() = casts.size

    inner class MovieViewHolder(view: View): RecyclerView.ViewHolder(view){

        private val imageView = view.imageView
        private val name = view.name
        private val progressDrawable = getProgressDrawable(view.context)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(casts[adapterPosition])
            }
        }

        fun bind(cast: Cast){
            name.text=cast.name
            val s = "https://image.tmdb.org/t/p/w342${cast.poster_path}"  //is how you fetch a poster of a movie from TMDb
            imageView.loadImage(s,progressDrawable)
        }
    }
}