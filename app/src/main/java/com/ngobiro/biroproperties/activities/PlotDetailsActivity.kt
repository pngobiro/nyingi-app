package com.ngobiro.biroproperties.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v13.app.ActivityCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.method.ScrollingMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.ngobiro.biroproperties.R
import com.ngobiro.biroproperties.adapters.PictureGalleryAdapter
import com.ngobiro.biroproperties.data.Picture
import com.parse.*
import kotlinx.android.synthetic.main.activity_plot_details.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.ArrayList
import com.parse.ParseQuery
import com.parse.ParseObject





var plotVideo : ParseFile? = null

class PlotDetailsActivity : AppCompatActivity() , (Picture) -> Unit {


    override fun invoke(picture: Picture) {
            var intent = Intent(this, DisplayMapActivity::class.java)
            startActivity(intent)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.plot_details, menu)


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == R.id.view_plot_video) {
            var intent = Intent(this, PlayVideoActivity::class.java)

            startActivity(intent)

        }

        if (item?.itemId == android.R.id.home) {
            finish()
        }



        if (item?.itemId == R.id.view_plot_map) {
            var intent = Intent(this,DisplayMapActivity::class.java)
            startActivity(intent)

        }

        return super.onOptionsItemSelected(item)
    }



    private var adapter: PictureGalleryAdapter? = null
    private var picturesList: ArrayList<Picture>? = null
    private var pictureslayoutManager: RecyclerView.LayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plot_details)


        supportActionBar?.title = "Plots Details"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        make_call.setOnClickListener {






        }

        request_callback_btn.setOnClickListener {

            var intent = Intent(this, RequestCallbackActivity::class.java)
            startActivity(intent)
        }

        getPlotDetails(selected_plot_id)
        getPlotImages(selected_plot_id)

    }

    fun getPlotDetails(plot_id: String?) {

        val query = ParseQuery.getQuery<ParseObject>("Plots")
        query.include("location")
        query.getInBackground(plot_id, object : GetCallback<ParseObject> {

            override fun done(plot: ParseObject, e: ParseException?) {
                if (e == null) {

                    val title = plot.get("title") as String
                    val size = plot.get("size") as String
                    val price = plot.get("price") as Int
                    val description = plot.get("description") as String
                    plotVideo = plot.get("video") as ParseFile?
                    val LocationObj = plot.getParseObject("location")
                    val location = LocationObj.getString("name")

                    val posted_on = plot.createdAt
                    var detailed_plot_description: TextView = findViewById(R.id.plot_description)
                        plot_description.movementMethod = ScrollingMovementMethod()
                    var detailed_plot_title: TextView = findViewById(R.id.plot_title)
                    var detailed_plot_price: TextView = findViewById(R.id.plot_price_txtView)
                    var detailed_plot_size: TextView = findViewById(R.id.plot_size_txtView)
                    var detailed_posted_on: TextView = findViewById(R.id.posted_on_textView)
                    val DATE_FORMAT = SimpleDateFormat("dd-MM-yyyy")
                    detailed_posted_on.text = "Plot Posted On ${DATE_FORMAT.format(posted_on)}"
                    detailed_plot_title.text = "${title} - ${location} "
                    val formattedPlotPrice = DecimalFormat("#,###.##").format(java.lang.Double.parseDouble(price.toString()))
                    detailed_plot_price.text = "Plot Price Kshs ${formattedPlotPrice}"
                    detailed_plot_size.text = "Plot Size ${size}"
                    detailed_plot_description.text = description


                } else {

                    Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()

                }


            }


        })
    }


    fun getPlotImages(plot_id: String?) {


        picturesList = ArrayList<Picture>()
        pictureslayoutManager = LinearLayoutManager(this, 0, false)
        adapter = PictureGalleryAdapter(picturesList!!,this)
        imagesRecyclerView.layoutManager = pictureslayoutManager
        imagesRecyclerView.adapter = adapter

        val pictures = ParseQuery.getQuery<ParseObject>("Pictures")

        val query = ParseQuery.getQuery<ParseObject>("Plots")
        var selectedPlot = query[plot_id]

       pictures.whereEqualTo("plot", selectedPlot)

        pictures.findInBackground { objects, e ->

            if (objects.size > 0) {

                if (e != null) {
                    Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()

                } else {


                    for (parseObject in objects) {
                        val image = parseObject.get("image") as ParseFile
                        image.getDataInBackground { data, ex ->
                            if (ex!=null) {
                                Toast.makeText(applicationContext,ex.localizedMessage,Toast.LENGTH_LONG).show()

                            } else {

                                val bitmap = BitmapFactory.decodeByteArray(data,0,data.size)

                                var picture = Picture()

                                picture.bitmap = bitmap


                                picturesList!!.add(picture)


                            }

                            adapter!!.notifyDataSetChanged()
                        }




                    }




                }


            }

        }
    }




}








