package com.ngobiro.biroproperties.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import com.ngobiro.biroproperties.data.Location
import com.ngobiro.biroproperties.R
import com.ngobiro.biroproperties.adapters.LocationsAdapter
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_display_town_locations.*
import org.jetbrains.anko.indeterminateProgressDialog

var selected_location_id:String? =""
var selected_location_name:String? =""

class DisplayTownLocationsActivity : AppCompatActivity() , (Location) -> Unit {


    override fun invoke(location: Location) {
        var intent = Intent(this, ListLocationPlotsActivity::class.java)

        selected_location_id = location.objectId
        selected_location_name = location.name

        startActivity(intent)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.search, menu)

        var searchItem : MenuItem? = menu?.findItem(R.id.action_search)
        var searchView : android.support.v7.widget.SearchView = searchItem?.actionView as android.support.v7.widget.SearchView
        searchView.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
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



    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == android.R.id.home) {
            finish()
        }


        return super.onOptionsItemSelected(item)
    }

    private var adapter: LocationsAdapter? = null
    private var locationList: ArrayList<Location>? = null
    private var locationlayoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_town_locations)

        supportActionBar?.setTitle("Locations in ${selected_town_name}")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        getLocations()

    }

    fun getLocations() {

        locationList = ArrayList<Location>()
        locationlayoutManager = LinearLayoutManager(this)
        adapter = LocationsAdapter(locationList!!, this)
        locationsrecycleview.layoutManager = locationlayoutManager
        locationsrecycleview.adapter = adapter

        val query = ParseQuery.getQuery<ParseObject>("Locations")
        query.cachePolicy = ParseQuery.CachePolicy.NETWORK_ELSE_CACHE
        val town = ParseQuery.getQuery<ParseObject>("Towns")
        town.cachePolicy = ParseQuery.CachePolicy.NETWORK_ELSE_CACHE
        val  town_object =   town[selected_town_id]
        query.whereEqualTo("town", town_object)
        val progress = indeterminateProgressDialog(message = "Please Wait...", title = "Fetching Locations from ${selected_town_name}")
        query.findInBackground { objects, e ->

            if (e != null) {
                Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
            } else {

                if (objects.size > 0) {
                    locationList!!.clear()

                    for (parseObject in objects) {
                        progress.show()
                        val id = parseObject.objectId
                        val name = parseObject.get("name") as String


                        var location  = Location()
                        location.objectId = id
                        location.name = name
                        locationList!!.add(location)

                    }

                    adapter!!.notifyDataSetChanged()

                }
                progress.dismiss()


            }


        }
    }

    private fun filter(text: String) {
        val filterdLocations = java.util.ArrayList<Location>()

        for (s in this!!.locationList!!) {
            if (s.name?.toLowerCase()?.contains(text.toLowerCase())!!) {
                filterdLocations.add(s)
            }
        }
        adapter?.filterList(filterdLocations)
    }


}
