package com.uet.tsfbankingapp.Transaction

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uet.tsfbankingapp.ModelClasses.TransactionData
import com.uet.tsfbankingapp.R

class TransactionRecyclerViewAdapter(val transactionList: ArrayList<TransactionData>) : RecyclerView.Adapter<TransactionRecyclerViewAdapter.TransactionItem>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionItem {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_transaction_layout, parent, false)
        return TransactionItem(v)
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    override fun onBindViewHolder(holder: TransactionItem, position: Int) {
        holder.bindItems(transactionList[position])
    }

    class TransactionItem(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        fun bindItems(item: TransactionData) {
            val from = itemView.findViewById(R.id.from) as TextView
            val to  = itemView.findViewById(R.id.to) as TextView
            val amount  = itemView.findViewById(R.id.amount) as TextView
            val date_time  = itemView.findViewById(R.id.date_time) as TextView

            from.text = "From: " + item.from.toString()
            to.text = "To: " + item.to.toString()
            amount.text = "Amount: $" + item.amount.toString()
            date_time.text = item.data_time
        }
    }
}