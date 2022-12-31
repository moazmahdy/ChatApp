package com.example.chatapp.model

import android.accounts.AuthenticatorDescription
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room(
    var id:String? = null,
    var name:String? = null,
    var description:String? = null,
    var categoryId:String? = null,
):Parcelable{

    fun getCategoryImageId():Int? {
        return Category.fromId(categoryId?:Category.SPORTS).imageId
    }
    companion object{
        const val COLLECTION_NAME = "Rooms"
    }
}
