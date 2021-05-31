package com.rastislavkish.rtk

import android.content.Context
import android.speech.tts.TextToSpeech

class Speech(context: Context) {

    private val tts=TextToSpeech(context, null)

    fun speak(text: String, interrupt: Boolean=true)
        {
        tts.speak(text, if (interrupt) TextToSpeech.QUEUE_FLUSH else TextToSpeech.QUEUE_ADD, null, null)
        }

    }
