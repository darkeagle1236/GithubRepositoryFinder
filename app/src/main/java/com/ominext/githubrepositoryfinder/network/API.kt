package com.ominext.githubrepositoryfinder.network

import com.google.gson.GsonBuilder
import com.ominext.githubrepositoryfinder.model.SearchResponse
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Query

object API {
    private const val URL = "https://api.github.com/"

    val service: AppRepository by lazy {
        val logging = HttpLoggingInterceptor()
        val gson = GsonBuilder().serializeNulls().setPrettyPrinting().create()
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(client)
            .build().create<AppRepository>(AppRepository::class.java)
    }

    interface AppRepository {

        @GET("search/repositories")
        fun search(@Query("q") queryString:String): Observable<SearchResponse>
    }
}