package com.ngobiro.biroproperties.activities

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.widget.Toast
import com.ngobiro.biroproperties.R
import com.ngobiro.biroproperties.adapters.PictureGalleryAdapter
import com.ngobiro.biroproperties.data.Picture
import com.parse.*
import kotlinx.android.synthetic.main.activity_remove_plot_images.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.longToast
import org.jetbrains.anko.yesButton
import java.util.ArrayList


class RemovePlotImagesActivity : AppCompatActivity(), (Picture) -> Unit {

    override fun invoke(picture: Picture) {
        longToast("${picture.objectId}")
        alert("Are you sure you want to delete this Picture! ") {
            yesButton {
                deletePlotPicture(picture.objectId)
            }
        }.show()
    }

    private var adapter: PictureGalleryAdapter? = null
    private var pictureList: ArrayList<Picture>? = null
    private var picturelayoutManager: RecyclerView.LayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remove_plot_images)
        supportActionBar?.setTitle("Remove Plot Images")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        fetchPlotImages(selected_plot_id)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }


    private fun fetchPlotImages(selected_plot_id: String?) {

        pictureList = ArrayList<Picture>()
        picturelayoutManager = LinearLayoutManager(this)
        adapter = PictureGalleryAdapter(pictureList!!, this)
        remove_pictures_recyclerview.layoutManager = picturelayoutManager
        remove_pictures_recyclerview.adapter = adapter

        val pictures = ParseQuery.getQuery<ParseObject>("Pictures")
        val query = ParseQuery.getQuery<ParseObject>("Plots")
        var selectedPlot = query[selected_plot_id]

        pictures.whereEqualTo("plot", selectedPlot)
        pictures.findInBackground { objects, e ->
            if (objects.size > 0) {
                if (e != null) {
                    Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
                } else {
                    for (parseObject in objects) {
                        val image = parseObject.get("image") as ParseFile
                        image.getDataInBackground { data, ex ->
                            if (ex != null) {
                                Toast.makeText(applicationContext, ex.localizedMessage, Toast.LENGTH_LONG).show()
                            } else {
                                val bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
                                var picture = Picture()
                                picture.bitmap = bitmap
                                picture.objectId = parseObject.objectId
                                pictureList!!.add(picture)
                            }
                            adapter!!.notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }

    private fun deletePlotPicture(picture_id: String?) {
        val pictures = ParseQuery.getQuery<ParseObject>("Pictures")
        val picture = pictures[picture_id]
        picture.deleteEventually()
        longToast("Plot Picture Deleted")
        adapter!!.notifyDataSetChanged()

    }

}



