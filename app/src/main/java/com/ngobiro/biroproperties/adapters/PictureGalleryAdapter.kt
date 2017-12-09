package com.ngobiro.biroproperties.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ngobiro.biroproperties.R
import com.ngobiro.biroproperties.activities.PlotDetailsActivity
import com.ngobiro.biroproperties.activities.PlotImageSelectionActivity
import com.ngobiro.biroproperties.data.Picture
import com.ngobiro.biroproperties.data.Plot


/**
 * Created by ngobiro on 9/16/17.
 */

class  PictureGalleryAdapter(private var list: ArrayList<Picture>, val listener: (Picture) -> Unit): RecyclerView.Adapter<PictureGalleryAdapter.ViewHolder>(){

    override fun onBindViewHolder(holder: PictureGalleryAdapter.ViewHolder, position: Int) {
        holder.bind(list[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.images,parent,false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return list.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(picture: Picture,listener: (Picture) -> Unit) = with(itemView)  {


            var image : ImageView = itemView.findViewById(R.id.iv_photo)


                image.setImageBitmap(picture.bitmap)

          setOnClickListener { listener(picture) }




        }




    }

}