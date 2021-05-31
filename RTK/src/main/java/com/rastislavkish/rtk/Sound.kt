package com.rastislavkish.rtk

import android.content.Context
import android.media.SoundPool

class Sound {

    var soundID=0
    var streamID=0

    fun load(filePath: String, context: Context)
        {
        soundID=Sound.sp.load(context.getAssets().openFd(filePath), 1)
        }

    fun play()
        {
        streamID=Sound.sp.play(soundID, 1.0f, 1.0f, 1, 0, 1.0f)
        }
    fun playLooped()
        {
        streamID=Sound.sp.play(soundID, 1.0f, 1.0f, 1, 1, 1.0f)
        }
    fun replay()
        {
        stop()
        play()
        }

    fun pause()
        {
        Sound.sp.pause(streamID)
        }
    fun resume()
        {
        Sound.sp.resume(streamID)
        }
    fun stop()
        {
        Sound.sp.stop(streamID)
        }

    companion object {

        private val sp: SoundPool

        init {
            val builder=SoundPool.Builder()
            sp=builder.build()
            }
        }

    }

