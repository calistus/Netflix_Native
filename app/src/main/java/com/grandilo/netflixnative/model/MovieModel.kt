package com.grandilo.netflixnative.model

data class MovieModel(
    val errorMessage: String,
    val expression: String,
    val results: List<Result>,
    val searchType: String
)