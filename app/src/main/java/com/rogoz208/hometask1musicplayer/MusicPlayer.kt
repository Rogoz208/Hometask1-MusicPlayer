package com.rogoz208.hometask1musicplayer

import android.content.Context
import android.media.MediaPlayer

class MusicPlayer(private val context: Context) {

    private val tracks = listOf(R.raw.music1, R.raw.music2, R.raw.music3, R.raw.music4)
    private var player: MediaPlayer? = null
    private var currentTrack: Int = 0
    private var currentPosition: Int? = null

    fun start() {
        if (currentPosition == null) {
            player = MediaPlayer.create(context, tracks[currentTrack])
            player?.start()
        } else {
            player = MediaPlayer.create(context, tracks[currentTrack])
            currentPosition?.let {
                player?.seekTo(it)
                player?.start()
            }
        }
    }

    fun pause() {
        player?.pause()
        currentPosition = player?.currentPosition
        stop()
    }

    fun next() {
        if (currentTrack == tracks.size - 1) currentTrack = 0 else currentTrack++
        if (player?.isPlaying == true) {
            stop()
            start()
        }
    }

    fun previous() {
        if (currentTrack == 0) currentTrack = tracks.size - 1 else currentTrack--
        if (player?.isPlaying == true) {
            stop()
            start()
        }
    }

    fun stop() {
        player?.stop()
        player?.release()
        player = null
    }
}