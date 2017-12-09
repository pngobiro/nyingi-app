package com.ngobiro.biroproperties.activities

import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.ngobiro.biroproperties.R
import com.ngobiro.biroproperties.adapters.PlotActiveCallbacksAdapter
import com.ngobiro.biroproperties.adapters.PlotsAdapter
import com.ngobiro.biroproperties.data.Plot
import com.ngobiro.biroproperties.data.PlotCallback
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import com.parse.ui.ParseLoginBuilder
import kotlinx.android.synthetic.main.activity_display_active_callbacks.*
import kotlinx.android.synthetic.main.activity_view_plots_by_max_price.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

var selectedRequestedCallback:String? = ""

class DisplayActiveCallbacksActivity : AppCompatActivity() , (PlotCallback) -> Unit {


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.manage_plot, menu)


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == R.id.add_plot) {
            var intent = Intent(this, AddNewPlotActivity::class.java)

            startActivity(intent)
        }

        if (item?.itemId == R.id.log_out) {
            ParseUser.logOut()

            val builder = ParseLoginBuilder(this)
            startActivityForResult(builder.build(), 0)


        }



        return super.onOptionsItemSelected(item)
    }



    override fun invoke(plotcallback: PlotCallback) {

        var intent = Intent(this, ViewCallbackRequestDetailsActivity::class.java)
        selectedRequestedCallback = plotcallback.objectId
        startActivity(intent)

    }



    var activePlotCallbacksList = ArrayList<PlotCallback>()
    var  activePlotCallbackslayoutManager = LinearLayoutManager(this)
    var adapter =PlotActiveCallbacksAdapter(activePlotCallbacksList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_active_callbacks)







        getActiveCallbacks()
    }

    fun getActiveCallbacks() {

        activePlotCallbacksList = ArrayList<PlotCallback>()
        activePlotCallbackslayoutManager = LinearLayoutManager(this)
        adapter = PlotActiveCallbacksAdapter(activePlotCallbacksList, this)
        active_callbacks_recyclerView.layoutManager = activePlotCallbackslayoutManager
        active_callbacks_recyclerView.adapter = adapter

        val query = ParseQuery<ParseObject>("Callbacks")
        query.include("plot")
        query.whereEqualTo("status",false)


        query.findInBackground { objects, e ->

            if (e != null) {
                Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
            } else {

                if (objects.size > 0) {

                    for (parseObject in objects) {

                            if (e != null) {
                                Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()

                            } else {

                                val id = parseObject.objectId
                                val customerName = parseObject.get("name") as String
                                val customerMobileNumber = parseObject.get("mobile_number") as String
                                val createdAt = parseObject.createdAt as Date
                                val customerRemarks = parseObject.get("remarks") as String
                                val PlotObj = parseObject.getParseObject("plot")
                                val plotDetail = PlotObj.getString("title")
                                val DATE_FORMAT = SimpleDateFormat("dd-MM-yyyy")
                                val formattedCreatedDate = DATE_FORMAT.format(createdAt)



                                var plotcallback = PlotCallback()
                                plotcallback.objectId = id
                                plotcallback.created_at = formattedCreatedDate
                                plotcallback.customer_name = customerName
                                plotcallback.mobile_number = customerMobileNumber
                                plotcallback.remarks = customerRemarks
                                plotcallback.plot_details = plotDetail



                                activePlotCallbacksList.add(plotcallback)
                            }


                            adapter.notifyDataSetChanged()
                        }

                    }
                }



            }

        }
    }


