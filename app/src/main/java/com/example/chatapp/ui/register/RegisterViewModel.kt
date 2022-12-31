package com.example.chatapp.ui.register

import androidx.databinding.ObservableField
import com.example.chatapp.DataUtils
import com.example.chatapp.base.BaseViewModel
import com.example.chatapp.database.addUserToFirestore
import com.example.chatapp.model.AppUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterViewModel : BaseViewModel<Navigator>() {

    val firstName = ObservableField<String>()
    val firstNameError = ObservableField<String>()
    val lastName = ObservableField<String>()
    val lastNameError = ObservableField<String>()
    val userName = ObservableField<String>()
    val userNameError = ObservableField<String>()
    val email = ObservableField<String>()
    val emailError = ObservableField<String>()
    val password = ObservableField<String>()
    val passwordError = ObservableField<String>()

    private val auth = Firebase.auth

    fun createAccount() {
        if (validate()) {
            addAccountToFireBase()
        }
    }

    private fun addAccountToFireBase() {
        showLoading.value = true
        auth.createUserWithEmailAndPassword(email.get()!!, password.get()!!)
            .addOnCompleteListener { task ->
                showLoading.value = false
                if (!task.isSuccessful) {
                    //show error message
                    messageLiveData.value = task.exception!!.localizedMessage
                } else {
                    //show success message
                    //messageLiveData.value = "success registration"
                    //navigator?.openHomeScreen()
                    createFirestoreUser(task.result.user?.uid)
                }
            }
    }

    private fun createFirestoreUser(uid: String?) {
        showLoading.value = true
        val user = AppUser(
            id = uid,
            firstName = firstName.get(),
            lastName = lastName.get(),
            userName = userName.get(),
            email = email.get()
        )
        addUserToFirestore(user, {
            showLoading.value = false
            DataUtils.user = user
            navigator?.openHomeScreen()
        }, {
            showLoading.value = false
            messageLiveData.value = it.localizedMessage
        })
    }

    private fun validate(): Boolean {
        var valid = true
        if (firstName.get().isNullOrBlank()) {
            firstNameError.set("Enter First Name")
            valid = false
        } else {
            firstNameError.set(null)
        }
        if (lastName.get().isNullOrBlank()) {
            lastNameError.set("Enter last Name")
            valid = false
        } else {
            lastNameError.set(null)
        }
        if (userName.get().isNullOrBlank()) {
            userNameError.set("Enter User Name")
            valid = false
        } else {
            userNameError.set(null)
        }
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
}