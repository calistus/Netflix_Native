package com.grandilo.netflixnative.model

data class Result(
    val description: String,
    val id: String,
    val image: String,
    val resultType: String,
    val title: String
) {
    override fun toString(): String {
        return "Result(description='$description', id='$id', image='$image', resultType='$resultType', title='$title')"
    }
}