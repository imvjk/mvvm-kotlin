/**
 * Copyright (C) Vijay Kumar Yadav.
 */
package example.com.mvvmmoviedb.movies

import android.app.Application
import com.google.common.collect.Lists
import example.com.mvvmmoviedb.RobolectricTest
import example.com.mvvmmoviedb.data.DataSource
import example.com.mvvmmoviedb.data.Repository
import example.com.mvvmmoviedb.data.model.Movie
import example.com.mvvmmoviedb.data.model.Movies
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Matchers.eq
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.configuration.capture
import org.powermock.core.classloader.annotations.PrepareForTest

@PrepareForTest(Repository::class)
class MoviesViewModelTest : RobolectricTest() {

    @Mock
    private lateinit var repository: Repository
    @Mock
    private lateinit var context: Application
    @Captor
    private lateinit var loadMoviesCallbackCaptor: ArgumentCaptor<DataSource.GetMoviesCallback>
    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var movies: List<Movie>
    private val startIndex = 1

    @Before
    fun setupTasksViewModel() {
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

        // Get a reference to the class under test
        moviesViewModel = MoviesViewModel(context, repository)

        // initialise the movie
        val movie = Movie(1, "title", "path", "date", 1.toDouble(), "overview")
        movies = Lists.newArrayList(movie)
    }

    @Test
    fun loadMoviesFromRepositoryWithDefaultFilter() {
        with(moviesViewModel) {
            loadMovies(startIndex)
            // Callback is captured and invoked with stubbed tasks
            verify<Repository>(repository).getMovies(eq(startIndex), eq(false),
                    capture(loadMoviesCallbackCaptor))


            // Then progress indicator is shown
            assertTrue(dataLoading.get())
            loadMoviesCallbackCaptor.value.onOperationComplete(Movies(startIndex, movies))

            // Then progress indicator is hidden
            assertFalse(dataLoading.get())

            // And data loaded
            assertFalse(items.isEmpty())
            assertTrue(items.size == 1)
        }
    }

    @Test
    fun loadMoviesFromRepositoryWithLatestFilter() {
        with(moviesViewModel) {

            loadMovies(startIndex, true)
            // Callback is captured and invoked with stubbed tasks
            verify<Repository>(repository).getMovies(eq(startIndex), eq(true),
                    capture(loadMoviesCallbackCaptor))


            // Then progress indicator is shown
            assertTrue(dataLoading.get())
            loadMoviesCallbackCaptor.value.onOperationComplete(Movies(startIndex, movies))

            // Then progress indicator is hidden
            assertFalse(dataLoading.get())

            // And data loaded
            assertFalse(items.isEmpty())
            assertTrue(items.size == 1)
        }
    }
}
