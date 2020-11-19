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
        @SerializedName("total_results") val total_results: Int,
        @SerializedName("total_pages") val total_pages: Int,
        @SerializedName("results") val results: List<Movie>
    )

    @Parcelize
    data class Movie(
        @SerializedName("popularity") val popularity: Double,
        @SerializedName("vote_count") val vote_count: Int,
        @SerializedName("video") val video: Boolean,
        @SerializedName("poster_path") val poster_path: String,
        @SerializedName("id") val id: Int,
        @SerializedName("adult") val adult: Boolean,
        @SerializedName("backdrop_path") val backdrop_path: String,
        @SerializedName("original_language") val original_language: String,
        @SerializedName("original_title") val original_title: String,
        @SerializedName("genre_ids") val genre_ids: List<Int>,
        @SerializedName("title") val title: String,
        @SerializedName("vote_average") val vote_average: Double,
        @SerializedName("overview") val overview: String,
        @SerializedName("release_date") val release_date: String
    ) : Parcelable {
        fun getMovieImageUrl(image_path: String): String {
            return IMAGE_BASE_URL + image_path
        }
    }
}
