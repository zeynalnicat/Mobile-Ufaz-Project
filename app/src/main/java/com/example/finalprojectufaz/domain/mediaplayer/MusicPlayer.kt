package com.example.finalprojectufaz.domain.mediaplayer

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

class MusicPlayer {
     private var mPlayer:MediaPlayer? = null
    var isPlaying = false


    companion object {
        private var instance: MusicPlayer? = null

        fun getInstance(): MusicPlayer {
            if (instance == null) {
                instance = MusicPlayer()
            }
            return instance!!
        }
    }

    fun startMusic(context: Context, uri: String) {
        val musicPlayer = getInstance()
        musicPlayer.mPlayer?.stop()
        musicPlayer.mPlayer?.release()
        isPlaying = true
        musicPlayer.mPlayer = MediaPlayer().apply {
            try {
                setDataSource(context, Uri.parse(uri))
                prepare()
                start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun stopMusic() {
        getInstance().mPlayer?.let {
            if (it.isPlaying) {
                it.stop()
                isPlaying = false
            }
            it.release()
            instance?.mPlayer = null
        }
    }

    fun pauseMusic() {
        mPlayer?.pause()
    }
    fun seekTo(position: Int) {
        mPlayer?.seekTo(position)
    }

    fun getCurrentPosition(): Int {
        return mPlayer?.currentPosition ?: 0
    }

    fun getDuration():Int {
        return mPlayer?.duration ?: 0
    }

    fun resumeMusic(context: Context, uri: String, position: Int) {
        if (mPlayer == null) {
            isPlaying = true
            mPlayer = MediaPlayer().apply {
                setDataSource(uri)
                prepare()
                seekTo(position)
                start()
            }
        } else {
            mPlayer?.seekTo(position)
            mPlayer?.start()
        }
    }

}