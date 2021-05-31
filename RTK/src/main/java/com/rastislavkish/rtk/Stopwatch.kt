package com.rastislavkish.rtk

class Stopwatch {

    val elapsed
    get() = System.currentTimeMillis()-initialTime

    private var initialTime=System.currentTimeMillis()

    fun restart()
        {
        initialTime=System.currentTimeMillis()
        }

    }

