package com.tbd.forkfront
import android.os.Bundle
import android.net.Uri
import android.graphics.Bitmap
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.BackHandler
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.RadioButton
import androidx.compose.material3.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


public class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
			Box(
				modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
			) {
				SettingsScreen (
					exitCallback = {
						finish()
					}
				) 
			}
        }
    }
}

enum class SettingsPage {
    main,
    tileset,
    directional_overlay,
    command_panels,
    hearse,
    edit_options_file,
    credits
}

@Composable
fun SettingsScreen(
	exitCallback: () -> Unit
) {
	val settingsViewModel: SettingsViewModel = viewModel(
		extras = MutableCreationExtras().apply {
			set(SettingsViewModel.CONTEXT_KEY, LocalContext.current)
		},
		factory = SettingsViewModel.Factory
	)
    val page = remember { mutableStateOf(SettingsPage.main) }
    val scrollState = rememberScrollState();

    BackHandler(
        onBack = {
            when (page.value) {
                SettingsPage.main -> exitCallback()
                else -> page.value = SettingsPage.main
            }
        }
    )

    Column(
		verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxSize()
			.padding(16.dp)
            .background(color = MaterialTheme.colorScheme.surface)
            .verticalScroll(scrollState)
    ) {
        val callback = { newPage: SettingsPage ->
            page.value = newPage 
        }
        when (page.value) {
            SettingsPage.main -> SettingsMainPage(
                newPageCallback = callback,
				settingsViewModel = settingsViewModel
            )
            SettingsPage.tileset -> SettingsTilesetPage(
				newPageCallback = callback,
				settingsViewModel = settingsViewModel
			)
            SettingsPage.directional_overlay -> SettingsDirectionalOverlayPage(
				newPageCallback = callback,
				settingsViewModel = settingsViewModel
			)
            SettingsPage.command_panels -> SettingsCommandPanelPage(
				newPageCallback = callback,
				settingsViewModel = settingsViewModel
			)
            SettingsPage.hearse -> SettingsHearsePage(
				newPageCallback = callback,
				settingsViewModel = settingsViewModel
			)
			SettingsPage.edit_options_file -> SettingsEditOptionsFilePage(
				newPageCallback = callback,
				settingsViewModel = settingsViewModel
			)
            SettingsPage.credits -> SettingsCreditPage()
        }
    }
}

