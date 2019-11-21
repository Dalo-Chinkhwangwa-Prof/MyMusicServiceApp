package com.example.myserviceapplication.view

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.example.myserviceapplication.R
import com.example.myserviceapplication.service.PlayMusicService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var serviceIntent: Intent

    var playMusicService: PlayMusicService? = null

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
//        only called when there was an error binding to service
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            playMusicService = (service as PlayMusicService.PlayMusicBinder).getMusicService()

            Log.d("TAG_X", "onServiceConnected")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        serviceIntent = Intent(this, PlayMusicService::class.java)

        play_button.setOnClickListener {
            if (playMusicService == null) {
                startService(serviceIntent)
                bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
            }
            playMusicService?.playPressed()
        }

        pause_button.setOnClickListener {
            if (playMusicService != null)
                playMusicService?.pausePressed()
        }

        stop_button.setOnClickListener {
            if (playMusicService != null) {
                unbindService(serviceConnection)
                playMusicService = null
            }
        }
    }

    override fun onStop() {
        super.onStop()
//        stopService(serviceIntent)
    }

}
