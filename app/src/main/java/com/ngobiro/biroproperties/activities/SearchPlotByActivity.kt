package com.ngobiro.biroproperties.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.ngobiro.biroproperties.R
import kotlinx.android.synthetic.main.activity_search_plot_by.*

class SearchPlotByActivity : AppCompatActivity() {


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {


        if (item?.itemId == android.R.id.home) {
            finish()
        }


        return super.onOptionsItemSelected(item)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_plot_by)



        supportActionBar?.setTitle("Search Plots in ${selected_town_name} By;")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        search_plot_by_location_btn.setOnClickListener {

            val intent = Intent(this, DisplayTownLocationsActivity::class.java)
            startActivity(intent)



        }


        search_plot_by_price_btn.setOnClickListener {

            val intent = Intent(this,SearchPlotByPriceActivity::class.java)
            startActivity(intent)



        }







    }






}
