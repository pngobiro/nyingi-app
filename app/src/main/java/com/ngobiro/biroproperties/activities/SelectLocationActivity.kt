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
import com.ngobiro.biroproperties.adapters.LocationsAdapter
import com.ngobiro.biroproperties.data.Location
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_select_location.*





class SelectLocationActivity : AppCompatActivity() , (Location) -> Unit {


        private var adapter: LocationsAdapter? = null
        private var locationList: ArrayList<Location>? = null
        private var locationlayoutManager: RecyclerView.LayoutManager? = null


        override fun invoke(location: Location) {
            var intent = Intent(this, MapsActivity::class.java)
            myplotGlobalLocationID= location.objectId
            myplotGlobalLocationName= location.name
            startActivity(intent)
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_select_location)

            supportActionBar?.setTitle("Select Location...")
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)


            getLocations()



        }


        fun getLocations() {

            locationList = ArrayList<Location>()
            locationlayoutManager = LinearLayoutManager(this)
            adapter = LocationsAdapter(locationList!!, this)
            locations_recyclerView.layoutManager = locationlayoutManager
            locations_recyclerView.adapter = adapter

            val query = ParseQuery.getQuery<ParseObject>("Locations")
            val town = ParseQuery.getQuery<ParseObject>("Towns")
            val  town_object =   town[myplotGlobalTownID]
            query.whereEqualTo("town", town_object)

            query.findInBackground { objects, e ->

                if (e != null) {
                    Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
                } else {

                    if (objects.size > 0) {
                        locationList!!.clear()

                        for (parseObject in objects) {
                            val id = parseObject.objectId
                            val name = parseObject.get("name") as String


                            var location = Location()
                            location.objectId = id
                            location.name = name
                            locationList!!.add(location)

                        }

                        adapter!!.notifyDataSetChanged()


                    }


                }


            }
        }



    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == R.id.add_plot) {
            var intent = Intent(this, AddNewPlotActivity::class.java)

            startActivity(intent)
        }

        if (item?.itemId == android.R.id.home) {
            finish()
        }


        return super.onOptionsItemSelected(item)
    }


    }


