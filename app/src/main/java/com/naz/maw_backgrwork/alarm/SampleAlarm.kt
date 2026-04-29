package com.naz.maw_backgrwork.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class SampleAlarm : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        repeat(6) {
            Toast.makeText(context, "ALARM!", Toast.LENGTH_SHORT).show()
            Log.e("TAG", "Run event")
            Thread.sleep(3000)
        }
    }
}