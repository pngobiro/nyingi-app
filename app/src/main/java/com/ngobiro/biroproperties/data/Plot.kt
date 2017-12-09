package com.ngobiro.biroproperties.data

import android.graphics.Bitmap
import android.net.Uri
import com.parse.ParseFile
import java.io.Serializable
import java.util.*

/**
 * Created by ngobiro on 9/5/17.
 */
data class Plot(  var objectId:String? = null,
                  var title:String? = null,
                  var description:String? = null,
                  var size : String? = null,
                  var owner_name:String? = null,
                  var picture: String? =null,
                  var price: String? =null,
                  var mobile_number: String? =null,
                  var posted_on: Date? =null,
                  var location: String? =null,
                  var latitude: Int? =null,
                  var logitude: Int? =null)