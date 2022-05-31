package com.grandilo.netflixnative.model

data class MovieModel(
    val errorMessage: String,
    val expression: String,
    val results: List<Result>,
    val searchType: String
) {
    override fun toString(): String {
        return "MovieModel(errorMessage='$errorMessage', expression='$expression', results=$results, searchType='$searchType')"
    }
}