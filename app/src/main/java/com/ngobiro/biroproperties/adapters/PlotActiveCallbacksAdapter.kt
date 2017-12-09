package com.ngobiro.biroproperties.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ngobiro.biroproperties.R
import com.ngobiro.biroproperties.data.PlotCallback
import com.ngobiro.biroproperties.data.Town

/**
 * Created by ngobiro on 9/14/17.
 */

class  PlotActiveCallbacksAdapter(private var list: ArrayList<PlotCallback>, val listener: (PlotCallback) -> Unit): RecyclerView.Adapter<PlotActiveCallbacksAdapter.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.plotcallback,parent,false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return list.size
    }

//findViewById(R.id.section_name) as TextView


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(plotcallback: PlotCallback, listener: (PlotCallback) -> Unit) = with(itemView)  {
            var createdAt: TextView = itemView.findViewById(R.id.createdAtTxtview)
            var customerName: TextView = itemView.findViewById(R.id.customerNameTxtview)
            var customerMobileNumber: TextView = itemView.findViewById(R.id.customerMobilenumberTxtview)
            var plotDetails: TextView = itemView.findViewById(R.id.plotDetailsTxtview)
            createdAt.text = "Created On: ${plotcallback.created_at.toString()}"
            customerName.text = "Customer Name: ${plotcallback.customer_name.toString()}"
            customerMobileNumber.text = "Customer Mobile: ${plotcallback.mobile_number}"
            plotDetails.text = "Subject: ${plotcallback.plot_details}"
            setOnClickListener { listener(plotcallback) }


        }




    }

}