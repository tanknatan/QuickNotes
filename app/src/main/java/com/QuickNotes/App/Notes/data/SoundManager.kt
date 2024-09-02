package com.Color.Sphere.Challenge.gamecolor.data




import android.content.Context
import android.media.MediaPlayer
import com.QuickNotes.App.Notes.R


object SoundManager {
    private lateinit var musicPlayer: MediaPlayer


    fun init(context: Context) = runCatching {
        musicPlayer = MediaPlayer.create(context, R.raw.music)

    }

    fun playMusic() = runCatching {
        musicPlayer.setVolume(Prefs.music, Prefs.music)
        musicPlayer.start()
        musicPlayer.isLooping = true
    }


    fun pauseMusic() = runCatching{
        musicPlayer.pause()

    }


    fun resumeMusic() = runCatching {
        if (!musicPlayer.isPlaying) {
            musicPlayer.start()
        }
    }



    fun onDestroy() = runCatching {
        musicPlayer.release()

    }

    fun setVolumeMusic() = runCatching {
        musicPlayer.setVolume(Prefs.music, Prefs.music)
    }




}