package com.ngobiro.biroproperties.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ngobiro.biroproperties.data.Location
import com.ngobiro.biroproperties.R
import com.ngobiro.biroproperties.data.Town

/**
 * Created by ngobiro on 9/2/17.
 */


class  LocationsAdapter(private var list: ArrayList<Location>, val listener: (Location) -> Unit): RecyclerView.Adapter<LocationsAdapter.ViewHolder>(){
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.locations_list,parent,false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    //findViewById(R.id.section_name) as TextView


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(location: Location, listener: (Location) -> Unit) = with(itemView)  {
            var section_name: TextView = itemView.findViewById(R.id.location_name)
            section_name.text = location.name.toString()
            setOnClickListener { listener(location) }


        }




    }



    fun filterList(filterdLocations: ArrayList<Location>) {
        this.list = filterdLocations
        notifyDataSetChanged()
    }


}
