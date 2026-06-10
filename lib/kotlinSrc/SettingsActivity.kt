package com.tbd.forkfront
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


public class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInsance: Bundle?) {
        super.onCreate(savedInsance)
        setContent {
            Text(
                "Hello, World!",
                modifier = Modifier.fillMaxSize().background(Color.Red),
                color = Color.Blue
            )
        }
    }
}
