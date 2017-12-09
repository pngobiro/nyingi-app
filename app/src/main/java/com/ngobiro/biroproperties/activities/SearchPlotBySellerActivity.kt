package com.ngobiro.biroproperties.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.ngobiro.biroproperties.R
import com.ngobiro.biroproperties.adapters.LocationsAdapter
import com.ngobiro.biroproperties.adapters.SellerAdapter
import com.ngobiro.biroproperties.data.Location
import com.ngobiro.biroproperties.data.Seller
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_search_plot_by_seller.*
import kotlinx.android.synthetic.main.activity_select_location.*
import com.parse.ParseUser



class SearchPlotBySellerActivity : AppCompatActivity() , (Seller) -> Unit {


    override fun invoke(seller: Seller) {

    }

    private var adapter: SellerAdapter? = null
    private var sellersList: ArrayList<Seller>? = null
    private var sellerslayoutManager: RecyclerView.LayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_plot_by_seller)

        getSellers()


    }


    fun getSellers() {

        sellersList = ArrayList<Seller>()
        sellerslayoutManager = LinearLayoutManager(this)
        adapter = SellerAdapter(sellersList!!, this)
        sellers_recyclerView.layoutManager = sellerslayoutManager
        sellers_recyclerView.adapter = adapter


        val query = ParseUser.getQuery()

        query.findInBackground { objects, e ->

            if (e != null) {
                Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
            } else {

                if (objects.size > 0) {
                    sellersList!!.clear()

                    for (parseObject in objects) {
                        val id = parseObject.objectId
                        val name = parseObject.get("name") as String


                        var seller = Seller()
                        seller.objectId = id
                        seller.name = name
                        sellersList!!.add(seller)

                    }

                    adapter!!.notifyDataSetChanged()


                }


            }


        }
    }
}
