package com.ngobiro.biroproperties.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import com.ngobiro.biroproperties.R
import com.ngobiro.biroproperties.adapters.PlotsAdapter
import com.ngobiro.biroproperties.data.ExtraFun
import com.ngobiro.biroproperties.data.Plot
import com.parse.*
import kotlinx.android.synthetic.main.activity_list_location_plots.*
import org.jetbrains.anko.indeterminateProgressDialog
import java.lang.Double
import java.text.DecimalFormat
import java.util.*




class ListLocationPlotsActivity : AppCompatActivity() ,  (Plot) -> Unit {

    override fun invoke(plot: Plot) {
        var intent = Intent(this, PlotDetailsActivity::class.java)
        selected_plot_id = plot.objectId
        updatePlotClickCount(plot.objectId)
        selected_plot_title = plot.title
        startActivity(intent)
    }




    private var adapter: PlotsAdapter? = null
    private var plotList: ArrayList<Plot>? = null
    private var plotslayoutManager: RecyclerView.LayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_location_plots)

        supportActionBar?.setTitle("Plots in $selected_location_name - $selected_town_name")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        pricefilter.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onQueryTextChange(p0: String?): Boolean {

                filter(p0)

                return false
            }


        })
        getLocationPlots()




    }




    fun getLocationPlots() {

        plotList = ArrayList<Plot>()
        plotslayoutManager = LinearLayoutManager(this)
        adapter = PlotsAdapter(plotList!!, this)
        plot_location_recyclerView.layoutManager = plotslayoutManager
        plot_location_recyclerView.adapter = adapter

        val query = ParseQuery.getQuery<ParseObject>("Plots")
        val location = ParseQuery.getQuery<ParseObject>("Locations")
        val town = ParseQuery.getQuery<ParseObject>("Towns")
        val location_object = location[selected_location_id]
        val town_object = town[selected_town_id]
        var location_name = location_object.get("name")
        var town_name = town_object.get("name")
        query.whereEqualTo("location", location_object)
        query.whereEqualTo("town", town_object)
        query.whereEqualTo("status", true)
        query.addDescendingOrder("createdAt")
        val progress = indeterminateProgressDialog(message = "Please Wait..",title= "Fetching Plots in ${selected_location_name}")

        query.findInBackground { objects, e ->

            if (e != null) {
                Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
            } else {


                for (parseObject in objects) {


                    if (objects.size > 0) {

                        progress.show()

                        val id = parseObject.objectId
                        val title = parseObject.get("title") as String
                        val size = parseObject.get("size") as String
                        val price = parseObject.get("price") as Int
                        val description = parseObject.get("description") as String
                        val posted_on = parseObject.createdAt as Date

                        val formattedPlotPrice = DecimalFormat("#,###.##").format(Double.parseDouble(price.toString()))
                        var plot = Plot()
                        plot.posted_on = posted_on
                        plot.objectId = id
                        plot.title = title
                        plot.size = size
                        plot.price = formattedPlotPrice
                        plot.description = description
                        plot.location = location_name.toString()
                        plot.picture =  ExtraFun.getPlotFirstPicture(id)
                        plotList!!.add(plot)

                    }

                    adapter!!.notifyDataSetChanged()
                }

                progress.hide()
            }


        }


    }

    private fun updatePlotClickCount(plot_id:String?){
        val plots = ParseQuery.getQuery<ParseObject>("Plots")
        var plot =  plots[plot_id]
        plot.increment("clicks")
        plot.saveEventually()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        return true
    }



    private fun filter(price: String?) {
        val filterdPlots = java.util.ArrayList<Plot>()

        for (s in this!!. plotList!!) {
            if (s.price?.toInt() == price?.toInt()) {
                filterdPlots.add(s)
            }
        }
        adapter?.filterList(filterdPlots)
    }



}


