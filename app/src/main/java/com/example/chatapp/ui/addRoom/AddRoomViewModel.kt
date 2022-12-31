package com.example.chatapp.ui.addRoom

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.chatapp.base.BaseViewModel
import com.example.chatapp.database.addRoom
import com.example.chatapp.model.Category
import com.example.chatapp.model.Room

class AddRoomViewModel:BaseViewModel<Navigator>() {

    val title = ObservableField<String>()
    val titleError = ObservableField<String>()
    val description = ObservableField<String>()
    val descriptionError = ObservableField<String>()
    val categories = Category.getCategoryList()
    var selectedCategory = categories[0]
    val roomAdded = MutableLiveData<Boolean>()


    private fun validate(): Boolean {
        var valid = true
        if (title.get().isNullOrBlank()) {
            titleError.set("Enter Title")
            valid = false
        } else {
            titleError.set(null)
        }
        if (description.get().isNullOrBlank()) {
            descriptionError.set("Enter Description")
            valid = false
        } else {
            descriptionError.set(null)
        }
        return valid
    }

    fun createRoom(){
        if (validate()){
            val room = Room(
                name = title.get(),
                description = description.get(),
                categoryId = selectedCategory.id
            )
            showLoading.value = true
            addRoom(room , {
                showLoading.value = false
                roomAdded.value = true
            } , {
                showLoading.value = false
                messageLiveData.value = it.localizedMessage
            })
        }
    }
}