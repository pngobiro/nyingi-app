package com.ngobiro.biroproperties.activities
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ngobiro.biroproperties.R
import com.ngobiro.biroproperties.data.Picture
import com.parse.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.progressDialog
import org.jetbrains.anko.yesButton
import java.io.ByteArrayOutputStream



class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var locationManager : LocationManager? = null
    var locationListener : LocationListener? = null
    var latitude = ""
    var longitude = ""

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.save_plot,menu)


        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == R.id.save_plot) {
            if (latitude.isNotBlank()  && longitude.isNotBlank())
            {
                saveToParse()
                val intent = Intent(applicationContext, MyPlotsActivity::class.java)
                startActivity(intent)

            }

            else
            {
                alert("Please Choose a Location on the Map ") {
                    yesButton { }
                }.show()
            }

        }

        if (item?.itemId == android.R.id.home) {
            finish()
        }


        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        supportActionBar?.setTitle("Add Plot to Map ")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnMapLongClickListener(myListener)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationListener = object : LocationListener {
            override fun onLocationChanged(p0: Location?) {

                if (p0 != null) {
                    var userLocation = LatLng(p0.latitude,p0.longitude)
                    mMap.clear()
                    mMap.addMarker(MarkerOptions().position(userLocation).title(myplotGlobalTitle))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,17f))
                }
            }

            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

            }

            override fun onProviderEnabled(p0: String?) {

            }

            override fun onProviderDisabled(p0: String?) {

            }

        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1)
            }

        } else {
            locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,2,2f,locationListener)
            mMap.clear()
            var lastLocation = locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            var lastUserLocation = LatLng(lastLocation.latitude,lastLocation.longitude)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation,17f))

        }



    }

    fun saveToParse() {

        // default ACLs for User object
        val parseACL = ParseACL(ParseUser.getCurrentUser())
        parseACL.publicReadAccess = true

        ParseUser.getCurrentUser().acl = parseACL

        val plot = ParseObject("Plots")
        plot.put("title", myplotGlobalTitle.toUpperCase())
        plot.put("size", myplotGlobalSize.toUpperCase())
        plot.put("price", myplotGlobalPrice)
        plot.put("description", myplotGlobalDescription.capitalize())
        plot.put("status", true)
        plot.put("is_featured", false)
        plot.put("location", ParseObject.createWithoutData("Locations", "${myplotGlobalLocationID}"))
        plot.put("town", ParseObject.createWithoutData("Towns", "${myplotGlobalTownID}"))
        plot.put("createdBy", ParseUser.getCurrentUser())
        plot.put("latitude",latitude)
        plot.put("longitude",longitude)
        plot.put("clicks",0)

        if (globalPictureList.size < 1) {

            plot.saveInBackground { e ->
                if (e != null) {
                    Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext, "Plot Saved $myplotGlobalTitle", Toast.LENGTH_LONG).show()
                    val intent = Intent(applicationContext, MyPlotsActivity::class.java)
                    startActivity(intent)
                }
            }


        } else {

            globalPictureList.forEach { image: Picture ->


                val picture = ParseObject("Pictures")
                val byteArrayOutputStream = ByteArrayOutputStream()
                image.bitmap!!.compress(Bitmap.CompressFormat.PNG, 20, byteArrayOutputStream)
                val bytes = byteArrayOutputStream.toByteArray()
                val parseFile = ParseFile("image.png", bytes)

                picture.put("image", parseFile)

                picture.put("plot", plot)


                picture.saveInBackground { e ->
                    if (e != null) {
                        Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
                    } else {

                        //Toast.makeText(applicationContext, "Saving ... $myplotGlobalTitle Picture", Toast.LENGTH_LONG).show()

                        ProgressCallback { percent ->

                            val dialog = progressDialog(message = "Please wait a bit…", title = "Saving File")

                            dialog.incrementProgressBy(percent)

                        }


                    }
                }

            }

        }



    }

    val myListener = object : GoogleMap.OnMapLongClickListener {
        override fun onMapLongClick(p0: LatLng?) {

            mMap.clear()

            mMap.addMarker(MarkerOptions().position(p0!!).title("Your Location"))

            latitude = p0.latitude.toString()
            longitude = p0.longitude.toString()

            Toast.makeText(applicationContext,"Now Save This Place!", Toast.LENGTH_LONG).show()

        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        if (grantResults.size > 0) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,2,2f,locationListener)

            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


}