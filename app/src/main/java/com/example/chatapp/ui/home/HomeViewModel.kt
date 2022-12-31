package com.example.chatapp.ui.home

import com.example.chatapp.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel: BaseViewModel<Navigator>() {


    fun createRoom(){
        navigator?.gotoAddRoom()
    }

    fun logout(){
        FirebaseAuth.getInstance().signOut();
        navigator?.openLogin()
    }
}