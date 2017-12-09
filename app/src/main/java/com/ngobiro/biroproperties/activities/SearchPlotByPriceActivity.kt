package com.ngobiro.biroproperties.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.ngobiro.biroproperties.R
import kotlinx.android.synthetic.main.activity_search_plot_by_price.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import java.text.DecimalFormat

class SearchPlotByPriceActivity : AppCompatActivity() {


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == android.R.id.home) {
            finish()
        }


        return super.onOptionsItemSelected(item)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_plot_by_price)

        supportActionBar?.setTitle("Find Plots In ${selected_town_name} With Max Price Of... ")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        max_plot_price_Btn.setOnClickListener {


            if (maximum_plot_price_editText.text.isBlank()) {
                alert("Please Enter Price e.g 500,000") {
                    yesButton { }

                }.show()
            }

            else {

                var intent = Intent(this, ViewPlotsByMaxPriceActivity::class.java)
                requestedMaxPrice  = maximum_plot_price_editText.text.toString().toInt()

                startActivity(intent)

            }


        }


    }
}
