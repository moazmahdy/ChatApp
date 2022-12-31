package com.example.chatapp.ui.chat

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.chatapp.DataUtils
import com.example.chatapp.base.BaseViewModel
import com.example.chatapp.database.addMessage
import com.example.chatapp.model.Message
import com.example.chatapp.model.Room
import java.util.*

class ChatViewModel: BaseViewModel<Navigator>() {

    val messageField = ObservableField<String>()
    val toastLiveData = MutableLiveData<String>()
    var room: Room?=null
    fun sendMessage(){
        val message = Message(
            content = messageField.get(),
            roomId = room?.id,
            senderId = DataUtils.user?.id,
            senderName = DataUtils.user?.userName,
            dateTime = Date().time
        )
        addMessage(message,
        onSuccessListener = {
            messageField.set("")
        }, onFailureListener = {
                toastLiveData.value = "something wrong, try again later"
            })
    }
}