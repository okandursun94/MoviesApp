package com.od.moviesapp.View.Activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.widget.CircularProgressDrawable
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.od.moviesapp.ModelView.CreditViewModel
import com.od.moviesapp.ModelView.DetailViewModel
import com.od.moviesapp.ModelView.VideosViewModel
import com.od.moviesapp.R
import com.od.moviesapp.View.Adapter.CastListAdapter
import com.od.moviesapp.View.Adapter.VideoListAdapter
import com.od.moviesapp.util.getProgressDrawable
import com.od.moviesapp.util.loadImage
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*

class DetailActivity: AppCompatActivity() {

    var selectedMovieId: Int = -1
    lateinit var viewModel: DetailViewModel
    lateinit var viewModelCredit: CreditViewModel
    lateinit var viewModelVideos: VideosViewModel
    lateinit var progressDrawable: CircularProgressDrawable

    private val castAdapter = CastListAdapter(arrayListOf())
    private val videoAdapter = VideoListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        selectedMovieId = intent.getData("id").toInt() // get data from previous activity

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModelCredit = ViewModelProviders.of(this).get(CreditViewModel::class.java)
        viewModelVideos = ViewModelProviders.of(this).get(VideosViewModel::class.java)
        progressDrawable = getProgressDrawable(this )

        init()
    }

    fun init(){

        viewModel.refresh(selectedMovieId)
        observeViewModel()

        viewModelCredit.refresh(selectedMovieId)
        castList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = castAdapter
            castAdapter.onItemClick = { cast ->

                println("clicked: "+cast.name)
                startNewActivity(cast.id)
            }
        }
        observeCreditViewModel()

        viewModelVideos.refresh(selectedMovieId)
        videoList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = videoAdapter
            videoAdapter.onItemClick = { video ->

                println("clicked: "+video.name)
                openYoutubeLink(video.key)

            }
        }
        observeVideoViewModel()

    }

    fun observeViewModel(){

        viewModel.movie.observe(this, Observer { movie ->
            movie?.let{
                summary_text.text = movie.overview
                tv_rating.text = movie.rating.toString()


                val s = "https://image.tmdb.org/t/p/w342${movie?.posterPath}"  //is how you fetch a poster of a movie from TMDb
                imageView.loadImage(s,progressDrawable)}
        })

        viewModel.movieLoadError.observe(this, Observer { isError ->
            isError?.let {
                list_error.visibility = if(it) View.VISIBLE else View.GONE }
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loading_view.visibility = if(it) View.VISIBLE else View.GONE
                if(it){
                    list_error.visibility = View.GONE
                    moviesList.visibility = View.GONE
                }
            }
        })
    }

    fun observeCreditViewModel(){

        viewModelCredit.cast.observe(this, Observer { cast ->
            cast?.let{
            castAdapter.updateMovies(it.cast)}
        })
    }

    fun observeVideoViewModel(){

        viewModelVideos.video.observe(this, Observer { cast ->
            cast?.let{
                videoAdapter.updateMovies(it.video)}
        })
    }

    fun Intent.getData(key: String): String {
        return extras?.getString(key) ?: "intent is null"
    }

    fun startNewActivity(value: String){
        val intent = Intent(this, PersonActivity::class.java)
        intent.putExtra("id", value)
        startActivity(intent)
    }

    fun openYoutubeLink(youtubeID: String) {
        val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + youtubeID))
        val intentBrowser = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + youtubeID))
        try {
            this.startActivity(intentApp)
        } catch (ex: ActivityNotFoundException) {
            this.startActivity(intentBrowser)
        }

    }
}