@Composable
fun SettingsMainPage(
	newPageCallback: (SettingsPage) -> Unit,
	settingsViewModel: SettingsViewModel,
) {
	val fullscreen = settingsViewModel.fullscreen.collectAsStateWithLifecycle(initialValue = false)
	val immersiveMode = settingsViewModel.immersiveMode.collectAsStateWithLifecycle(initialValue = false)
	val volumeDownAction = settingsViewModel.volumeDownAction.collectAsStateWithLifecycle(initialValue = "0")
	val volumeUpAction = settingsViewModel.volumeUpAction.collectAsStateWithLifecycle(initialValue = "0")
	val lockView = settingsViewModel.lockView.collectAsStateWithLifecycle(initialValue = false)
	val monospaceMode = settingsViewModel.monospaceMode.collectAsStateWithLifecycle(initialValue = false)
	val statusBackgroundOpacity = settingsViewModel.statusBackgroundOpacity.collectAsStateWithLifecycle(initialValue = 0)
	val mapBorderOpacity = settingsViewModel.mapBorderOpacity.collectAsStateWithLifecycle(initialValue = 255)
	val fallbackRenderer = settingsViewModel.fallbackRenderer.collectAsStateWithLifecycle(initialValue = false)
    SettingsCategory("Main")
    SettingsChangePage(
        title = "Tileset",
        description = "The visual style of the game world",
        page = SettingsPage.tileset,
        callback = newPageCallback
    )
    SettingsSwitch(
        title = "Fullscreen",
        description = "Enable fullscreen mode",
        checked = fullscreen.value,
        callback = { newValue ->
			settingsViewModel.updateFullscreen(newValue)
		}
    )
    SettingsSwitch(
        title = "Immersive mode",
        description = "Hide the on-screen navigation bar",
        checked = immersiveMode.value,
        callback = { newValue ->
			settingsViewModel.updateImmersiveMode(newValue)
		}
    )
    SettingsSelectDialog(
        title = "Volume down action",
        description = "The action performed when pressing the volume down key",
        itemNames = stringArrayResource(R.array.actionNames),
        itemValues = stringArrayResource(R.array.actionValues),
        selected = volumeDownAction.value,
        callback = { newValue ->
			settingsViewModel.updateVolumeDownAction(newValue)
		}
    )
    SettingsSelectDialog(
        title = "Volume up action",
        description = "The action performed when pressing the volume up key",
        itemNames = stringArrayResource(R.array.actionNames),
        itemValues = stringArrayResource(R.array.actionValues),
        selected = volumeUpAction.value,
		callback = { newValue ->
			settingsViewModel.updateVolumeUpAction(newValue)
		}
    )
    SettingsSwitch(
        title = "Lock view",
        description = "Do not move the view with the character whent he entire map can fit the screen",
        checked = lockView.value,
        callback = { newValue ->
			settingsViewModel.updateLockView(newValue)
		}
    )
    SettingsSwitch(
        title = "Monospace mode",
        description = "Use a monospace font for nicer typography",
        checked = monospaceMode.value,
        callback = { newValue ->
			settingsViewModel.updateMonospaceMode(newValue)
		}
    )
    SettingsSlider(
        title = "Status background opacity",
        description = "The background opacity of the status lines",
        range = 0..255,
        start = statusBackgroundOpacity.value,
        callback = { newValue ->
			settingsViewModel.updateStatusBackgroundOpacity(newValue)
		}
    )
    SettingsSlider(
        title = "Map border opacity",
        description = "The opacity of the border around the map",
        range = 0..255,
        start = mapBorderOpacity.value,
        callback = {}
    )
    SettingsChangePage(
        title = "Directional Overlay",
        description = "Configure the directional overlay",
        page = SettingsPage.directional_overlay,
        callback = newPageCallback
    )
    SettingsChangePage(
        title = "Command panels",
        description = "Configure layout and content of the command panels",
        page = SettingsPage.command_panels,
        callback = newPageCallback
    )
    SettingsButton(
        title = "Reset command panel",
        description = "Restore the command panel to its original layout",
        callback = {}
    )
    SettingsCategory("Advanced")
    SettingsChangePage(
        title = "Hearse",
        description = "Allow players to share bones files over the internet",
        page = SettingsPage.hearse,
        callback = newPageCallback
    )
    SettingsChangePage(
        title = "Edit options file",
        description = "Manually specify options in defaults.nh",
        page = SettingsPage.edit_options_file,
        callback = newPageCallback
    )
    SettingsSwitch(
        title = "Use fallback renderer",
        description = "Check this if you experience rendering problems",
        checked = fallbackRenderer.value,
        callback = {newValue ->
			settingsViewModel.updateFallbackRenderer(newValue)
		}
    )
    SettingsCategory("About")
    SettingsChangePage(
        title = "Credits",
        description = "Contributors of this port",
        page = SettingsPage.credits,
        callback = newPageCallback
    )
}

