package com.ngobiro.biroproperties.adapters

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ngobiro.biroproperties.R
import com.ngobiro.biroproperties.R.id.imageView
import com.ngobiro.biroproperties.data.Plot
import com.ngobiro.biroproperties.data.Town
import java.text.SimpleDateFormat


/**
 * Created by ngobiro on 9/5/17.
 */



class  PlotsAdapter(private var list: ArrayList<Plot>, val listener: (Plot) -> Unit): RecyclerView.Adapter<PlotsAdapter.ViewHolder>(){


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.plots_list,parent,false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return list.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(plot: Plot, listener: (Plot) -> Unit) = with(itemView)  {
            var plot__title: TextView = itemView.findViewById(R.id.plot_title)
            var plot__size: TextView = itemView.findViewById(R.id.plot_size)
            var plot__price: TextView = itemView.findViewById(R.id.plot_price)
            var plot_image : ImageView = itemView.findViewById(R.id.plot_picture)
            var plot_location : TextView = itemView.findViewById(R.id.plot_location)
            var plot_posting_date : TextView = itemView.findViewById(R.id.plot_posting_date_textView)
            val DATE_FORMAT = SimpleDateFormat("dd-MM-yyyy")
            plot__title.text = plot.title.toString()
            plot__size.text = "Plot Size: ${plot.size.toString()}"
            plot__price.text = "Price Kshs: ${plot.price.toString()}"
            plot_posting_date.text = "Posted On: ${DATE_FORMAT.format(plot.posted_on)}"
            plot_location.text = plot.location.toString()

            if (plot.picture != null) {
                Glide.with(context)
                        .load(plot.picture)
                        .placeholder(R.drawable.loading_plot_image)
                        .centerCrop()
                        .crossFade()
                        .into(plot_image)

            }


            else{

                Glide.with(context)
                        .load(R.drawable.no_plot_image)
                        .centerCrop()
                        .crossFade()
                        .into(plot_image)

            }

            setOnClickListener { listener(plot) }




        }




    }


    fun filterList(filteredPlot: ArrayList<Plot>) {
        this.list = filteredPlot
        notifyDataSetChanged()
    }











}