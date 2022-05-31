package com.grandilo.netflixnative.repository

import com.grandilo.netflixnative.model.MovieModel
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieRepository {

    @GET("/SearchMovie/{searchKey}")
    suspend fun searchMovie(@Path("searchKey") searchKey:String):MovieModel
}