package com.ominext.githubrepositoryfinder.network

import com.ominext.githubrepositoryfinder.model.SearchResponse
import retrofit2.Call
import retrofit2.http.*


interface GetDataService {

    @GET("search/repositories")
    fun search(@Query("q") queryString:String): Call<SearchResponse>
}