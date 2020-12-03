package nl.svdoetelaar.madlevel6example.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.withTimeout
import nl.svdoetelaar.madlevel6example.api.MovieApi
import nl.svdoetelaar.madlevel6example.api.MovieApiService
import nl.svdoetelaar.madlevel6task2.model.MovieJsonResponse.Movie

class MovieRepository(context: Context) {
    private val movieApiService: MovieApiService = MovieApi.createApi(context)
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    suspend fun getMovies(year: Int) {
        try {
            val result = withTimeout(5_000) {
                movieApiService.getMovies(year.toString())
            }

            _movies.value = result.results
        } catch (error: Throwable) {
            throw MovieRefreshError("Unable to refresh trivia", error)
        }

    }

    class MovieRefreshError(message: String, cause: Throwable) : Throwable(message, cause)
}