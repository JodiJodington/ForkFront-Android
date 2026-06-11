package com.tbd.forkfront
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.BackHandler
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State


public class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Settings {
                finish()
            }
        }
    }
}

enum class SettingsPage {
    main,
    tileset,
}

@Composable
fun Settings(exitCallback: () -> Unit) {
    val page = remember { mutableStateOf(SettingsPage.main) }
    val callback = { newPage: SettingsPage -> page.value = newPage }

    BackHandler(
        onBack = {
            when (page.value) {
                SettingsPage.main -> exitCallback()
                else -> page.value = SettingsPage.main
            }
        }
    )

    when (page.value) {
        SettingsPage.main -> SettingsMainPage(callback)
        SettingsPage.tileset -> SettingsTilesetPage(callback)
    }
}

@Composable
fun SettingsMainPage(newPageCallback: (SettingsPage) -> Unit) {
    val scrollState = rememberScrollState();
    Box(
        modifier = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme .colorScheme.background)
    ) {
        Column(
            modifier = Modifier
            .fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.surface)
            .align(Alignment.Center)
            .verticalScroll(scrollState)
        ) {
            SettingsChangePage(
                title = "Tileset",
                page = SettingsPage.tileset,
                callback = newPageCallback
            )
            SettingsSwitch(
                title = "Fullscreen",
                callback = {}
            )
        }
    }
}

@Composable
fun SettingsTilesetPage(newPageCallback: (SettingsPage) -> Unit) {
    val scrollState = rememberScrollState();
    val selectedOption = remember { mutableStateOf("TTY") }
    Box(
        modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background)
    ) {

        val names = stringArrayResource(R.array.tileNames)
        val values = stringArrayResource(R.array.tileNames)
        if (names.size != values.size) throw IllegalArgumentException("tileNames and tileValues must be of equal length")
        Column(
            modifier = Modifier
            .fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.surface)
            .align(Alignment.Center)
            .verticalScroll(scrollState)
        ) {
            names.indices.forEach {i ->
                val name = names[i]
                val value = values[i]
                Row(
                    modifier = Modifier.fillMaxWidth()
                ){
                    RadioButton(
                        selected = value == selectedOption.value,
                        onClick = {
                            selectedOption.value = value
                        }
                    )
                    Text(
                        name,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsChangePage(title: String, page: SettingsPage, callback: (SettingsPage) -> Unit) {
    Box(
        modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .clickable(
            onClick = {
                callback(page)
            }
        )
    ) {
        Text(
            title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(Alignment.CenterStart),
        )
    }
}

@Composable
fun SettingsSwitch(title: String, callback: (Boolean) -> Unit) {
    val checked = remember { mutableStateOf(true) }
    Box(
        modifier = Modifier.fillMaxWidth().padding(16.dp)
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
