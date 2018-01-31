/**
 * Copyright (C) Vijay Kumar Yadav.
 */
package example.com.mvvmmoviedb.di

import android.content.Context
import example.com.mvvmmoviedb.data.Repository
import example.com.mvvmmoviedb.data.remote.ApiService
import example.com.mvvmmoviedb.data.remote.RemoteDataSource
import example.com.mvvmmoviedb.data.remote.RemoteDataSource.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Inject the dependency to create Repository. Useful for testing.
 */
object Injection {

    fun provideTasksRepository(context: Context): Repository {

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val apiService = retrofit.create(ApiService::class.java)
        return Repository.getInstance(RemoteDataSource.getInstance(apiService))
    }
}
