package com.example.chatapp.model

import com.example.chatapp.R

data class Category (
    val id:String? = null,
    val name:String? = null,
    val imageId:Int? = null){

    companion object{
        const val MUSIC = "music"
        const val SPORTS = "sports"
        const val MOVIES = "movies"
        fun fromId(categoryId:String):Category{
            when(categoryId){
                MUSIC->{
                    return Category(
                        id = MUSIC,
                        name = "Music",
                        imageId = R.drawable.music_ic
                    )
                } SPORTS->{
                    return Category(
                        id = SPORTS,
                        name = "Sports",
                        imageId = R.drawable.sports_ic
                    )
                } else -> {
                    return Category(
                        id = MOVIES,
                        name = "Movies",
                        imageId = R.drawable.movie_ic
                    )
                }
            }
        }
        fun getCategoryList():List<Category>{
            return listOf(
                fromId(MUSIC),
                fromId(SPORTS),
                fromId(MOVIES)
            )
        }
    }
}