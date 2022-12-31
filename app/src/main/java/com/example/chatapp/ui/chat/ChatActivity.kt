package com.example.chatapp.ui.chat

import android.os.BugreportManager
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.Constants
import com.example.chatapp.R
import com.example.chatapp.base.BaseActivity
import com.example.chatapp.database.getMessageRef
import com.example.chatapp.databinding.ActivityChatBinding
import com.example.chatapp.model.Message
import com.example.chatapp.model.Room
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.Query

class ChatActivity : BaseActivity<ActivityChatBinding,ChatViewModel>() {

    lateinit var room: Room
    val adapter = MessagesAdapter()
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        room = intent.getParcelableExtra(Constants.EXTRA_ROOM)!!
        viewModel.room = room
        viewDataBinding.vm = viewModel
        layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        viewDataBinding.messageRecyclerview.layoutManager = layoutManager
        viewDataBinding.messageRecyclerview.adapter = adapter
        listenForMessagesUpdates()
    }

    fun listenForMessagesUpdates(){
        getMessageRef(room.id!!)
            .orderBy("dateTime" , Query.Direction.ASCENDING)
            .addSnapshotListener{ snapshots, excepthion ->
                if (excepthion != null){
                    Toast.makeText(this,"can't retrieve messages" , Toast.LENGTH_LONG)
                        .show()
                } else {
                    val newMessagesList = mutableListOf<Message>()
                    for (dc in snapshots!!.documentChanges) {
                        when (dc.type) {
                            DocumentChange.Type.ADDED -> {
                                val message = dc.document.toObject(Message::class.java)
                                newMessagesList.add(message)
                            }
//                            DocumentChange.Type.MODIFIED -> Log.d(TAG, "Modified city: ${dc.document.data}")
//                            DocumentChange.Type.REMOVED -> Log.d(TAG, "Removed city: ${dc.document.data}")
                            else -> {}
                        }
                    }
                    adapter.appendMessages(newMessagesList)
                    viewDataBinding.messageRecyclerview.smoothScrollToPosition(adapter.itemCount)
                }
            }
        }

    override fun getLayoutId(): Int {
        return R.layout.activity_chat
    }

    override fun initViewModel(): ChatViewModel {
        return ViewModelProvider(this)[ChatViewModel::class.java]
    }
}