@Composable
fun SettingsTilesetPage(
	newPageCallback: (SettingsPage) -> Unit,
	settingsViewModel: SettingsViewModel,
) {
    val scrollState = rememberScrollState();
    val tileset = settingsViewModel.tileset.collectAsStateWithLifecycle(initialValue = "TTY")
	val customTileW = settingsViewModel.customTileW.collectAsStateWithLifecycle(initialValue = 32)
	val customTileH = settingsViewModel.customTileH.collectAsStateWithLifecycle(initialValue = 32)

    val names = stringArrayResource(R.array.tileNames) + arrayOf("custom")
    val values = stringArrayResource(R.array.tileValues) + arrayOf("custom")
    if (names.size != values.size) throw IllegalArgumentException("tileNames and tileValues must be of equal length")
	names.indices.forEach {i ->
		val name = names[i]
		val value = values[i]
		Row(
			modifier = Modifier.fillMaxWidth()
		) {
			RadioButton(
				selected = value == tileset.value,
				onClick = {
					settingsViewModel.updateTileset(value)
				}
			)
			Text(
				name,
				modifier = Modifier.align(Alignment.CenterVertically),
				style = MaterialTheme.typography.titleMedium,
			)
		}
	}
	val customEnabled = tileset.value == "custom"
	Row {
		Text(
			"Tileset image:",
			style = MaterialTheme.typography.displayMedium,
		)
	}
	Row(
		horizontalArrangement = Arrangement.spacedBy(12.dp),
	){
		EnterSize(
			"width:",
			modifier = Modifier.weight(1f),
			enabled = customEnabled,
			start = customTileW.value,
			callback = { newValue ->
				settingsViewModel.updateCustomTileW(newValue)
			}
		)
		EnterSize(
			"height:",
			modifier = Modifier.weight(1f),
			enabled = customEnabled,
			start = customTileH.value,
			callback = { newValue ->
				settingsViewModel.updateCustomTileH(newValue)
			}
		)
	}
}
@Composable
fun SettingsDirectionalOverlayPage(
	newPageCallback: (SettingsPage) -> Unit,
	settingsViewModel: SettingsViewModel,
) {
    SettingsSwitch(
        title = "Always show in portrait",
        description = "Always show the directional overlay in portrait mode",
        checked = false,
        callback = {}
    )
    SettingsSwitch(
        title = "Always show in landscape",
        description = "Always show the directional overlay in landscape mode",
        checked = false,
        callback = {},
    )
    SettingsSelectDialog(
        title = "Portrait location",
        description = "The location of the overlay in portrait mode",
        itemNames = stringArrayResource(R.array.dirLocationNames),
        itemValues = stringArrayResource(R.array.dirLocationValues),
        selected = "0",
        callback = {}
    )
    SettingsSelectDialog(
        title = "Landscape location",
        description = "The location of the overlay in landscape mode",
        itemNames = stringArrayResource(R.array.dirLocationNames),
        itemValues = stringArrayResource(R.array.dirLocationValues),
        selected = "0",
        callback = {}
    )
    SettingsSlider(
        title = "Opacity",
        description = "The opacity of the overlay",
        range = 0..255,
        start = 255,
        callback = {},
    )
    SettingsSlider(
        title = "Size",
        description = "The relative size of the overlay",
        range = -10..10,
        start = 0,
        callback = {}
    )
    SettingsSwitch(
        title = "Allow map input with overlay",
        description = "Allow directional input by clicking on the map when the overlay is shown",
        checked = false,
        callback = {}
    )
}

@Composable
fun SettingsCommandPanelPage(
	page: Int = 0,
	newPageCallback: (SettingsPage) -> Unit,
	settingsViewModel: SettingsViewModel,
) {
    val page = remember { mutableStateOf(page) }
    BackHandler(
        enabled = page.value != 0,
        onBack = {
            page.value = 0
        }
    )
    when (page.value) {
        0 -> {
            SettingsButton(
                title = "Standard panel",
                description = null,
                callback = {
                    page.value = 1
                }
            )
            for (i in 2..6) {
                SettingsButton(
                    title = "Panel $i",
                    description = null,
                    callback = {
                        page.value = i
                    }
                )
            }
        }
        else -> {
            SettingsSwitch(
                title = "Active in portrait",
                description = "Whether this panel is active in portrait mode",
                checked = false,
                callback = {}
            )
            SettingsSwitch(
                title = "Active in landscape",
                description = "Whether this panel is active in landscape mode",
                checked = false,
                callback = {}
            )
            SettingsTextFieldDialog(
                title = "Name",
                description = "Set the name of this panel",
                callback = {},
            )
            SettingsSelectDialog(
                title = "Portrait location",
                description = "The panel's location in portrait mode",
                itemNames = stringArrayResource(R.array.dirLocationNames),
                itemValues = stringArrayResource(R.array.dirLocationValues),
                selected = "0",
                callback = {}
            )
            SettingsSelectDialog(
                title = "Landscape location",
                description = "The panel's location in landscape mode",
                itemNames = stringArrayResource(R.array.dirLocationNames),
                itemValues = stringArrayResource(R.array.dirLocationValues),
                selected = "0",
                callback = {}
            )
            SettingsSlider(
                title = "Opacity",
                description = "The opacity of the panel",
                range = 0..255,
                start = 255,
                callback = {}
            )
            SettingsSlider(
                title = "Size",
                description = "The relative size of the panel",
                range = -10..10,
                start = 0,
                callback = {}
            )
        }
    }
}

