package com.ngobiro.biroproperties.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ngobiro.biroproperties.R
import kotlinx.android.synthetic.main.activity_add_new_plot.*
import kotlinx.android.synthetic.main.activity_rental_house_payment.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class RentalHousePaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rental_house_payment)

        payment_button.setOnClickListener {

            if (mobile_number_textView.text.isNullOrBlank())
                alert("Please Enter Your Mobile number to Pay") {
                    yesButton { }

                }.show()

        }

    }
}
