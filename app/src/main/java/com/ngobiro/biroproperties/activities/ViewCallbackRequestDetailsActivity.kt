package com.ngobiro.biroproperties.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.method.ScrollingMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.ngobiro.biroproperties.R
import com.parse.*
import com.parse.ui.ParseLoginBuilder
import kotlinx.android.synthetic.main.activity_manage_plot.*
import kotlinx.android.synthetic.main.activity_plot_details.*
import kotlinx.android.synthetic.main.activity_view_callback_request_details.*
import org.jetbrains.anko.longToast
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class ViewCallbackRequestDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_callback_request_details)
        supportActionBar?.setTitle("Calls Details")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        getSelectedRequest()




              if  (getCallbackStatus(selectedRequestedCallback)){

                 callback_switch.isChecked = true
                  callback_switch.text = "Mark as Not Done"

              } else{

                  callback_switch.isChecked = false
                  callback_switch.text = "Mark as  Done"


              }




      customerCallBackBtn.setOnClickListener {



           if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
               requestPermissions(arrayOf(Manifest.permission.CALL_PHONE),1)
           } else{
               val phoneIntent = Intent(Intent.ACTION_CALL)
               phoneIntent.data = Uri.parse("tel:" + "0718952129")
               startActivity(phoneIntent)

           }



       }



        callback_switch.setOnClickListener {

            val query = ParseQuery.getQuery<ParseObject>("Callbacks")
            var callback = query[selectedRequestedCallback]


            if (callback_switch.isChecked){
                callback.put("status", true)
                callback.save()
                    longToast("Mark as Done")

            }

            else{
                callback.put("status", false)
                callback.save()
                    longToast("Marked as Not Done")

            }
        }







    }

    fun getSelectedRequest() {
        val query = ParseQuery.getQuery<ParseObject>("Callbacks")
        query.include("plot")
        query.cachePolicy = ParseQuery.CachePolicy.NETWORK_ELSE_CACHE
        query.getInBackground(selectedRequestedCallback, object : GetCallback<ParseObject> {

            override fun done(callback: ParseObject, e: ParseException?) {
                if (e == null) {
                    val customerName = callback.get("name") as String
                    val customerMobileNumber = callback.get("mobile_number") as String
                    val customerRemarks = callback.get("remarks") as String
                    val createdAt = callback.createdAt
                    var customerNameTxt: TextView = findViewById(R.id.customerNameTxt)
                    val PlotObj = callback.getParseObject("plot")
                    val plotDetail = PlotObj.getString("title")
                    var customerMobileNumberTxt: TextView = findViewById(R.id.customerMobileNumberTxt)
                    var requestTime: TextView = findViewById(R.id.callbackRequestTimeTxt)
                    var plotDetailsTxt: TextView = findViewById(R.id.plotDetailsTxt)
                    var customerRemarksTxt: TextView = findViewById(R.id.customerRemarksTxt)
                    val DATE_FORMAT = SimpleDateFormat("dd-MM-yyyy")
                    requestTime.text = "Request Date ${DATE_FORMAT.format(createdAt)}"
                    customerNameTxt.text = "Customer Name: $customerName"
                    customerMobileNumberTxt.text = "Customer Mobile: $customerMobileNumber"
                    plotDetailsTxt.text = "Plot Details: $plotDetail"
                    customerRemarksTxt.text = "Customer Remarks: $customerRemarks"

                } else {

                    Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }

        })
    }



    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == android.R.id.home) {
            finish()
        }


        return super.onOptionsItemSelected(item)
    }


    private fun getCallbackStatus(callback_id: String?): Boolean {
        val query = ParseQuery.getQuery<ParseObject>("Callbacks")
        var callback = query[callback_id]
        var status = callback.get("status")
        return status as Boolean

    }


}