@Composable
fun SettingsHearsePage(
	newPageCallback: (SettingsPage) -> Unit,
	settingsViewModel: SettingsViewModel,
) {
    SettingsSwitch(
        title = "Enable hearse",
        description = "Bones sharing. Don't use Hearse with Wizard Mode bones",
        checked = false,
        callback = {},
    )
    SettingsTextFieldDialog(
        title = "Email",
        description = "How Hearse admins can contact you if needed",
        callback = {}
    )
    SettingsTextFieldDialog(
        title = "Nickname",
        description = "The name displayed on Hearse website",
        callback = {}
    )
    SettingsTextFieldDialog(
        title = "User token",
        description = "You can copy this to keep your identity across multiple devices",
        callback = {}
    )
    SettingsSwitch(
        title = "Keep my bones",
        description = "Default is to delete bones after upload",
        checked = false,
        callback = {}
    )
    SettingsButton(
        title = "Hearse is hosted by krollmark. Click here to visit the Hearse website",
        description = null,
        callback = {}
    )
}

@Composable
fun SettingsEditOptionsFilePage(
	newPageCallback: (SettingsPage) -> Unit,
	settingsViewModel: SettingsViewModel,
) {
	val textStateField = rememberTextFieldState()
	TextField(
		state = textStateField,
		textStyle = MaterialTheme.typography.bodyLarge,
	)
	Row {
		Button(
			onClick = {
				// TODO: save it
				newPageCallback(SettingsPage.main)
			}
		) {
			Text(
				"Save",
				style = MaterialTheme.typography.labelLarge
			)
		}
		Button(
			onClick = {
				newPageCallback(SettingsPage.main)
			}
		) {
			Text(
				"Discard",
				style = MaterialTheme.typography.labelLarge
			)
		}
	}
}

@Composable
fun SettingsCreditPage() {
    // TODO
}

@Composable
fun SettingsSlider(
    title: String,
    description: String,
    range: IntRange,
    start: Int,
    callback: (Int) -> Unit
) {
	Row(
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		Column(
			modifier = Modifier.weight(1f)
		){
			Text(title, style = MaterialTheme.typography.titleMedium)
			Text(description, style = MaterialTheme.typography.bodySmall)
		}
		Text(start.toString(), style = MaterialTheme.typography.headlineLarge)
	}
	Row(
		horizontalArrangement = Arrangement.spacedBy(5.dp),
	) {
		Text(range.start.toString(), style = MaterialTheme.typography.headlineMedium)
		Slider(
			modifier = Modifier.weight(1f),
			value = start.toFloat(),
			valueRange = range.first.toFloat()..range.last.toFloat(),
			onValueChange = { newValue ->
				callback(newValue.toInt())
			}
		)
		Text(range.endInclusive.toString(), style = MaterialTheme.typography.headlineMedium)
	}
}

@Composable
fun SettingsButton(title: String, description: String?, callback: () -> Unit) {
	Column (
		Modifier
			.clickable(
				onClick = {
					callback()
				}
			)
			.fillMaxWidth()
	) {
		Text(
			title,
			style = MaterialTheme.typography.titleMedium
		)
		if (description != null) {
			Text(
				description,
				style = MaterialTheme.typography.bodySmall
			)
		}
	}
}

@Composable
fun SettingsCategory(text: String) {
    Column {
        HorizontalDivider()
        Text(
			text,
			style = MaterialTheme.typography.displayMedium
		)
        HorizontalDivider()
    }
}

