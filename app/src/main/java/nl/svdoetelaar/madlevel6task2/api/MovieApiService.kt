package nl.svdoetelaar.madlevel6example.api

import nl.svdoetelaar.madlevel6task2.model.MovieJsonResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("/3/discover/movie")
    suspend fun getMovies(
        @Query("year") year: String? = "",
        @Query("page") page: String? = ""
    ): MovieJsonResponse.Root
}