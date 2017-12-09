package com.ngobiro.biroproperties.activities


import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import com.ngobiro.biroproperties.R
import kotlinx.android.synthetic.main.activity_play_video.*
import android.widget.*
import org.jetbrains.anko.longToast
import android.view.MenuItem


class PlayVideoActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setTitle("Plot Video")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        setContentView(R.layout.activity_play_video)



        plot_video_txtView.text = "$myplotGlobalTitle"

        val video_url :String?= plotVideo?.url
        val simpleVideoView: VideoView = findViewById(R.id.plot_videoView)  // initiate a video view

        if (plotVideo?.url.isNullOrBlank()) {

            longToast("No Plot Video Found!!!")
        }

        else {
            val mediaControls = MediaController(this)
            mediaControls.setAnchorView(simpleVideoView)
            simpleVideoView.setMediaController(mediaControls)
            simpleVideoView.setVideoURI(Uri.parse(video_url))
            simpleVideoView.start()


        }

    }








    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }


        return true
    }







    }



