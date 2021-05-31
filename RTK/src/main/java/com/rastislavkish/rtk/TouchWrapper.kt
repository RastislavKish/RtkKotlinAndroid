package com.rastislavkish.rtk

import android.view.MotionEvent
import android.view.View

class Point(val x: Float=0.0f, val y: Float=0.0f) {

    fun distanceFrom(point: Point) = Math.sqrt(Math.pow((point.x-x).toDouble(), 2.0)+Math.pow((point.y-y).toDouble(), 2.0)).toFloat()
    fun horizontalDistanceFrom(point: Point) = Math.abs(point.x-x)
    fun verticalDistanceFrom(point: Point) = Math.abs(point.y-y)

    }
class GestureEventArgs(fingerCount: Int) {

    val fingerCount=fingerCount

    }
class TouchWrapper {

    var horizontalPosition=0.0f
    private set

    var verticalPosition=0.0f
    private set

    //-1;-1 is the unset value
    private var initialPoint=Point() //Here it doesn't mather, whether the point is set or not
    private var lastPoint=Point(-1.0f, -1.0f)
    private var fingerCount=0

    private val swipeLeftListeners=mutableListOf<(GestureEventArgs) -> Unit>()
    private val swipeRightListeners=mutableListOf<(GestureEventArgs) -> Unit>()
    private val swipeUpListeners=mutableListOf<(GestureEventArgs) -> Unit>()
    private val swipeDownListeners=mutableListOf<(GestureEventArgs) -> Unit>()
    private val tapListeners=mutableListOf<(GestureEventArgs) -> Unit>()

    fun update(event: MotionEvent)
        {
        when (event.getActionMasked()) {
            MotionEvent.ACTION_DOWN -> {
                initialPoint=Point(event.getX(), event.getY())
                }
            MotionEvent.ACTION_MOVE -> {
                if (event.getActionIndex()==0) {
                    horizontalPosition=event.getX()-initialPoint.x
                    verticalPosition=event.getY()-initialPoint.y
                    }
                }
            MotionEvent.ACTION_UP -> {
                if (lastPoint.x==-1.0f && lastPoint.y==-1.0f) {
                    lastPoint=Point(event.getX(), event.getY())
                    }

                val eventArgs=GestureEventArgs(if (fingerCount!=0) fingerCount else 1)
                fingerCount=0

                if (initialPoint.distanceFrom(lastPoint)>=150) {
                    // We have a swipe

                    val horizontalDistance=initialPoint.horizontalDistanceFrom(lastPoint)
                    val verticalDistance=initialPoint.verticalDistanceFrom(lastPoint)

                    // We can determine if the swipe was horizontal or vertical by comparing the distances
                    // And the sign of the longer axis can determine the final direction

                    if (horizontalDistance>=verticalDistance) {
                        if (horizontalPosition>=0) {
                            // Swipe right

                            raiseEvent(swipeRightListeners, eventArgs)
                            }
                        else {
                            // Swipe left

                            raiseEvent(swipeLeftListeners, eventArgs)
                            }
                        }
                    else {
                        if (verticalPosition>=0) {
                            // Swipe down

                            raiseEvent(swipeDownListeners, eventArgs)
                            }
                        else {
                            // Swipe up

                            raiseEvent(swipeUpListeners, eventArgs)
                            }
                        }
                    }
                else {
                    // Tap

                    raiseEvent(tapListeners, eventArgs)
                    }

                fingerCount=0
                horizontalPosition=0.0f
                verticalPosition=0.0f
                lastPoint=Point(-1.0f, -1.0f)
                }
            MotionEvent.ACTION_POINTER_UP -> {
                if (fingerCount==0) fingerCount=event.getPointerCount()
                if (event.getActionIndex()==0 && lastPoint.x==-1.0f && lastPoint.y==-1.0f) {
                    lastPoint=Point(event.getX(), event.getY())
                    }
                }
            }
        }

    fun addSwipeLeftListener(listener: (GestureEventArgs) -> Unit)
        {
        swipeLeftListeners.add(listener)
        }
    fun addSwipeRightListener(listener: (GestureEventArgs) -> Unit)
        {
        swipeRightListeners.add(listener)
        }
    fun addSwipeUpListener(listener: (GestureEventArgs) -> Unit)
        {
        swipeUpListeners.add(listener)
        }
    fun addSwipeDownListener(listener: (GestureEventArgs) -> Unit)
        {
        swipeDownListeners.add(listener)
        }
    fun addTapListener(listener: (GestureEventArgs) -> Unit)
        {
        tapListeners.add(listener)
        }

    private fun raiseEvent(listeners: MutableList<(GestureEventArgs) -> Unit>, args: GestureEventArgs)
        {
        for (listener in listeners) {
            listener(args)
            }
        }
    }

