package com.ngobiro.biroproperties.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.support.v7.widget.SearchView
import android.widget.Toast
import com.ngobiro.biroproperties.R
import com.ngobiro.biroproperties.adapters.TownsAdapter
import com.ngobiro.biroproperties.data.Town
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_display_towns.*
import org.jetbrains.anko.indeterminateProgressDialog


var selected_town_id:String? =""
var selected_town_name:String? =""

class DisplayTownsActivity : AppCompatActivity() , (Town) -> Unit {

    private var adapter: TownsAdapter? = null
    private var townList: ArrayList<Town>? = null
    private var townlayoutManager: RecyclerView.LayoutManager? = null

    override fun invoke(town: Town) {
        var intent = Intent(this, SearchPlotByActivity::class.java)
        selected_town_id = town.objectId
        selected_town_name = town.name

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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_towns)

        supportActionBar?.setTitle("Select County")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        getTowns()

    }

    fun getTowns() {

        townList = ArrayList<Town>()
        townlayoutManager = LinearLayoutManager(this)
        adapter = TownsAdapter(townList!!, this)
        townsrecycleview.layoutManager = townlayoutManager
        townsrecycleview.adapter = adapter

        val query = ParseQuery.getQuery<ParseObject>("Towns")
        query.cachePolicy = ParseQuery.CachePolicy.NETWORK_ELSE_CACHE
        query.findInBackground { objects, e ->
            val progress = indeterminateProgressDialog(message = "Please Wait...", title = "Fetching Locations from ${selected_town_name}")

            if (e != null) {
                Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
            } else {

                if (objects.size > 0) {
                    townList!!.clear()

                    for (parseObject in objects) {
                        progress.show()
                        val id = parseObject.objectId
                        val name = parseObject.get("name") as String
                        var town  = Town()
                        town.objectId = id
                        town.name = name
                        townList!!.add(town)

                    }

                    adapter!!.notifyDataSetChanged()

                }

            progress.dismiss()
            }


        }
    }


    private fun filter(text: String) {
        val filterdTowns =  ArrayList<Town>()

        for (s in this!!.townList!!) {
            if (s.name?.toLowerCase()?.contains(text.toLowerCase())!!) {
                filterdTowns.add(s)
            }
        }
        adapter?.filterList(filterdTowns)
    }

}
