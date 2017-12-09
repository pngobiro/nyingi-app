package com.ngobiro.biroproperties.activities


import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.MenuItem
import android.view.View
import com.ngobiro.biroproperties.R
import com.ngobiro.biroproperties.data.ExtraFun
import kotlinx.android.synthetic.main.activity_add_new_plot.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton



var myplotGlobalTitle = ""
var myplotGlobalPrice :Int?= null
var myplotGlobalSize = ""
var myplotGlobalDescription = ""
var myplotGlobalTownID:String? = ""
var myplotGlobalTownName:String? =""
var myplotGlobalLocationID:String? = ""
var myplotGlobalLocationName :String? = ""
var selected_plot_id:String? =""
var selected_plot_title :String? = ""

class AddNewPlotActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_plot)

        supportActionBar?.setTitle("Add New Plot...")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        myAddMorePicsMode = false
        globalPictureList.clear()




        plot_description_EditView.movementMethod = ScrollingMovementMethod()
    }


    fun add_new_plot_next(view: View) {

        if (plot_title_EditView.text.isNullOrBlank()) {
            alert("Please Enter the Plot Title") {
                yesButton { }
            }.show()

        }
        if (plot_size_EditView.text.isNullOrBlank()) {

            alert("Please Enter the Plot Size") {
                yesButton { }
            }.show()

        }

        if (plot_price_EditView.text.isNullOrBlank()) {
            alert("Please Enter the Plot Price") {
                yesButton { }
            }.show()
        }

        if (plot_description_EditView.text.isNullOrBlank()) {
            alert("Please Enter the Plot Description") {
                yesButton { }
            }.show()
        }

        else{

            myplotGlobalTitle = plot_title_EditView.text.toString()
            myplotGlobalPrice = plot_price_EditView.text.toString().toInt()
            myplotGlobalSize = plot_size_EditView.text.toString()
            myplotGlobalDescription = plot_description_EditView.text.toString()
            val intent = Intent(this, PlotImageSelectionActivity::class.java)
            startActivity(intent)
            }


        }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {


        if (item?.itemId == android.R.id.home) {
            finish()
        }


        return super.onOptionsItemSelected(item)
    }


}







