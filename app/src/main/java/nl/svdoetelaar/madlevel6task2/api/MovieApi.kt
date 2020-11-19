package nl.svdoetelaar.madlevel6example.api

import android.content.Context
import nl.svdoetelaar.madlevel6task2.R
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieApi {
    companion object {
        fun createApi(context: Context): MovieApiService {
            val baseUrl = context.getString(R.string.movie_api_url)

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .addInterceptor(ApiInterceptor(context))
                .build()

            val triviaApi = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return triviaApi.create(MovieApiService::class.java)
        }
    }

    class ApiInterceptor(private val context: Context) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            val url = request.url.newBuilder()
                .addQueryParameter("api_key", context.getString(R.string.movie_api_key)).build()
            request = request.newBuilder().url(url).build()

            return chain.proceed(request)
        }
    }
}

