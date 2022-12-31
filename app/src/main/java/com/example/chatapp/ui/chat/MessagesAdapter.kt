package com.example.chatapp.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.DataUtils
import com.example.chatapp.R
import com.example.chatapp.databinding.ItemMessageRecievedBinding
import com.example.chatapp.databinding.ItemMessageSentBinding
import com.example.chatapp.model.Message

class MessagesAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var items = mutableListOf<Message?>()
    val RECIEVED = 1;
    val SENT = 2;

    override fun getItemViewType(position: Int): Int {
        val message = items.get(position)
        if (message?.senderId == DataUtils.user?.id){
            return SENT
        } else {
            return RECIEVED
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == RECIEVED){
            val itemBinding:ItemMessageRecievedBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_message_recieved,
                parent , false
            )
            return RecievedMessageViewHolder(itemBinding)
        } else {
            val itemBinding:ItemMessageSentBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_message_sent,
                parent , false
            )
            return SentMessageViewHolder(itemBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RecievedMessageViewHolder){
            holder.bind(items.get(position))
        } else if (holder is SentMessageViewHolder){
            holder.bind(items.get(position))
        }
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    fun appendMessages(newMessagesList: MutableList<Message>) {
        items.addAll(newMessagesList)
        notifyItemRangeInserted(items.size + 1 , newMessagesList.size)
    }

    class SentMessageViewHolder(val viewDataBinding: ItemMessageSentBinding)
        :RecyclerView.ViewHolder(viewDataBinding.root){
            fun bind(message: Message?){
                viewDataBinding.message = message
                viewDataBinding.executePendingBindings()
            }
    }
    class RecievedMessageViewHolder(val viewDataBinding: ItemMessageRecievedBinding)
        :RecyclerView.ViewHolder(viewDataBinding.root){
        fun bind(message: Message?){
            viewDataBinding.message = message
            viewDataBinding.executePendingBindings()
        }
    }
}