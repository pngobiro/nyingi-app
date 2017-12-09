package com.ngobiro.biroproperties.activities

import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ngobiro.biroproperties.R
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_plot_details.*
import kotlinx.android.synthetic.main.activity_request_callback.*
import kotlinx.android.synthetic.main.activity_search_plot_by_price.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import java.io.ByteArrayOutputStream

var requestCallbackName:String? = ""
var requestCallbackMobileNumber:String? = ""
var requestCallbackRemarks:String = ""

class RequestCallbackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_callback)






        request_callback_header_textView.text = selected_plot_title

        buyer_request_callback_btn.setOnClickListener {


            if (name_editText.text.length < 4) {
                alert("Please Enter a Valid Name ") {
                    yesButton { }

                }.show()
            }


            if (mobile_number_editText.text.length != 10 ) {
                alert("Please enter a  Valid Mobile Number ") {
                    yesButton { }

                }.show()
            }

            else {


                saveRequest()


            }


        }

    }


    fun saveRequest() {

        requestCallbackName = name_editText.text.toString()
        requestCallbackMobileNumber = mobile_number_editText.text.toString()
        requestCallbackRemarks = remarks_editText.text.toString()


        val parseObj = ParseObject("Callbacks")
        val plots = ParseQuery.getQuery<ParseObject>("Plots")
        val plotObj = plots[selected_plot_id]
        val plotOwnerId =  plotObj.get("createdBy")


        parseObj.put("name", requestCallbackName?.toUpperCase())
        parseObj.put("mobile_number", requestCallbackMobileNumber)
        parseObj.put("remarks", requestCallbackRemarks.capitalize())
        parseObj.put("status",false)
        parseObj.put("plot", ParseObject.createWithoutData("Plots", "${selected_plot_id}"))
        parseObj.put("seller",plotOwnerId)

        parseObj.saveInBackground { e ->
            if (e != null) {
                Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, "Request Succesfully  Sent", Toast.LENGTH_LONG).show()
                val intent = Intent(applicationContext, PlotDetailsActivity::class.java)
                startActivity(intent)
            }
        }
    }





}
