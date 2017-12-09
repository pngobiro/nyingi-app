package com.ngobiro.biroproperties.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ngobiro.biroproperties.R
import com.ngobiro.biroproperties.adapters.SelectionPictureGallery
import com.parse.ParseUser
import com.parse.ui.ParseLoginBuilder
import kotlinx.android.synthetic.main.activity_manage_plot_pictures.*



class ManagePlotPicturesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_plot_pictures)

        supportActionBar?.setTitle("Manage Plot Images")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)




        add_photos_to_plot_btn.setOnClickListener {

            var intent = Intent(this, PlotImageSelectionActivity::class.java)
            globalPictureList.clear()
            myAddMorePicsMode = true
            startActivity(intent)


        }


        remove_pictures_from_plot_btn.setOnClickListener {

            var intent = Intent(this, RemovePlotImagesActivity::class.java)

            startActivity(intent)



        }




    }



    override fun onOptionsItemSelected(item: MenuItem?): Boolean {


        if (item!!.itemId == android.R.id.home) {
            finish()
        }


        return super.onOptionsItemSelected(item)
    }
}
