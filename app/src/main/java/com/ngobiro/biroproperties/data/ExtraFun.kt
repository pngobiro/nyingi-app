package com.ngobiro.biroproperties.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.parse.*
import com.parse.ParseFile
import com.parse.ParseObject
import java.io.*
import android.widget.Toast
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager




object ExtraFun{



      fun getPlotFirstPicture(plot_id: String): String? {

          val pictures = ParseQuery.getQuery<ParseObject>("Pictures")
          val plots = ParseQuery.getQuery<ParseObject>("Plots")
          val selectedPlot = plots[plot_id]
          pictures.whereEqualTo("plot", selectedPlot)
          try {

              var imageUrl: ParseObject? = pictures.first
              var image = imageUrl?.get("image") as ParseFile
              return image.url.toString()

          } catch (e: ParseException) {

              e.printStackTrace()
          }


          return null


      }








}



















