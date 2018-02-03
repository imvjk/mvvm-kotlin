/**
 * Copyright (C) VijayK
 */
package example.com.mvvmmoviedb.moviedetail

import android.app.Application
import example.com.mvvmmoviedb.RobolectricTest
import example.com.mvvmmoviedb.data.DataSource
import example.com.mvvmmoviedb.data.Repository
import example.com.mvvmmoviedb.data.model.Movie
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.powermock.core.classloader.annotations.PrepareForTest

@PrepareForTest(Repository::class)
class MovieDetailViewModelTest : RobolectricTest() {

    @Mock
    private lateinit var repository: Repository
    @Mock
    private lateinit var context: Application
    @Captor
    private lateinit var loadMoviesCallbackCaptor: ArgumentCaptor<DataSource.GetMoviesCallback>
    private lateinit var movieDetailViewModel: MovieDetailViewModel
    private lateinit var movie: Movie
    private val startIndex = 1

    @Before
    fun setupMovieDetailViewModel() {
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

        movie = Movie(1, "title", "path", "date", 1.toDouble(), "overview")

        // Get a reference to the class under test
        movieDetailViewModel = MovieDetailViewModel(context, repository)
    }

    @Test
    fun getMovieAndLoadIntoView() {

        movieDetailViewModel.start(movie)

        // Then verify that the view was notified
        assertEquals(movieDetailViewModel.movie.get().title, movie.title)
        assertEquals(movieDetailViewModel.movie.get().id, movie.id)
        assertEquals(movieDetailViewModel.movie.get().overview, movie.overview)
    }
}