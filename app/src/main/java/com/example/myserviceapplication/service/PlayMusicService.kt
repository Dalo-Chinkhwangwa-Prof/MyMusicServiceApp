package com.example.myserviceapplication.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.example.myserviceapplication.R

class PlayMusicService : Service() {

    lateinit var mediaPlayer: MediaPlayer
    lateinit var myBinder: PlayMusicBinder

    override fun onCreate() {
        super.onCreate()
        Log.d("TAG_X", "onCreate - Service")

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("TAG_X", "onStartCommand - Service")

        mediaPlayer = MediaPlayer.create(this, R.raw.android_service_sound)
        mediaPlayer.isLooping = true

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("TAG_X", "onBind - Service")
        myBinder = PlayMusicBinder()
        return myBinder
    }

    fun playPressed() {
        if (!mediaPlayer.isPlaying)
            mediaPlayer.start()
    }

    fun pausePressed() {
        if (mediaPlayer.isPlaying)
            mediaPlayer.pause()
    }

    fun stopPressed() {
        mediaPlayer.reset()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        mediaPlayer.stop()
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        Log.d("TAG_X", "onDestroy - Service")
    }


    inner class PlayMusicBinder : Binder() {
        fun getMusicService(): PlayMusicService {
            return this@PlayMusicService
        }
    }
}