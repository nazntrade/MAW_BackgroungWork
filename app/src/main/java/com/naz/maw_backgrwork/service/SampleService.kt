package com.naz.maw_backgrwork.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

enum class ServiceType(val value: Int) {
    Regular(0),
    Foreground(1)
}

class SampleService : Service() {

    private var counter = 0

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val type = intent?.extras?.getInt(SERVICE_TYPE) ?: 0
        val serviceType =
            when(type) {
                1 -> ServiceType.Foreground
                else -> ServiceType.Regular
            }

        
    }

    companion object {
        const val SERVICE_TYPE = "Service type"
    }
}