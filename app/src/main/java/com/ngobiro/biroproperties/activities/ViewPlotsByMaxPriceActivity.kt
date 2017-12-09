package com.ngobiro.biroproperties.activities

import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.ngobiro.biroproperties.R
import com.ngobiro.biroproperties.adapters.PlotsAdapter
import com.ngobiro.biroproperties.data.ExtraFun
import com.ngobiro.biroproperties.data.Plot
import com.ngobiro.biroproperties.data.ExtraFun.getPlotFirstPicture
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_search_plot_by_price.*
import kotlinx.android.synthetic.main.activity_view_plots_by_max_price.*
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

var requestedMaxPrice: Int? = null

class ViewPlotsByMaxPriceActivity : AppCompatActivity(), (Plot) -> Unit {


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }


    override fun invoke(plot: Plot) {

        var intent = Intent(this, PlotDetailsActivity::class.java)
        selected_plot_id = plot.objectId
        selected_plot_title = plot.title
        startActivity(intent)

    }


    private var adapter: PlotsAdapter? = null
    private var maxpriceplotList: ArrayList<Plot>? = null
    private var maxpriceplotslayoutManager: RecyclerView.LayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_plots_by_max_price)
        supportActionBar?.setTitle("Search Plot By Price...")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val formattedRequestedMaxPrice = DecimalFormat("#,###.##").format(java.lang.Double.parseDouble(requestedMaxPrice.toString()))
        plots_max_price_header.text = "Plots in ${selected_town_name} with Maximum Price of Kshs ${formattedRequestedMaxPrice}"
        getPlotsByPrices(selected_town_id, requestedMaxPrice)


    }

    fun getPlotsByPrices(town_id: String?, plot_price: Int?) {

        maxpriceplotList = ArrayList<Plot>()
        maxpriceplotslayoutManager = LinearLayoutManager(this)
        adapter = PlotsAdapter(maxpriceplotList!!, this)
        max_price_recyclerView.layoutManager = maxpriceplotslayoutManager
        max_price_recyclerView.adapter = adapter

        val query = ParseQuery<ParseObject>("Plots")
        query.include("location")
        val town = ParseQuery.getQuery<ParseObject>("Towns")
        val town_object = town[town_id]
        query.whereEqualTo("town", town_object)
        query.whereEqualTo("status", true)
        query.whereLessThanOrEqualTo("price", plot_price)
        query.addAscendingOrder("price")
        val progress = indeterminateProgressDialog(message = "Please Wait...", title = "Fetching Plots with Max Price of Kshs ${requestedMaxPrice}")
        query.findInBackground { objects, e ->
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
                            val posted_on = parseObject.createdAt as Date
                            val LocationObj = parseObject.getParseObject("location")
                            val location = LocationObj.getString("name")
                            val formattedPlotPrice = DecimalFormat("#,###.##").format(java.lang.Double.parseDouble(price.toString()))


                            var plot = Plot()
                            plot.posted_on = posted_on
                            plot.objectId = id
                            plot.title = title
                            plot.size = size
                            plot.price = formattedPlotPrice
                            plot.description = description
                            plot.location = location
                            plot.picture = ExtraFun.getPlotFirstPicture(id)
                            maxpriceplotList!!.add(plot)
                        }
                        adapter!!.notifyDataSetChanged()
                    }
                    progress.hide()

                }

            }
        } // end of function
    }
}



