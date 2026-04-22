package com.naz.maw_backgrwork

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
import com.naz.maw_backgrwork.ui.theme.MAW_BackgrWorkTheme

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

                            }
                            Spacer(modifier = Modifier.padding(16.dp))

                            RegularButton(text = "Start Foreground Service") {

                            }
                            Spacer(modifier = Modifier.padding(16.dp))

                            RegularButton(text = "Start Intent Service") {

                            }
                            Spacer(modifier = Modifier.padding(16.dp))

                            RegularButton(text = "Start Alarm Manager") {

                            }
                            Spacer(modifier = Modifier.padding(16.dp))

                            RegularButton(text = "Start Work Manager") {

                            }
                            Spacer(modifier = Modifier.padding(16.dp))

                            RegularButton(text = "Start Job Scheduler") {

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

