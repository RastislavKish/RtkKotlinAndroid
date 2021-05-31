package com.rastislavkish.rtkkotlinandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent

import android.media.SoundPool

import com.rastislavkish.rtk.Sound
import com.rastislavkish.rtk.Speech
import com.rastislavkish.rtk.Stopwatch
import com.rastislavkish.rtk.TouchWrapper
import com.rastislavkish.rtk.GestureEventArgs

class MainActivity : AppCompatActivity() {

    private val stopwatch=Stopwatch()
    lateinit private var speech: Speech
    private val touchWrapper=TouchWrapper()

    private val bump=Sound()

    override fun onCreate(savedInstanceState: Bundle?)
        {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        speech=Speech(this)

        touchWrapper.addSwipeLeftListener(this::swipeLeft)
        touchWrapper.addSwipeRightListener(this::swipeRight)
        touchWrapper.addSwipeUpListener(this::swipeUp)
        touchWrapper.addSwipeDownListener(this::swipeDown)
        touchWrapper.addTapListener(this::tap)

        bump.load("Bump.opus", this)

        }
    override fun onTouchEvent(event: MotionEvent): Boolean
        {
        touchWrapper.update(event)

        return super.onTouchEvent(event)
        }

    fun swipeLeft(e: GestureEventArgs)
        {
        speech.speak("Swiped left with ${e.fingerCount} fingers")
        }
    fun swipeRight(e: GestureEventArgs)
        {
        speech.speak("Swiped right with ${e.fingerCount} fingers")
        }
    fun swipeUp(e: GestureEventArgs)
        {
        bump.play()
        speech.speak("Swiped up with ${e.fingerCount} fingers")
        }
    fun swipeDown(e: GestureEventArgs)
        {
        speech.speak("Swiped down with ${e.fingerCount} fingers")
        }
    fun tap(e: GestureEventArgs)
        {
        speech.speak("Tapped with ${e.fingerCount} fingers after ${stopwatch.elapsed} ms")
        stopwatch.restart()
        }

    }
