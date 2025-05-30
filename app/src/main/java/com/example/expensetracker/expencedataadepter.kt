package com.example.expensetracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Expencedataadepter(private var expences: List<expensedbvar>) :
    RecyclerView.Adapter<Expencedataadepter.Expenceviewholder>() {

    class Expenceviewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCategoryShow: TextView = itemView.findViewById(R.id.categoryshowTV)
        val tvPaymentMethodShow: TextView = itemView.findViewById(R.id.paymentmethodshowTV)
        val tvDateShow: TextView = itemView.findViewById(R.id.dateshowTV)
        val tvAmountShow: TextView = itemView.findViewById(R.id.amountshowTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Expenceviewholder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.expenseshow_view, parent, false)
        return Expenceviewholder(view)
    }

    override fun onBindViewHolder(holder: Expenceviewholder, position: Int) {
        val expense = expences[position]
        holder.tvCategoryShow.text = expense.category
        holder.tvPaymentMethodShow.text = expense.paymentmethod

        val day = expense.day
        val month = expense.month
        val year = expense.year

        val monthstring = when (month) {
            1 -> "January"
            2 -> "February"
            3 -> "March"
            4 -> "April"
            5 -> "May"
            6 -> "June"
            7 -> "July"
            8 -> "August"
            9 -> "September"
            10 -> "October"
            11 -> "November"
            12 -> "December"
            else -> "Unknown"
        }
        holder.tvDateShow.text = "$monthstring $day, $year"
        holder.tvAmountShow.text = "₹${expense.amount}"
    }

    override fun getItemCount(): Int = expences.size

    fun refreshdata(newexpence: List<expensedbvar>) {
        expences = newexpence
        notifyDataSetChanged()
    }
}