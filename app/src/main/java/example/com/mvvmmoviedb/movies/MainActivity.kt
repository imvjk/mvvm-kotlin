/**
 * Copyright (C) Vijay Kumar Yadav.
 */
package example.com.mvvmmoviedb.movies

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import example.com.mvvmmoviedb.R
import example.com.mvvmmoviedb.data.model.Movie
import example.com.mvvmmoviedb.moviedetail.MovieDetailActivity
import example.com.mvvmmoviedb.util.obtainViewModel
import example.com.mvvmmoviedb.util.replaceFragmentInActivity

/**
 * Main & Launcher Activity class to show list of movies.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewFragment()
        viewModel = obtainViewModel().apply {
            openMovieDetailsEvent.observe(this@MainActivity, Observer<Movie> { movie ->
                openDetailActivity(movie)
            })
        }
    }

    private fun openDetailActivity(movie: Movie?) {
        val intent = Intent(this, MovieDetailActivity::class.java).apply {
            putExtra(MovieDetailActivity.MOVIE, movie)
        }
        startActivity(intent)
    }

    private fun setupViewFragment() {
        supportFragmentManager.findFragmentById(R.id.contentFrame)
                ?: MoviesFragment.newInstance().let {
                    replaceFragmentInActivity(it, R.id.contentFrame)
                }
    }

    fun obtainViewModel(): MoviesViewModel = obtainViewModel(MoviesViewModel::class.java)
}
