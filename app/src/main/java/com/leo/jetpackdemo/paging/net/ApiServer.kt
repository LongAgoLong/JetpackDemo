package com.leo.jetpackdemo.paging.net

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServer {
    @GET("wxarticle/list/{id}/{page}/json")
    fun getHistoricalData(@Path("id") id: Int, @Path("page") page: Int): Call<String>
}
