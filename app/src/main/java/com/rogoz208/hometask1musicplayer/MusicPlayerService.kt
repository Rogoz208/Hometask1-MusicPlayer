package com.rogoz208.hometask1musicplayer

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat

class MusicPlayerService : Service() {

    private lateinit var player: MusicPlayer

    override fun onCreate() {
        super.onCreate()
        player = MusicPlayer(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> start()
            Actions.PAUSE.toString() -> player.pause()
            Actions.NEXT.toString() -> player.next()
            Actions.PREVIOUS.toString() -> player.previous()
            Actions.STOP.toString() -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, "music_player_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Player is active")
            .build()

        player.start()

        startForeground(1, notification)
    }

    private fun stop() {
        player.stop()
        stopSelf()
    }

    enum class Actions {
        START, STOP, PAUSE, NEXT, PREVIOUS
    }
}