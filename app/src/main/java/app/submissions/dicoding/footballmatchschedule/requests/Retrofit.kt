package app.submissions.dicoding.footballmatchschedule.requests

import app.submissions.dicoding.footballmatchschedule.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {

  private val loggingInterceptor: HttpLoggingInterceptor =
      HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

  private val okClient: OkHttpClient =
      OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

  val client: Retrofit = Retrofit.Builder()
      .baseUrl(BuildConfig.BASE_URL)
      .client(okClient)
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(GsonConverterFactory.create())
      .build()
}