package com.cholee.quarter_page

import android.app.Application
import android.content.Context

class MyApplication : Application() {
    init{
        instance = this
    }
    companion object {
        lateinit var instance: MyApplication
        fun applicationContext() : Context {
            return instance.applicationContext
        }
    }
}