@Composable
fun SettingsSelectDialog(
    title: String,
    description: String,
    itemNames: Array<String>,
    itemValues: Array<String>,
    selected: String,
    callback: (String) -> Unit,
) {
    if (itemNames.size != itemValues.size) throw IllegalArgumentException("itemName and itemValues must be the same size")

    val openDialog = remember { mutableStateOf(false) }

    SettingsButton(
        title = title,
        description = description,
        callback = {
            openDialog.value = true
        }
    )
    if (openDialog.value) {
        Dialog(
            onDismissRequest = {
                openDialog.value = false
            }
        ) {
            Column(
				verticalArrangement = Arrangement.spacedBy(12.dp),
				modifier = Modifier
					.background(color = MaterialTheme.colorScheme.surface)
					.padding(16.dp)
			){
                itemNames.indices.forEach { i ->
                    val name = itemNames[i]
                    val value = itemValues[i]
                    Row (
						horizontalArrangement = Arrangement.spacedBy(12.dp),
						modifier = Modifier.clickable(
							role = Role.RadioButton,
							onClick = {
								callback(value)
							}
						)
					) {
                        RadioButton(
							selected = selected == value,
							onClick = null,
                        )
                        Text(
							name,
							style = MaterialTheme.typography.titleMedium,
							modifier = Modifier.align(Alignment.CenterVertically),
						)
                    }
                }
                Row(
					horizontalArrangement = Arrangement.spacedBy(12.dp),
				) {
                    Button(
                        onClick = {
                            openDialog.value = false
                            callback(selected)
                        }
                    ) {
                        Text(
							"Saved",
							style = MaterialTheme.typography.labelLarge,
						)
                    }
                    Button(
                        onClick = {
                            openDialog.value = false
                        }
                    ) {
                        Text(
							"Cancel",
							style = MaterialTheme.typography.labelLarge
						)
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsTextFieldDialog(title: String, description: String, callback: (String) -> Unit) {
	val textStateField = rememberTextFieldState()
    val openDialog = remember { mutableStateOf(false) }
    SettingsButton(
        title = title,
        description = description,
        callback = {
            openDialog.value = true
        }
    )
	if (openDialog.value) {
		Dialog(
			onDismissRequest = {
				openDialog.value = false
			}
		) {
			Column(
				modifier = Modifier
					.background(color = MaterialTheme.colorScheme.surface)
					.height(300.dp)
					.width(300.dp)
					.padding(16.dp)
			){
				TextField(
					state = textStateField,
					lineLimits = TextFieldLineLimits.SingleLine,
					textStyle = MaterialTheme.typography.bodyLarge,
				)
				Row {
					Button(
						modifier = Modifier.weight(1f),
						onClick = {
							openDialog.value = false
							callback(textStateField.text.toString())
						}
					) {
						Text(
							"Saved",
							style = MaterialTheme.typography.labelLarge
						)
					}
					Button(
						modifier = Modifier.weight(1f),
						onClick = {
							openDialog.value = false
						}
					) {
						Text(
							"Cancel",
							style = MaterialTheme.typography.labelLarge
						)
					}
				}
			}
        }
    }
}

@Composable
fun EnterSize(
	label: String,
	enabled: Boolean,
	start: Int,
	modifier: Modifier = Modifier,
	callback: (Int) -> Unit
) {
    val textStateField = rememberTextFieldState(start.toString())
    TextField(
		value = start.toString(),
		onValueChange = { newValue ->
			callback(newValue.toInt())
		},
		keyboardOptions = KeyboardOptions(
			keyboardType = KeyboardType.Number,
		),
		maxLines = 1,
		textStyle = MaterialTheme.typography.headlineLarge,
		modifier = modifier,
        enabled = enabled,
        label = {
            Text(
				label,
				style = MaterialTheme.typography.labelLarge
			)
        }
    )
}

@Composable
fun SettingsChangePage(title: String, description: String, page: SettingsPage, callback: (SettingsPage) -> Unit) {
    SettingsButton(title = title, description = description, callback = {
        callback(page)
    })
}


@Composable
fun SettingsSwitch(title: String, description: String, checked: Boolean, callback: (Boolean) -> Unit) {
	Row(
		horizontalArrangement = Arrangement.SpaceBetween,
		modifier = Modifier
		.clickable(
			role = Role.Switch,
			onClick = {
				callback(!checked)
			}
		)
		.fillMaxWidth()
	){
		Column(
			modifier = Modifier.weight(1f),
		){
			Text(
				title,
				style = MaterialTheme.typography.titleMedium,
			)
			Text(
				description,
				style = MaterialTheme.typography.bodySmall
			)

		}
		Switch(
			checked = checked,
			onCheckedChange = null,
        )
    }
}
