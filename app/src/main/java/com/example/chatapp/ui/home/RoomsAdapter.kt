package com.example.chatapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.databinding.ItemRoomBinding
import com.example.chatapp.model.Room

class RoomsAdapter(var items:List<Room?>?) : RecyclerView.Adapter<RoomsAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding : ItemRoomBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_room , parent , false)
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items!![position])
        onItemClickListener?.let {
            holder.itemView.setOnClickListener {
                onItemClickListener?.onItemClickListener(position , items!![position]!!)
            }
        }
    }

    var onItemClickListener:OnItemClickListener? = null
    interface OnItemClickListener{
        fun onItemClickListener(pos:Int , room: Room)
    }

    override fun getItemCount(): Int {
        return items?.size ?:0
    }

    fun changeData(rooms: List<Room>) {
        items = rooms
        notifyDataSetChanged()
    }


    class ViewHolder(private val viewDataBinding: ItemRoomBinding)
        :RecyclerView.ViewHolder(viewDataBinding.root){
            fun bind(room: Room?){
                viewDataBinding.item = room
                viewDataBinding.invalidateAll()
            }
    }
}