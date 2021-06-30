package com.od.moviesapp.View.Activity

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.od.moviesapp.ModelView.ListViewModel
import com.od.moviesapp.ModelView.SearchViewModel
import com.od.moviesapp.R
import com.od.moviesapp.View.Adapter.MovieListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity() {

    lateinit var viewModel: ListViewModel
    lateinit var viewModelSearch: SearchViewModel
    private val moviesAdapter = MovieListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModelSearch = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        init()
        edtListener()
    }

    fun init(){

        viewModel.refresh()
        moviesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = moviesAdapter
            moviesAdapter.onItemClick = { movie ->

                println("clicked: "+movie.title)
                movie.id?.let { startNewActivity(it) }
            }
        }
        observeViewModel()
    }

    fun edtListener(){

        // edit text enter key listener
        edt_search.setOnKeyListener(object : View.OnKeyListener {
            @SuppressLint("SetTextI18n")
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER
                ) {

                    val x = edt_search.text
                    if(edt_search.text.toString()==""){
                        viewModel.refresh()
                        viewModel.movies.value?.let { moviesAdapter.updateMovies(it) }
                    }else{
                        viewModelSearch.refresh(edt_search.text.toString(),moviesAdapter)
                        //viewModel.movies.value?.let { moviesAdapter.updateMovies(it) }
                    }
                    edt_search.setText("")
                    edt_search.setHint(x)
                    hideKeyboard()

                    return true
                }
                return false
            }
        })
    }

    fun observeViewModel(){

        viewModel.movies.observe(this, Observer { movies ->
            movies?.let{
                moviesList.visibility = View.VISIBLE
                moviesAdapter.updateMovies(it)}
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

    fun startNewActivity(value: Long){
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("id", value.toString())
        startActivity(intent)
    }

    fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }
}