/**
 * Copyright (C) Vijay Kumar Yadav.
 */
package example.com.mvvmmoviedb.moviedetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import example.com.mvvmmoviedb.R
import example.com.mvvmmoviedb.data.model.Movie
import example.com.mvvmmoviedb.util.obtainViewModel
import example.com.mvvmmoviedb.util.replaceFragmentInActivity

/**
 * Activity class for movie detail.
 */
class MovieDetailActivity : AppCompatActivity() {

    companion object {
        const val MOVIE = "Movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewFragment()
    }

    private fun setupViewFragment() {
        supportFragmentManager.findFragmentById(R.id.contentFrame)
                ?: MovieDetailFragment.newInstance(intent.getSerializableExtra(MOVIE) as Movie).let {
                    replaceFragmentInActivity(it, R.id.contentFrame)
                }
    }

    fun obtainViewModel(): MovieDetailViewModel = obtainViewModel(MovieDetailViewModel::class.java)
}