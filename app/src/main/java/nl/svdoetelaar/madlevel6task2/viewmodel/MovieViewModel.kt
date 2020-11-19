package nl.svdoetelaar.madlevel6example.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nl.svdoetelaar.madlevel6example.repository.MovieRepository

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val movieRepository = MovieRepository(application.applicationContext)
    val trivia = movieRepository.movie
    private val _errorText: MutableLiveData<String> = MutableLiveData()
    val errorText: LiveData<String> get() = _errorText

    fun getTriviaNumber(year: Int) {
        viewModelScope.launch {
            try {
                movieRepository.getMovies(year)
            } catch (error: MovieRepository.MovieRefreshError) {
                _errorText.value = error.message
                Log.e("Movie error", error.cause.toString())
            }
        }
    }
}