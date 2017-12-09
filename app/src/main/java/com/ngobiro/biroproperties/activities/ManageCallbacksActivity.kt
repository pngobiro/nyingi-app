package com.ngobiro.biroproperties.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ngobiro.biroproperties.R
import com.parse.ParseUser
import com.parse.ui.ParseLoginBuilder
import kotlinx.android.synthetic.main.activity_manage_callbacks.*

class ManageCallbacksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_callbacks)

        supportActionBar?.setTitle("Manage Calls")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        view_active_callbacks_btn.setOnClickListener {

            val intent = Intent(applicationContext, DisplayActiveCallbacksActivity::class.java)
            startActivity(intent)



        }
    }



    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == android.R.id.home) {
            finish()
        }




        return super.onOptionsItemSelected(item)
    }
}
