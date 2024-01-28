package com.rogoz208.hometask1musicplayer

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rogoz208.hometask1musicplayer.databinding.ActivityMusicPlayerBinding

class MusicPlayerActivity : AppCompatActivity(R.layout.activity_music_player) {

    private var isPlaying: Boolean = false

    private val binding by viewBinding(ActivityMusicPlayerBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissions()
        setupListeners()
    }

    private fun requestPermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }
    }

    private fun setupListeners() {
        binding.playPauseButton.setOnClickListener {
            isPlaying = if (isPlaying) {
                startServiceWithAction(MusicPlayerService.Actions.PAUSE)
                it.setBackgroundResource(R.drawable.baseline_play_circle_24)
                false
            } else {
                startServiceWithAction(MusicPlayerService.Actions.START)
                it.setBackgroundResource(R.drawable.baseline_pause_circle_24)
                true
            }
        }

        binding.nextButton.setOnClickListener {
            startServiceWithAction(MusicPlayerService.Actions.NEXT)
        }

        binding.previousButton.setOnClickListener {
            startServiceWithAction(MusicPlayerService.Actions.PREVIOUS)
        }

        binding.stopButton.setOnClickListener {
            startServiceWithAction(MusicPlayerService.Actions.STOP)
            isPlaying = false
            binding.playPauseButton.setBackgroundResource(R.drawable.baseline_play_circle_24)
        }
    }

    private fun startServiceWithAction(action: MusicPlayerService.Actions) {
        Intent(applicationContext, MusicPlayerService::class.java).also {
            it.action = action.toString()
            startService(it)
        }
    }
}