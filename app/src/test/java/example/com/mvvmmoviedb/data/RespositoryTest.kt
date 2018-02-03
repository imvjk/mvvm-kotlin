/**
 * Copyright (C) Vijay Kumar Yadav.
 */
package example.com.mvvmmoviedb.data

import example.com.mvvmmoviedb.RobolectricTest
import example.com.mvvmmoviedb.data.model.Movie
import example.com.mvvmmoviedb.data.model.Movies
import example.com.mvvmmoviedb.data.remote.RemoteDataSource
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.configuration.any
import org.mockito.configuration.capture
import org.mockito.configuration.eq
import org.powermock.core.classloader.annotations.PrepareForTest

/**
 * Test for Respository class.
 */
@PrepareForTest(RemoteDataSource::class)
class RespositoryTest : RobolectricTest() {

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    private lateinit var repository: Repository

    @Captor
    private lateinit var callbackCaptor: ArgumentCaptor<DataSource.GetMoviesCallback>

    @Mock
    private lateinit var loadMoviesCallback: DataSource.GetMoviesCallback

    @Before
    fun setupRepository() {
        MockitoAnnotations.initMocks(this)
        // Get a reference to the class under test
        repository = Repository.getInstance(remoteDataSource)
    }


    @After
    fun destroyRepositoryInstance() {
        Repository.destroyInstance()
    }

    @Test
    fun getMoviesTestWhenOperationFailed() {
        repository.getMovies(1, false, loadMoviesCallback)
        // verify remote data source api is called.
        Mockito.verify(remoteDataSource).getMovies(eq(1), eq(false), capture(callbackCaptor))
        // triggered operation failed
        callbackCaptor.value.onOperationFailed();
        // verify callback called
        Mockito.verify<DataSource>(remoteDataSource).getMovies(eq(1), eq(false),
                any<DataSource.GetMoviesCallback>())

    }

    @Test
    fun getMoviesTestWhenOperationSuccessful() {
        repository.getMovies(1, false, loadMoviesCallback)
        // verify remote data source api is called.
        Mockito.verify(remoteDataSource).getMovies(eq(1), eq(false), capture(callbackCaptor))
        // triggered operation Complete
        callbackCaptor.value.onOperationComplete(Movies(1, ArrayList<Movie>()))
        // verify callback called
        Mockito.verify<DataSource>(remoteDataSource).getMovies(eq(1), eq(false),
                any<DataSource.GetMoviesCallback>())

    }
}