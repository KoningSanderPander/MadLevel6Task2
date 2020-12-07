package nl.svdoetelaar.madlevel6task2.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

class MovieJsonResponse {

    companion object {
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original"
    }

    data class Root(
        @SerializedName("page") val page: Int,
        @SerializedName("total_results") val totalResults: Int,
        @SerializedName("total_pages") val totalPages: Int,
        @SerializedName("results") val results: List<Movie>
    )

    @Parcelize
    data class Movie(
        @SerializedName("popularity") val popularity: Double,
        @SerializedName("vote_count") val voteCount: Int,
        @SerializedName("video") val video: Boolean,
        @SerializedName("poster_path") val posterPath: String,
        @SerializedName("id") val id: Int,
        @SerializedName("adult") val adult: Boolean,
        @SerializedName("backdrop_path") val backdropPath: String,
        @SerializedName("original_language") val originalLanguage: String,
        @SerializedName("original_title") val originalTitle: String,
        @SerializedName("genre_ids") val genreIds: List<Int>,
        @SerializedName("title") val title: String,
        @SerializedName("vote_average") val voteAverage: Double,
        @SerializedName("overview") val overview: String,
        @SerializedName("release_date") val releaseDate: String
    ) : Parcelable {
        fun getMovieImageUrl(image_path: String): String {
            return IMAGE_BASE_URL + image_path
        }
    }
}
