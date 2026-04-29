package com.naz.maw_backgrwork.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.naz.maw_backgrwork.R

class SampleForegroundService: Service() {

    private var counter = 0

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(1, getNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val handler = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            Handler()
        } else {
            Handler.createAsync(Looper.getMainLooper())
        }

        Thread {
            while (counter < 1_000_000) {
                try {
                    Thread.sleep(1000)
                    counter++
                    Log.e("TAG", "Counter $counter")
                    if ((counter >= 100 && counter % 100 == 0) || counter == 1 || counter == 2 || counter == 3) {
                        handler.post {
                            Toast.makeText(
                                applicationContext,
                                "Hooray! $counter",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    val notification = getNotification()
                    startForeground(1, notification)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()

        return START_STICKY
    }

    private fun getNotification(): Notification {
        return NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle("My Foreground Service Running")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentText("Counter: $counter")
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "My Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager?.createNotificationChannel(serviceChannel)
        }
    }

    companion object {
        const val CHANNEL_ID = "My Channel"
    }
}