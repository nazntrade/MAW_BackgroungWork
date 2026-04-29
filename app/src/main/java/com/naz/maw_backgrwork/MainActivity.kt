package com.naz.maw_backgrwork

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.job.JobInfo
import android.content.ComponentName
import android.app.job.JobScheduler
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.naz.maw_backgrwork.alarm.SampleAlarm
import com.naz.maw_backgrwork.job.SampleJobService
import com.naz.maw_backgrwork.service.SampleForegroundService
import com.naz.maw_backgrwork.service.SampleIntentService
import com.naz.maw_backgrwork.service.SampleService
import com.naz.maw_backgrwork.ui.theme.MAW_BackgrWorkTheme
import com.naz.maw_backgrwork.workmanager.SampleWorkManager
import java.util.Calendar
import android.util.Log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MAW_BackgrWorkTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            RegularButton(text = "Start Regular service") {
                                val intent = Intent(applicationContext, SampleService::class.java)
                                startService(intent)
                            }
                            Spacer(modifier = Modifier.padding(16.dp))

                            RegularButton(text = "Start Foreground Service") {
                                val intent = Intent(applicationContext, SampleForegroundService::class.java)
                                startService(intent)
                            }
                            Spacer(modifier = Modifier.padding(16.dp))

                            RegularButton(text = "Start Intent Service") {
                                val intent = Intent(applicationContext, SampleIntentService::class.java)
                                startService(intent)
                            }
                            Spacer(modifier = Modifier.padding(16.dp))

                            RegularButton(text = "Start Alarm Manager") {
                                val calendar = Calendar.getInstance()
                                calendar.add(Calendar.SECOND, 10)
                                val intent = Intent(applicationContext, SampleAlarm::class.java)
                                val pendingIntent = PendingIntent.getBroadcast(
                                    applicationContext,
                                    0,
                                    intent,
                                    PendingIntent.FLAG_IMMUTABLE
                                )
                                val alarmManager = getSystemService(ALARM_SERVICE) as? AlarmManager
                                alarmManager?.let {
                                    try {
                                        it.setExact(
                                            AlarmManager.RTC_WAKEUP,
                                            calendar.timeInMillis,
                                            pendingIntent
                                        )
                                    } catch (e: SecurityException) {
                                        e.printStackTrace()
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.padding(16.dp))

                            RegularButton(text = "Start Work Manager") {
                                val constraints = Constraints.Builder()
                                    .setRequiresCharging(false)
                                    .setRequiredNetworkType(NetworkType.CONNECTED)
                                    .setRequiresBatteryNotLow(true)
                                    .build()

                                val worker = OneTimeWorkRequest.Builder(SampleWorkManager::class.java)
                                    .setConstraints(constraints)
                                    .build()

                                WorkManager.getInstance(applicationContext).enqueue(worker)
                            }
                            Spacer(modifier = Modifier.padding(16.dp))

                            RegularButton(text = "Start Job Scheduler") {
                                val componentName =
                                    ComponentName(applicationContext, SampleJobService::class.java)
                                val jobInfo = JobInfo.Builder(1, componentName)
                                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                                    .setPersisted(true)
                                    .setPeriodic((15 * 60 * 1000).toLong())
                                    .build()

                                val jobScheduler =
                                    getSystemService(JOB_SCHEDULER_SERVICE) as? JobScheduler
                                jobScheduler?.let {
                                    val result = jobScheduler.schedule(jobInfo)
                                    if (result == JobScheduler.RESULT_SUCCESS) {
                                        Log.d("TAG", "Job scheduled successfully")
                                    } else {
                                        Log.d("TAG", "Job scheduling failed")
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.padding(16.dp))
                        }
                    }

                }
            }
        }
    }

    @Composable
    private fun RegularButton(text: String, onClick: () -> Unit) {
        Button(onClick = onClick) {
            Text(text = text)
        }
    }
}

