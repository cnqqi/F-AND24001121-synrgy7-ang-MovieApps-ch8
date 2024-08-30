package com.synrgy.ch7.api

import com.synrgy.ch7.data.local.response.GetMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "ae090fb45011efeb32fc8f96b225a9b2",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "ae090fb45011efeb32fc8f96b225a9b2",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = "ae090fb45011efeb32fc8f96b225a9b2",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>
}