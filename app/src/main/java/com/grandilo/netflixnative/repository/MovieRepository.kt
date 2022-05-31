package com.grandilo.netflixnative.repository

import com.grandilo.netflixnative.model.MovieModel
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieRepository {

    @GET("SearchMovie/k_l662u5kv/{searchKey}")
    suspend fun searchMovie(@Path("searchKey") searchKey:String):MovieModel
}