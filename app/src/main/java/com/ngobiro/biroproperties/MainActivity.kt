package com.ngobiro.biroproperties

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ngobiro.biroproperties.activities.DisplayTownsActivity
import com.ngobiro.biroproperties.activities.MyPlotsActivity
import com.parse.ParseAnalytics
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_main.*
import com.parse.ui.ParseLoginBuilder



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ParseAnalytics.trackAppOpenedInBackground(intent)
        setContentView(R.layout.activity_main)


        buy_plot_btn.setOnClickListener {
            val intent = Intent(this, DisplayTownsActivity::class.java)
            startActivity(intent)
        }

        share_app_btn.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            shareIntent.type = "text/plain"
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey, download this app!")
            startActivity(shareIntent)
        }


        manage_plots_btn.setOnClickListener {
            val currentUser = ParseUser.getCurrentUser()
            if (currentUser == null) {
                val builder = ParseLoginBuilder(this)
                startActivityForResult(builder.build(), 0)
            }
            else {

                val intent = Intent(this, MyPlotsActivity::class.java)
                startActivity(intent)
            }
        }
    }








}
