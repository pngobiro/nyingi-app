package com.ngobiro.biroproperties.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.ngobiro.biroproperties.R
import com.ngobiro.biroproperties.adapters.PlotsAdapter
import com.ngobiro.biroproperties.data.ExtraFun
import com.ngobiro.biroproperties.data.Plot
import com.parse.*
import com.parse.ui.ParseLoginBuilder
import kotlinx.android.synthetic.main.activity_my_active_plots.*
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.progressDialog
import java.text.DecimalFormat
import java.util.*


var myAddMorePicsMode :Boolean = false

class MyActivePlotsActivity : AppCompatActivity() ,  (Plot) -> Unit {
    override fun invoke(plot: Plot) {
        var intent = Intent(this, ManagePlotActivity::class.java)
        selected_plot_id = plot.objectId
        selected_plot_title = plot.title
        startActivity(intent)
    }

    private var adapter: PlotsAdapter? = null
    private var my_activeplotsList: ArrayList<Plot>? = null
    private var locationlayoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_active_plots)
        supportActionBar?.setTitle("Active Plots List")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        getMyActivePlots()
    }


    fun getMyActivePlots(){

        my_activeplotsList = ArrayList<Plot>()
        locationlayoutManager = LinearLayoutManager(this)
        adapter = PlotsAdapter(my_activeplotsList!!, this)
        my_active_plots_RecyclerView.layoutManager = locationlayoutManager
        my_active_plots_RecyclerView.adapter = adapter
        val myActivePlots = ParseQuery.getQuery<ParseObject>("Plots")
        myActivePlots.cachePolicy = ParseQuery.CachePolicy.NETWORK_ELSE_CACHE
        myActivePlots.include("location")
        myActivePlots.whereEqualTo("createdBy", ParseUser.getCurrentUser())
        myActivePlots.whereEqualTo("status",true)
        val progress = indeterminateProgressDialog(message = "Please Wait...",title= "Fetching Active Plots")
        myActivePlots.findInBackground { objects, e ->
            if (e != null) {
                Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                if (objects.size > 0) {

                    for (parseObject in objects) {
                            if (e != null) {
                                Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
                            } else {
                                progress.show()
                                val id = parseObject.objectId
                                val title = parseObject.get("title") as String
                                val size = parseObject.get("size") as String
                                val price = parseObject.get("price") as Int
                                val description = parseObject.get("description") as String
                                val LocationObj = parseObject.getParseObject("location")
                                val location = LocationObj.getString("name")
                                val posted_on = parseObject.createdAt as Date
                                val formattedPlotprice = DecimalFormat("#,###.##").format(java.lang.Double.parseDouble(price.toString()))
                                var plot = Plot()
                                plot.posted_on = posted_on
                                plot.objectId = id
                                plot.title = title
                                plot.size = size
                                plot.price = formattedPlotprice
                                plot.description = description
                                plot.location = location
                                plot.picture = ExtraFun.getPlotFirstPicture(id)
                                my_activeplotsList!!.add(plot)
                            }
                            adapter!!.notifyDataSetChanged()
                    }
                    progress.hide()

                }
            }
        }
    }



    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

}
