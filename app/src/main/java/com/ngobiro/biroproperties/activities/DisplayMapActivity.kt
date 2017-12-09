package com.ngobiro.biroproperties.activities


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.ngobiro.biroproperties.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.parse.GetCallback
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseQuery

class DisplayMapActivity : AppCompatActivity(), OnMapReadyCallback {



    private lateinit var mMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_map)

        supportActionBar?.setTitle("Plot on Map ")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)



    }


    override fun onMapReady(p0: GoogleMap) {

        mMap = p0

        val query = ParseQuery<ParseObject>("Plots")
        query.getInBackground(selected_plot_id, object : GetCallback<ParseObject> {

            override fun done(plot: ParseObject, e: ParseException?) {

                if (e != null) {
                    Toast.makeText(applicationContext,e.localizedMessage, Toast.LENGTH_LONG).show()
                }

                else {


                    val latitude = plot.get("latitude") as String
                    val longitude = plot.get("longitude") as String

                    val latitudeDouble = latitude.toDouble()
                    val longitudeDouble = longitude.toDouble()


                    val plotLocation = LatLng(latitudeDouble, longitudeDouble)
                    mMap.addMarker(MarkerOptions().position(plotLocation).title(selected_plot_title))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(plotLocation, 17f))

                }
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.plot_details, menu)


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }

        if (item?.itemId == R.id.view_plot_video) {
            var intent = Intent(this, PlayVideoActivity::class.java)

            startActivity(intent)

        }
        return super.onOptionsItemSelected(item)
    }





}