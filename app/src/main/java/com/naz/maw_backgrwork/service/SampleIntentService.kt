package com.naz.maw_backgrwork.service

import android.app.IntentService
import android.content.Intent
import android.util.Log

/** Deprecated
IntentService is subject to all the background execution limits imposed with Android 8.0 (API level 26).
Consider using androidx.work.WorkManager instead.
See Also:
androidx.core.app.JobIntentService*/

class SampleIntentService : IntentService("SampleIntentService") {

    private var counter: Int = 0

    @Deprecated("Deprecated in Java")
    override fun onHandleIntent(intent: Intent?) {
        Log.e("TAG", "Intent service handled")
        while (counter < 1_000_000) {
            try {
                Thread.sleep(1000)
                counter++
                Log.e("TAG", "Counter $counter")
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}