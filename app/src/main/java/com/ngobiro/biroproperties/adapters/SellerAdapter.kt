package com.ngobiro.biroproperties.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ngobiro.biroproperties.R
import com.ngobiro.biroproperties.data.Seller


/**
 * Created by ngobiro on 9/14/17.
 */


class  SellerAdapter(private var list: ArrayList<Seller>, val listener: (Seller) -> Unit): RecyclerView.Adapter<SellerAdapter.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.sellers,parent,false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return list.size
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(seller: Seller, listener: (Seller) -> Unit) = with(itemView)  {
            var seller_name: TextView = itemView.findViewById(R.id.seller_name)
            seller_name.text = seller.name.toString()
            setOnClickListener { listener(seller) }


        }




    }

}