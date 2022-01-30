package com.snapscreen.example

import android.app.Application
import com.snapscreen.mobile.Environment
import com.snapscreen.mobile.Snapscreen

class Application: Application() {

    override fun onCreate() {
        super.onCreate()

        Snapscreen.setup(this, "your-client-id", "your-client-secret", Environment.TEST)
        Snapscreen.instance?.country = "US"
        Snapscreen.instance?.usState = "PA"
    }
}