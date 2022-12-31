package com.example.chatapp.ui.login

import android.content.Intent
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.chatapp.DataUtils
import com.example.chatapp.base.BaseViewModel
import com.example.chatapp.database.signIn
import com.example.chatapp.model.AppUser
import com.example.chatapp.ui.register.RegisterActivity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.time.temporal.ValueRange

class LoginViewModel : BaseViewModel<Navigator>() {

    val email = ObservableField<String>()
    val emailError = ObservableField<String>()
    val password = ObservableField<String>()
    val passwordError = ObservableField<String>()
    val auth = Firebase.auth


    fun login() {
        if (validate()) {
            checkAccountInFireBase()
        }
    }

    private fun checkAccountInFireBase() {
        showLoading.value = true
        auth.signInWithEmailAndPassword(email.get()!!, password.get()!!)
            .addOnCompleteListener { task ->
                showLoading.value = false
                if (!task.isSuccessful) {
                    //show error message
                    messageLiveData.value = task.exception!!.localizedMessage
                } else {
                    //show success message
                    //messageLiveData.value = "success Login"
                    //navigator?.openHomeScreen()
                    checkUserFromFirestore(task.result.user?.uid)
                }
            }
    }

    private fun checkUserFromFirestore(uid: String?) {
        showLoading.value = true
        signIn(uid!!, OnSuccessListener { docSnapshot ->
            showLoading.value = false
            val user = docSnapshot.toObject(AppUser::class.java)
            if (user == null) {
                messageLiveData.value = "Valid Email or Password"
                return@OnSuccessListener
            } else {
                DataUtils.user = user
                navigator?.openHomeScreen()
            }
        }, onFailureListener = {
            showLoading.value = false
            messageLiveData.value = it.localizedMessage
        })
    }


    private fun validate(): Boolean {
        var valid = true
        if (email.get().isNullOrBlank()) {
            emailError.set("Enter Email")
            valid = false
        } else {
            emailError.set(null)
        }
        if (password.get().isNullOrBlank()) {
            passwordError.set("Enter Password")
            valid = false
        } else {
            passwordError.set(null)
        }
        return valid
    }

    fun openRegister() {
        navigator?.openRegisterScreen()
    }
}