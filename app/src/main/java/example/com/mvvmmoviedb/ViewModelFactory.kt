/**
 * Copyright (C) Vijay Kumar Yadav.
 */
package example.com.mvvmmoviedb

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.annotation.VisibleForTesting
import example.com.mvvmmoviedb.data.Repository
import example.com.mvvmmoviedb.di.Injection
import example.com.mvvmmoviedb.moviedetail.MovieDetailViewModel
import example.com.mvvmmoviedb.movies.MoviesViewModel

/**
 * Factory class to create View Models.
 */
class ViewModelFactory private constructor(
        private val application: Application,
        private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
            with(modelClass) {
                when {
                    isAssignableFrom(MoviesViewModel::class.java) ->
                        MoviesViewModel(application, repository)
                    isAssignableFrom(MovieDetailViewModel::class.java) ->
                        MovieDetailViewModel(application, repository)
                    else ->
                        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            } as T

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) =
                INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                    INSTANCE ?: ViewModelFactory(application,
                            Injection.provideTasksRepository(application.applicationContext))
                            .also { INSTANCE = it }
                }


        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
