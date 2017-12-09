package com.ngobiro.biroproperties.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ngobiro.biroproperties.R
import com.parse.ParseUser
import com.parse.ui.ParseLoginBuilder
import kotlinx.android.synthetic.main.activity_my_plots.*


class MyPlotsActivity : AppCompatActivity() {

    val currentUser = ParseUser.getCurrentUser()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_plots)

        supportActionBar?.setTitle("My Plots")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        logged_in_txtView.text = "Logged in as ${currentUser.username}"

        active_plots_btn.setOnClickListener {
            var intent = Intent(this, MyActivePlotsActivity::class.java)
            startActivity(intent)
        }

        inactive_plots_btn.setOnClickListener {
            var intent = Intent(this, MyInactivePlotsActivity::class.java)
            startActivity(intent)
        }


        manage_callback_requests_btn.setOnClickListener {
            var intent = Intent(this, ManageCallbacksActivity::class.java)
            startActivity(intent)
        }




    }

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

        if (item?.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }



}
