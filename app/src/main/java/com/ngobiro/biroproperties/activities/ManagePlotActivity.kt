package com.ngobiro.biroproperties.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.ngobiro.biroproperties.R
import com.parse.*
import com.parse.ui.ParseLoginBuilder
import kotlinx.android.synthetic.main.activity_manage_plot.*
import org.jetbrains.anko.*
import com.parse.ParseObject
import kotlinx.android.synthetic.main.activity_manage_plot.view.*


class ManagePlotActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_plot)

        supportActionBar?.setTitle("Manage Plot $selected_plot_title")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        getPlotStatus(selected_plot_id)


        plot_status_switch.setOnClickListener {

            val parseACL = ParseACL(ParseUser.getCurrentUser())
            ParseUser.getCurrentUser().acl = parseACL

            val query = ParseQuery.getQuery<ParseObject>("Plots")
            var plot = query[selected_plot_id]


            if (plot_status_switch.isChecked) {
                plot.put("status", true)
                plot_status_switch.text = "Disable Plot"

                plot.saveInBackground { e ->
                    if (e != null) {
                        Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()

                    }
                    else{
                        plot_status_switch.text = "Disable Plot"
                        longToast("Plot Activated")
                    }


                }
            } else {

                plot.put("status", false)
                plot.saveInBackground { e ->
                    if (e != null) {
                        Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
                    } else {
                        plot_status_switch.text = "Publish Plot"
                        longToast("Plot Disabled")

                    }
                }

            }
        }




        manage_plot_photos_btn.setOnClickListener {
            var intent = Intent(this, ManagePlotPicturesActivity::class.java)
            startActivity(intent)
        }


        delete_plots_btn.setOnClickListener {
            alert("Are You sure You want to delete the Plot") {
                okButton {
                    deletePlot()
                }
                noButton {
                }
            }.show()
        }

        view_plot_details_btn.setOnClickListener {
            var intent = Intent(this, PlotDetailsActivity::class.java)
            intent.putExtra("plot_id", "plot.objectId")
            startActivity(intent)
        }


        manage_plot_video_btn.setOnClickListener {
            var intent = Intent(this, ManagePlotVideoActivity::class.java)
            startActivity(intent)
        }

    }



    override fun onOptionsItemSelected(item: MenuItem?): Boolean {


        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


   fun deletePlot(){
       val query = ParseQuery.getQuery<ParseObject>("Plots")
       val pictures = ParseQuery.getQuery<ParseObject>("Pictures")
       val callbacks = ParseQuery.getQuery<ParseObject>("Callbacks")
       var plot = query[selected_plot_id]
       pictures.whereEqualTo("plot", plot)
       callbacks.whereEqualTo("plot", plot)
       pictures.findInBackground { pic, e ->
           pic.forEach { x ->
               longToast("Deleting Plot Picture(s)")
               x.deleteEventually()
           }
    }


       callbacks.findInBackground { callback, e ->
           callback.forEach { ca ->
               longToast("Deleting Callbacks Requests")
               ca.deleteEventually()
           }
       }



       plot.deleteEventually {

           longToast("Deleting Plot")
       }



       var intent = Intent(this, MyPlotsActivity::class.java)
       startActivity(intent)

    }



    fun getPlotStatus(plot_id : String? ){
        val query = ParseQuery.getQuery<ParseObject>("Plots")
        var plot = query[plot_id]

        val status = plot.get("status")

        if (status == true){
            plot_status_switch.text = "Plot is Published"
            plot_status_switch.isChecked = true

        }
        else{

            plot_status_switch.text = "Plot is Disabled"
            plot_status_switch.isChecked = false

        }



    }


}
