package com.ngobiro.biroproperties.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ngobiro.biroproperties.R
import com.parse.ParseUser
import com.parse.ui.ParseLoginBuilder
import kotlinx.android.synthetic.main.activity_add_new_rental_house.*

class AddNewRentalHouseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_rental_house)


        new_rental_next_btn.setOnClickListener {

            var intent = Intent(this, RentalHousePaymentActivity::class.java)
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
