/**
 * Copyright (C) Vijay Kumar Yadav.
 */
package example.com.mvvmmoviedb.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data class for list of movies.
 */
data class Movies constructor(
        @SerializedName("page")
        var page: Int,
        @SerializedName("results")
        var movies: List<Movie>
) {

}