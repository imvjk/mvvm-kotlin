/**
 * Copyright (C) Vijay Kumar Yadav.
 */
package example.com.mvvmmoviedb.data.remote

import example.com.mvvmmoviedb.BuildConfig
import example.com.mvvmmoviedb.data.DataSource
import example.com.mvvmmoviedb.data.model.Movies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Implementation of the data source that interact with server.
 */
class RemoteDataSource(val apiService: ApiService) : DataSource {

    companion object {

        internal const val BASE_URL = "https://api.themoviedb.org/3/"
        private const val API_KEY = "api_key"
        private const val PAGE = "page"
        private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/"
        internal const val SMALL_SIZE_URL = BASE_IMAGE_URL + "w185/"
        internal const val MID_SIZE_URL = BASE_IMAGE_URL + "w342/"
        private const val SORT = "sort_by"
        private const val RELEASE = "release_date.desc"
        private const val RELEASE_LESS = "release_date.lte"
        private const val YEAR = "2016"

        private var sINSTANCE: RemoteDataSource? = null


        /**
         * Returns the single instance of this class, creating it if necessary.
         */
        @JvmStatic
        fun getInstance(apiService: ApiService) =
                sINSTANCE ?: synchronized(RemoteDataSource::class.java) {
                    sINSTANCE ?: RemoteDataSource(apiService)
                            .also { sINSTANCE = it }
                }


        @JvmStatic
        fun destroyInstance() {
            sINSTANCE = null
        }
    }

    override fun getMovies(page: Int, withReleaseDate: Boolean, callback: DataSource.GetMoviesCallback) {
        val queryMap = HashMap<String, String>()
        queryMap[API_KEY] = BuildConfig.API_KEY
        if (withReleaseDate) {
            queryMap[SORT] = RELEASE
            queryMap[RELEASE_LESS] = YEAR
        }
        queryMap[PAGE] = page.toString()
        val call = apiService.getMovies(queryMap);
        call.enqueue(object : Callback<Movies> {
            override fun onFailure(call: Call<Movies>?, t: Throwable?) {
                callback.onOperationFailed(t);
            }

            override fun onResponse(call: Call<Movies>?, response: Response<Movies>?) {
                response?.run {
                    if (isSuccessful) {
                        callback.onOperationComplete(response.body())
                    } else {
                        callback.onOperationFailed()
                    }
                }
            }
        });
    }

}
