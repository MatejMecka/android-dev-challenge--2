 /*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.androiddevchallenge.ui.theme.MyTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import androidx.compose.material.ProgressIndicatorDefaults
import kotlinx.coroutines.delay
import androidx.compose.ui.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    //var
    var counter by remember { mutableStateOf(60) }
    var startTime by remember { mutableStateOf(60) }
    var started by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(1.0f) }

    val progressAnimated = animateFloatAsState(
        targetValue = progress,
        ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    Surface(color = MaterialTheme.colors.background) {
        Column(){
        Text(text = "Ready... Set... GO!")

            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                CircularProgressIndicator(
                    progress = progressAnimated,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp)
                )
                Text(text = "${counter}", modifier = Modifier.padding(start = 8.dp, bottom = 8.dp).animateContentSize())
            }
            OutlinedTextField(
                value = startTime.toString(),
                onValueChange = {
                    startTime = Integer.parseInt(it.replace(Regex("[^0123456789]"), ""))
                    counter = startTime
                },
            )
            FloatingActionButton(
                modifier = Modifier
                    .padding(top = 256.dp)
                    .size(64.dp),
                onClick = {
                    started = !started
                }
            ) {
                Icon(if (started) Icons.Rounded.Pause else Icons.Rounded.PlayArrow, contentDescription = if (started) "Pause" else "Continue")
            }

        }
    }

    if(started == false){
        LaunchedEffect("tick") {
            for(i in counter downTo 0){
                delay(1000)
                counter = i
                progress = (counter*1.0f) / startTime
                println(counter)
            }
            //started = false
        }
    }

}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
