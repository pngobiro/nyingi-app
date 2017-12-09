package com.ngobiro.biroproperties.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.ngobiro.biroproperties.R
import com.ngobiro.biroproperties.adapters.SelectionPictureGallery
import com.ngobiro.biroproperties.data.ExtraFun
import com.ngobiro.biroproperties.data.Picture
import com.parse.ParseACL
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseUser
import com.parse.ui.ParseLoginBuilder
import kotlinx.android.synthetic.main.activity_plot_image_selection.*
import com.vlk.multimager.utils.Params
import com.vlk.multimager.activities.GalleryActivity
import com.vlk.multimager.utils.Constants
import com.vlk.multimager.utils.Image
import org.jetbrains.anko.longToast
import java.io.ByteArrayOutputStream

var globalPictureList = ArrayList<Picture>()

class PlotImageSelectionActivity : AppCompatActivity() {
    var imagesList: ArrayList<Image>? = null


    private var adapter: SelectionPictureGallery? = null
    private var pictureslayoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plot_image_selection)


        supportActionBar?.setTitle("Add Images to $myplotGlobalTitle")


        openImagesGallery()



        next_image_to_plot_btn.setOnClickListener {
            if (myAddMorePicsMode == false) {
                val intent = Intent(this, SelectTownActivity::class.java)
                startActivity(intent)
            } else {
                AddMorePicturesToPlot()
            }
        }


    }

    fun openImagesGallery() {
        val intent = Intent(this, GalleryActivity::class.java)
        val params = Params()
        params.pickerLimit = 5
        intent.putExtra(Constants.KEY_PARAMS, params)
        startActivityForResult(intent, Constants.TYPE_MULTI_PICKER)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        when (requestCode) {

            Constants.TYPE_MULTI_CAPTURE -> imagesList = intent.getParcelableArrayListExtra<Image>(Constants.KEY_BUNDLE_LIST)
            Constants.TYPE_MULTI_PICKER -> imagesList = intent.getParcelableArrayListExtra<Image>(Constants.KEY_BUNDLE_LIST)


        }

        getImages()

    }

    operator fun invoke(picture: Picture) {
        openImagesGallery()

    }


    fun getImages() {
        globalPictureList = ArrayList<Picture>()
        pictureslayoutManager = LinearLayoutManager(this, 0, false)
        adapter = SelectionPictureGallery(globalPictureList, this)
        selectedImagesRecycler.layoutManager = pictureslayoutManager
        selectedImagesRecycler.adapter = adapter

        for (y in imagesList!!) {
            val picture = Picture()
            picture.objectId = y.toString()
            picture.bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, y.uri)
            globalPictureList.add(picture)


        }

        adapter!!.notifyDataSetChanged()


    }


    fun AddMorePicturesToPlot() {

        val parseACL = ParseACL(ParseUser.getCurrentUser())
        parseACL.publicReadAccess = true
        ParseUser.getCurrentUser().acl = parseACL



        if (globalPictureList.isEmpty()) {

            Toast.makeText(applicationContext, "No Picture selected ,Please Add Picture(s)", Toast.LENGTH_LONG).show()
            val intent = Intent(this, ManagePlotPicturesActivity::class.java)
            startActivity(intent)


        } else {




           globalPictureList.forEach {  x ->

               val picture = ParseObject("Pictures")
               val byteArrayOutputStream = ByteArrayOutputStream()
               x.bitmap?.compress(Bitmap.CompressFormat.PNG, 20, byteArrayOutputStream)
               val bytes = byteArrayOutputStream.toByteArray()
               val parseFile = ParseFile("image.png", bytes)

                picture.put("image", parseFile)
                picture.put("plot", ParseObject.createWithoutData("Plots", selected_plot_id))
               picture.saveInBackground { e ->
                    if (e != null) {
                        Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(applicationContext, "Saving Plot  Picture ... ${globalPictureList.indexOf(x)}", Toast.LENGTH_LONG).show()
                    }
                }
            }

            val intent = Intent(this, MyPlotsActivity::class.java)
            startActivity(intent)
        }
        }





    }
















