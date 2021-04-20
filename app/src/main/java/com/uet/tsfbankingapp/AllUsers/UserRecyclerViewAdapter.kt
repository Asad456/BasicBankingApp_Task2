package com.uet.tsfbankingapp.AllUsers

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uet.tsfbankingapp.ModelClasses.UserData
import com.uet.tsfbankingapp.R
import com.uet.tsfbankingapp.SingleUserActivity

class UserRecyclerViewAdapter(val userList: ArrayList<UserData>) : RecyclerView.Adapter<UserRecyclerViewAdapter.UserItem>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItem {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_user_layout, parent, false)
        return UserItem(v)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserItem, position: Int) {
        holder.bindItems(userList[position])
    }

    class UserItem(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        fun bindItems(user: UserData) {
            val userName = itemView.findViewById(R.id.user_name) as TextView
            val userEmail  = itemView.findViewById(R.id.user_email) as TextView
            val amount  = itemView.findViewById(R.id.total_amount) as TextView
            userName.text = user.name
            userEmail.text = user.email
            amount.text = "Total Amount: $" + user.amount.toString()

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, SingleUserActivity::class.java)
                intent.putExtra("id",user.id)
                intent.putExtra("name",user.name)
                intent.putExtra("email",user.email)
                intent.putExtra("amount",user.amount)
                itemView.context.startActivity(intent)
            }
        }
    }
}