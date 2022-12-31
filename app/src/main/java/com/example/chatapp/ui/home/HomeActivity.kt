package com.example.chatapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.Constants
import com.example.chatapp.R
import com.example.chatapp.base.BaseActivity
import com.example.chatapp.database.getRooms
import com.example.chatapp.databinding.ActivityHomeBinding
import com.example.chatapp.model.Room
import com.example.chatapp.ui.addRoom.AddRoomActivity
import com.example.chatapp.ui.chat.ChatActivity
import com.example.chatapp.ui.login.LoginActivity

class HomeActivity : BaseActivity<ActivityHomeBinding , HomeViewModel>() , Navigator {

    private val adapter = RoomsAdapter(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.vm = viewModel
        viewModel.navigator = this
        viewDataBinding.recyclerview.adapter = adapter

        adapter.onItemClickListener = object :RoomsAdapter.OnItemClickListener{
            override fun onItemClickListener(pos: Int, room: Room) {
                startChatActivity(room)
            }
        }
    }

    private fun startChatActivity(room: Room) {
        val intent = Intent(this , ChatActivity::class.java)
        intent.putExtra(Constants.EXTRA_ROOM , room)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        getRooms({
            val rooms = it.toObjects(Room::class.java)
            adapter.changeData(rooms)
        } , {
            Toast.makeText(this , "can't fetch rooms" , Toast.LENGTH_LONG)  .show()
        })
    }

    override fun initViewModel(): HomeViewModel {
        return ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun gotoAddRoom() {
        val intent = Intent(this , AddRoomActivity::class.java)
        startActivity(intent);
    }

    override fun openLogin() {
        Toast.makeText(this , "you're log out" , Toast.LENGTH_LONG).show()
        val intent = Intent(this , LoginActivity::class.java)
        startActivity(intent)
    }
}