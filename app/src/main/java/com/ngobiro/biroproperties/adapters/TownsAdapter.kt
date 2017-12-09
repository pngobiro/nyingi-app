package com.ngobiro.biroproperties.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ngobiro.biroproperties.R
import com.ngobiro.biroproperties.data.Town


/**
 * Created by ngobiro on 9/13/17.
 */

class TownsAdapter(private var townList: ArrayList<Town>,val listener: (Town) -> Unit) : RecyclerView.Adapter<TownsAdapter.ViewHolder>(){




    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(townList[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.towns, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return townList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(town: Town, listener: (Town) -> Unit) = with(itemView) {
            val town_name: TextView = itemView.findViewById(R.id.town_name)
            town_name.text = town.name.toString()
            setOnClickListener { listener(town) }
        }

    }


    fun filterList(filterdTowns: ArrayList<Town>) {
        this.townList = filterdTowns
        notifyDataSetChanged()
    }



    }
