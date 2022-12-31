package com.example.chatapp.model

data class AppUser(
    var id:String?=null,
    var firstName:String?=null,
    var lastName:String?=null,
    var userName:String?=null,
    var email:String?=null
)
{
    companion object{
        const val COLLECTION_NAME = "users"
    }
}
