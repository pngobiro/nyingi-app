package com.ngobiro.biroproperties.activities

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import com.ngobiro.biroproperties.R
import com.parse.*
import kotlinx.android.synthetic.main.activity_display_selected_plot_video.*
import org.jetbrains.anko.longToast
import net.alhazmy13.mediapicker.Utility.getRealPathFromURI
import net.alhazmy13.mediapicker.Utility.getRealPathFromURI
import java.io.*



class DisplaySelectedPlotVideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_selected_plot_video)


        val selected_video_path: String? = mPaths?.first()
        val simpleVideoView: VideoView = findViewById(R.id.plot_add_videoView)  // initiate a video view


        if (selected_video_path.isNullOrBlank()) {

            longToast("No Plot Video Selected!!!")
        } else {

            plot_add_video_btn.text = "Add Video to $selected_plot_title"


            plot_add_video_btn.setOnClickListener {

                addVideoToPlot(selected_video_path)

            }

            val mediaControls = MediaController(this)
            mediaControls.setAnchorView(simpleVideoView)
            simpleVideoView.setMediaController(mediaControls)
            simpleVideoView.setVideoURI(Uri.parse(selected_video_path))
            simpleVideoView.start()


        }


    }


    fun addVideoToPlot(video_url: String?) {


        val plot = ParseQuery.getQuery<ParseObject>("Plots")
        var plotObj = plot[selected_plot_id]

        val file = File(video_url)
        var bytes: ByteArray = file.readBytes()


        val parseFile = ParseFile("video.mp4", bytes)


        plotObj.put("video", parseFile)

        plotObj.saveInBackground { e ->
            if (e != null) {
                Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
            } else {


                Toast.makeText(applicationContext, "Plot Saved $myplotGlobalTitle", Toast.LENGTH_LONG).show()
                val intent = Intent(applicationContext, MyPlotsActivity::class.java)
                startActivity(intent)
            }
        }


    }



}











