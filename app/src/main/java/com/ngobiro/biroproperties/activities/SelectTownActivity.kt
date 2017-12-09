package com.ngobiro.biroproperties.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.widget.Toast
import com.ngobiro.biroproperties.R
import com.ngobiro.biroproperties.adapters.TownsAdapter
import com.ngobiro.biroproperties.data.Location
import com.ngobiro.biroproperties.data.Town
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_select_town.*
import android.app.SearchManager
import android.content.Context
import android.widget.SearchView
import org.jetbrains.anko.find


class SelectTownActivity : AppCompatActivity() , (Town) -> Unit {

    override fun invoke(town: Town) {
        var intent = Intent(this, SelectLocationActivity::class.java)
        myplotGlobalTownID = town.objectId
        myplotGlobalTownName = town.name
        startActivity(intent)
    }

    private var adapter: TownsAdapter? = null
    private var townList: ArrayList<Town>? = null
    private var townlayoutManager: RecyclerView.LayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        getTown()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_town)

        supportActionBar?.setTitle("Select County...")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Associate searchable configuration with the SearchView

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        var searchView : SearchView =  find(R.menu.search)


        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(componentName))
        searchView.maxWidth = Integer.MAX_VALUE

        // listening to search query text change
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // filter recycler view when query submitted

                return false
            }

           override fun onQueryTextChange(query: String): Boolean {
                // filter recycler view when text is changed

                return false
            }
        })



    }



    fun getTown() {

        townList = ArrayList<Town>()
        townlayoutManager = LinearLayoutManager(this)
        adapter =TownsAdapter(townList!!, this)
        town_recyclerView.layoutManager = townlayoutManager
        town_recyclerView.adapter = adapter

        val query = ParseQuery.getQuery<ParseObject>("Towns")
        query.cachePolicy = ParseQuery.CachePolicy.NETWORK_ELSE_CACHE

        query.findInBackground { objects, e ->

            if (e != null) {
                Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
            } else {

                if (objects.size > 0) {
                    townList!!.clear()

                    for (parseObject in objects) {
                        val id = parseObject.objectId
                        val name = parseObject.get("name") as String


                        var location = Town()
                        location.objectId = id
                        location.name = name
                        townList!!.add(location)

                    }

                    adapter!!.notifyDataSetChanged()


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
