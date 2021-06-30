package com.od.moviesapp.View.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.od.moviesapp.Model.Movie
import com.od.moviesapp.R
import com.od.moviesapp.util.getProgressDrawable
import com.od.moviesapp.util.loadImage
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieListAdapter(var movies: ArrayList<Movie>): RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    var onItemClick: ((Movie) -> Unit)? = null

    fun updateMovies(newMovies: List<Movie>){
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false )
    )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount() = movies.size

    inner class MovieViewHolder(view: View): RecyclerView.ViewHolder(view){

        private val movieName = view.name
        private val imageView = view.imageView
        private val overview = view.overview
        private val progressDrawable = getProgressDrawable(view.context)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(movies[adapterPosition])
            }
        }

        fun bind(movie: Movie){
            movieName.text = movie.title
            overview.text = movie.overview
            val s = "https://image.tmdb.org/t/p/w342${movie.posterPath}"  //is how you fetch a poster of a movie from TMDb
            imageView.loadImage(s,progressDrawable)
        }
    }
}