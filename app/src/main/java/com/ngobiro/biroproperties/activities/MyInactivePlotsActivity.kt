package com.ngobiro.biroproperties.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.ngobiro.biroproperties.R
import com.ngobiro.biroproperties.adapters.PlotsAdapter
import com.ngobiro.biroproperties.data.ExtraFun
import com.ngobiro.biroproperties.data.Plot
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import com.parse.ui.ParseLoginBuilder
import kotlinx.android.synthetic.main.activity_my_inactive_plots.*
import org.jetbrains.anko.indeterminateProgressDialog
import java.text.DecimalFormat
import java.util.*

class MyInactivePlotsActivity : AppCompatActivity() ,  (Plot) -> Unit {


    override fun invoke(plot: Plot) {
        var intent = Intent(this, ManagePlotActivity::class.java)
        selected_plot_id = plot.objectId
        selected_plot_title = plot.title
        startActivity(intent)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.search, menu)

        var searchItem : MenuItem? = menu?.findItem(R.id.action_search)
        var searchView : SearchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {

                filter(newText)

                return true
            }
        })



        return super.onCreateOptionsMenu(menu)
    }




    private var adapter: PlotsAdapter? = null
    private var  my_inactiveplotsList: ArrayList<Plot>? = null
    private var locationlayoutManager: RecyclerView.LayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_inactive_plots)

        supportActionBar?.setTitle("Inactive Plots List")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        getMyActivePlots()

    }


    fun getMyActivePlots(){

        my_inactiveplotsList = ArrayList<Plot>()
        locationlayoutManager = LinearLayoutManager(this)
        adapter = PlotsAdapter(my_inactiveplotsList!!, this)

        my_inactive_plots_RecyclerView.layoutManager = locationlayoutManager
        my_inactive_plots_RecyclerView.adapter = adapter

        val myInActivePlots = ParseQuery.getQuery<ParseObject>("Plots")
        myInActivePlots.include("location")
        myInActivePlots.whereEqualTo("createdBy", ParseUser.getCurrentUser())
        myInActivePlots.whereEqualTo("status",false)
        val progress = indeterminateProgressDialog(message = "Please Wait..",title= "Fetching Deactivated Plots")
        myInActivePlots.findInBackground { objects, e ->

            if (e != null) {
                Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
            } else {

                if (objects.size > 0) {
                    for (parseObject in objects) {
                        progress.show()
                                val id = parseObject.objectId
                                val title = parseObject.get("title") as String
                                val size = parseObject.get("size") as String
                                val price = parseObject.get("price") as Int
                                val description = parseObject.get("description") as String
                                val LocationObj = parseObject.getParseObject("location")
                                val location = LocationObj.getString("name")
                                val posted_on = parseObject.createdAt as Date
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
                                my_inactiveplotsList!!.add(plot)
                            }
                             progress.hide()
                            adapter!!.notifyDataSetChanged()
                    }
                }
            }


    }



    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun filter(text: String) {
        val filterdPlots =  ArrayList<Plot>()

        for (s in this!!. my_inactiveplotsList!!) {
            if (s.title?.toLowerCase()?.contains(text.toLowerCase())!!) {
                filterdPlots.add(s)
            }
        }
        adapter?.filterList(filterdPlots)
    }



}

