package com.ngobiro.biroproperties.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ngobiro.biroproperties.R
import com.parse.ParseUser
import com.parse.ui.ParseLoginBuilder
import kotlinx.android.synthetic.main.activity_logged_in_user.*

class LoggedInUserActivity : AppCompatActivity() {
    val user = ParseUser.getCurrentUser()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_in_user)

        if (user != null) {

            log_in_user_txtview.text = "Logged in as ${user.username}"
        }
        else {

            val builder = ParseLoginBuilder(this)
            startActivityForResult(builder.build(), 0)


        }

        log_out_btn.setOnClickListener {

            ParseUser.logOut()

            val builder = ParseLoginBuilder(this)
            startActivityForResult(builder.build(), 0)

        }



        my_plots_btn.setOnClickListener {

            var intent = Intent(this, MyPlotsActivity::class.java)

            startActivity(intent)
        }


        my_rental_houses_btn.setOnClickListener {

            var intent = Intent(this, MyRentalHousesActivity::class.java)

            startActivity(intent)

        }


        add_plot_btn.setOnClickListener {
            var intent = Intent(this, AddNewPlotActivity::class.java)

            startActivity(intent)

        }


        add_rental_house_btn.setOnClickListener {

            var intent = Intent(this, AddNewRentalHouseActivity::class.java)

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



        return super.onOptionsItemSelected(item)
    }
}
