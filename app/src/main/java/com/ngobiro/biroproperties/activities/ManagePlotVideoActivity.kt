package com.ngobiro.biroproperties.activities


import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ngobiro.biroproperties.R
import kotlinx.android.synthetic.main.activity_manage_plot_video.*
import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.parse.ParseUser
import com.parse.ui.ParseLoginBuilder
import net.alhazmy13.mediapicker.Video.VideoPicker
import com.ngobiro.biroproperties.MainActivity
import org.jetbrains.anko.longToast

var mPaths: List<String>? = null
class ManagePlotVideoActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_plot_video)

        plot_add_video_btn.setOnClickListener {

            VideoPicker.Builder(this)
                    .mode(VideoPicker.Mode.CAMERA_AND_GALLERY)
                    .directory(VideoPicker.Directory.DEFAULT)
                    .extension(VideoPicker.Extension.MP4)
                    .enableDebuggingMode(true)
                    .build()


        }


        }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.manage_plot, menu)


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == R.id.add_plot) {
            var intent = Intent(this, AddNewPlotActivity::class.java)

            startActivity(intent)
        }

        if (item?.itemId == R.id.log_out) {
            ParseUser.logOut()

            val builder = ParseLoginBuilder(this)
            startActivityForResult(builder.build(), 0)


        }



        return super.onOptionsItemSelected(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == VideoPicker.VIDEO_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            mPaths = data.getSerializableExtra(VideoPicker.EXTRA_VIDEO_PATH) as List<String>

            var intent = Intent(this, DisplaySelectedPlotVideoActivity::class.java)

            startActivity(intent)


        }
    }


}








