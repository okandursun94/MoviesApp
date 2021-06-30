package com.od.moviesapp.View.Activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.CircularProgressDrawable
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.od.moviesapp.ModelView.*
import com.od.moviesapp.R
import com.od.moviesapp.View.Adapter.PersonMoviesAdapter
import com.od.moviesapp.util.getProgressDrawable
import com.od.moviesapp.util.loadImage
import kotlinx.android.synthetic.main.activity_detail.imageView
import kotlinx.android.synthetic.main.activity_person.*
import kotlinx.android.synthetic.main.activity_person.tv_rating

class PersonActivity : AppCompatActivity() {

    var selectedPersonId: Int = -1

    lateinit var viewModelPerson: PersonViewModel
    lateinit var viewModelPersonMovie: PersonMovieViewModel
    lateinit var viewModelPersonSeries: PersonSeriesViewModel

    private val personMoviesAdapter = PersonMoviesAdapter(arrayListOf())
    private val personMoviesAdapter2 = PersonMoviesAdapter(arrayListOf())

    lateinit var progressDrawable: CircularProgressDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person)

        selectedPersonId = intent.getData("id").toInt() // get data from previous activity
        progressDrawable = getProgressDrawable(this )

        viewModelPerson = ViewModelProviders.of(this).get(PersonViewModel::class.java)
        viewModelPersonMovie = ViewModelProviders.of(this).get(PersonMovieViewModel::class.java)
        viewModelPersonSeries = ViewModelProviders.of(this).get(PersonSeriesViewModel::class.java)

        init()
    }

    fun init(){

        viewModelPerson.refresh(selectedPersonId)
        observePersonViewModel()

        viewModelPersonMovie.refresh(selectedPersonId)
        personMovieList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = personMoviesAdapter
            personMoviesAdapter.onItemClick = { movie ->

                println("clicked: "+movie.name)

            }
        }
        observeViewModel()

        viewModelPersonSeries.refresh(selectedPersonId)
        tvList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = personMoviesAdapter2
            personMoviesAdapter2.onItemClick = { movie ->

                println("clicked: "+movie.name)

            }
        }
        observePersonTVViewModel()
    }


    fun observeViewModel(){

        viewModelPersonMovie.cast.observe(this, Observer { cast ->
            cast?.let{
                personMoviesAdapter.updateMovies(it)}
        })
    }

    fun observePersonViewModel(){

        viewModelPerson.person.observe(this, Observer { person ->
            person?.let{
                tv_name.text = person.name
                tv_rating.text = person.known_for_department
                tv_biography.text = person.biography


                val s = "https://image.tmdb.org/t/p/w342${person?.profile_path}"  //is how you fetch a poster of a movie from TMDb
                imageView.loadImage(s,progressDrawable)}
        })
    }

    fun observePersonTVViewModel(){

        viewModelPersonSeries.cast.observe(this, Observer { cast ->
            cast?.let{
                personMoviesAdapter2.updateMovies(it)}
        })
    }

    fun Intent.getData(key: String): String {
        return extras?.getString(key) ?: "intent is null"
    }
}