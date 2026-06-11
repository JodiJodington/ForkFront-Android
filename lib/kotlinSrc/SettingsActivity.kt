package com.tbd.forkfront
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State


public class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(
                modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background)
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight().background(color = MaterialTheme.colorScheme.surface).align(Alignment.Center)
                ) {
                    SettingsSwitch(title = "Fullscreen")
                }
            }
        }
    }
}

@Composable
fun SettingsSwitch(title: String) {
    var checked = remember { mutableStateOf(true) }
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            title,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.align(Alignment.CenterStart)
        )
        Switch(
            checked = checked.value,
            onCheckedChange = { bool: Boolean ->
                checked.value = bool
            },
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}

