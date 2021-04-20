package com.uet.tsfbankingapp.SelectUser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uet.tsfbankingapp.ModelClasses.UserData
import com.uet.tsfbankingapp.R

class UserSelectRecyclerViewAdapter(val userList: ArrayList<UserData>, var toUserId: Array<Int>) : RecyclerView.Adapter<UserSelectRecyclerViewAdapter.UserSelectItem>(){

    var selected: Button? = null
    var index: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserSelectItem {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_select_user_layout, parent, false)
        return UserSelectItem(
            v
        )
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserSelectItem, position: Int) {

        holder.selectBtn.setBackgroundResource(R.drawable.unselected_tick)

        if(position==index)
        {
            holder.selectBtn.setBackgroundResource(R.drawable.selected_tick)
        }

        val user: UserData = userList[position]
        holder.userName.text = user.name
        holder.userEmail.text = user.email

        holder.itemView.setOnClickListener {
            if(selected!=null)
            {
                selected!!.setBackgroundResource(R.drawable.unselected_tick)
                holder.selectBtn.setBackgroundResource(R.drawable.selected_tick)
                selected = holder.selectBtn
                index = position
                toUserId[0] = user.id
                toUserId[1] = user.amount
            }
            else
            {
                holder.selectBtn.setBackgroundResource(R.drawable.selected_tick)
                selected = holder.selectBtn
                index = position
                toUserId[0] = user.id
                toUserId[1] = user.amount
            }
        }
    }

    class UserSelectItem(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val userName: TextView
        val userEmail: TextView
        val selectBtn: Button

        init {
            userName = itemView.findViewById(R.id.user_name) as TextView
            userEmail  = itemView.findViewById(R.id.user_email) as TextView
            selectBtn  = itemView.findViewById(R.id.selected_btn) as Button
        }
    }
}