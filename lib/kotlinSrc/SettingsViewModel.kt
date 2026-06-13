package com.tbd.forkfront
import android.content.Context
import android.preference.PreferenceManager
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow

class SettingsPreferences(context: Context) {
	val mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

	val tileset = MutableStateFlow<String>(mSharedPreferences.getString("tileset", "TTY")!!)
	val tileW = MutableStateFlow<Int>(mSharedPreferences.getInt("tileW", -1))
	val tileH = MutableStateFlow<Int>(mSharedPreferences.getInt("tileH", -1))
	val customTileW = MutableStateFlow<Int>(mSharedPreferences.getInt("customTileW", 32))
	val customTileH = MutableStateFlow<Int>(mSharedPreferences.getInt("customTileH", 32))

	val	fullscreen = MutableStateFlow<Boolean>(mSharedPreferences.getBoolean("fullscreen", false))
	val	immersiveMode = MutableStateFlow<Boolean>(mSharedPreferences.getBoolean("immersive", false))
	val volumeDownAction = MutableStateFlow<String>(mSharedPreferences.getString("voldown", "0")!!)
	val volumeUpAction = MutableStateFlow<String>(mSharedPreferences.getString("volup", "0")!!)
	val lockView = MutableStateFlow<Boolean>(mSharedPreferences.getBoolean("lockView", false))
	val monospaceMode = MutableStateFlow<Boolean>(mSharedPreferences.getBoolean("monospace", false))
	val statusBackgroundOpacity = MutableStateFlow<Int>(mSharedPreferences.getInt("statusOpacity", 0))
	val mapBorderOpacity = MutableStateFlow<Int>(mSharedPreferences.getInt("borderOpacity", 0))

	val alwaysShowOverlayInPortrait = MutableStateFlow<Boolean>(mSharedPreferences.getBoolean("ovlPortAlways", false))
	val alwaysShowOverlayInLandscape = MutableStateFlow<Boolean>(mSharedPreferences.getBoolean("ovlLandAlways", false))
	val overlayPortraitLocation = MutableStateFlow<String>(mSharedPreferences.getString("ovlPortLoc", "1")!!)
	val overlayLandscapeLocation = MutableStateFlow<String>(mSharedPreferences.getString("ovlLandLoc", "1")!!)
	val overlayOpacity = MutableStateFlow<Int>(mSharedPreferences.getInt("ovlOpacity", 255))
	val overlaySize = MutableStateFlow<Int>(mSharedPreferences.getInt("ovlSize", 0))
	val allowMapInputWithOverlay = MutableStateFlow<Boolean>(mSharedPreferences.getBoolean("allowMapDir", false))

	var commandPanelActiveInPortraits = arrayOf(
		MutableStateFlow<Boolean>(mSharedPreferences.getBoolean("pPortActive0", true)),
		MutableStateFlow<Boolean>(mSharedPreferences.getBoolean("pPortActive1", false)),
		MutableStateFlow<Boolean>(mSharedPreferences.getBoolean("pPortActive2", false)),
		MutableStateFlow<Boolean>(mSharedPreferences.getBoolean("pPortActive3", false)),
		MutableStateFlow<Boolean>(mSharedPreferences.getBoolean("pPortActive4", false)),
		MutableStateFlow<Boolean>(mSharedPreferences.getBoolean("pPortActive6", false))
	)
	var commandPanelActiveInLandscapes = arrayOf(
		MutableStateFlow<Boolean>(mSharedPreferences.getBoolean("pLandActive0", true)),
		MutableStateFlow<Boolean>(mSharedPreferences.getBoolean("pLandActive1", false)),
		MutableStateFlow<Boolean>(mSharedPreferences.getBoolean("pLandActive2", false)),
		MutableStateFlow<Boolean>(mSharedPreferences.getBoolean("pLandActive3", false)),
		MutableStateFlow<Boolean>(mSharedPreferences.getBoolean("pLandActive4", false)),
		MutableStateFlow<Boolean>(mSharedPreferences.getBoolean("pLandActive6", false))
	)
	var commandPanelNames = arrayOf(
		MutableStateFlow<String>(mSharedPreferences.getString("pName0", "Standard Panel")!!),
		MutableStateFlow<String>(mSharedPreferences.getString("pName1", "Panel 2")!!),
		MutableStateFlow<String>(mSharedPreferences.getString("pName2", "Panel 3")!!),
		MutableStateFlow<String>(mSharedPreferences.getString("pName3", "Panel 4")!!),
		MutableStateFlow<String>(mSharedPreferences.getString("pName4", "Panel 5")!!),
		MutableStateFlow<String>(mSharedPreferences.getString("pName5", "Panel 6")!!),
	)
	var commandPanelPortraitLocations = arrayOf(
		MutableStateFlow<String>(mSharedPreferences.getString("pPortLoc0", "3")!!),
		MutableStateFlow<String>(mSharedPreferences.getString("pPortLoc1", "3")!!),
		MutableStateFlow<String>(mSharedPreferences.getString("pPortLoc2", "3")!!),
		MutableStateFlow<String>(mSharedPreferences.getString("pPortLoc3", "3")!!),
		MutableStateFlow<String>(mSharedPreferences.getString("pPortLoc4", "3")!!),
		MutableStateFlow<String>(mSharedPreferences.getString("pPortLoc5", "3")!!)
	)
	var commandPanelLandscapeLocations = arrayOf(
		MutableStateFlow<String>(mSharedPreferences.getString("pLandLoc0", "3")!!),
		MutableStateFlow<String>(mSharedPreferences.getString("pLandLoc1", "3")!!),
		MutableStateFlow<String>(mSharedPreferences.getString("pLandLoc2", "3")!!),
		MutableStateFlow<String>(mSharedPreferences.getString("pLandLoc3", "3")!!),
		MutableStateFlow<String>(mSharedPreferences.getString("pLandLoc4", "3")!!),
		MutableStateFlow<String>(mSharedPreferences.getString("pLandLoc5", "3")!!)
	)
	var commandPanelOpacities = arrayOf(
		MutableStateFlow<Int>(mSharedPreferences.getInt("pOpacity0", 255)),
		MutableStateFlow<Int>(mSharedPreferences.getInt("pOpacity1", 255)),
		MutableStateFlow<Int>(mSharedPreferences.getInt("pOpacity2", 255)),
		MutableStateFlow<Int>(mSharedPreferences.getInt("pOpacity3", 255)),
		MutableStateFlow<Int>(mSharedPreferences.getInt("pOpacity4", 255)),
		MutableStateFlow<Int>(mSharedPreferences.getInt("pOpacity5", 255))
	)
	
	var commandPanelSizes = arrayOf(
		MutableStateFlow<Int>(mSharedPreferences.getInt("pSize0", 255)),
		MutableStateFlow<Int>(mSharedPreferences.getInt("pSize1", 255)),
		MutableStateFlow<Int>(mSharedPreferences.getInt("pSize2", 255)),
		MutableStateFlow<Int>(mSharedPreferences.getInt("pSize3", 255)),
		MutableStateFlow<Int>(mSharedPreferences.getInt("pSize4", 255)),
		MutableStateFlow<Int>(mSharedPreferences.getInt("pSize5", 255))
	)

	val hearseEnabled = MutableStateFlow<Boolean>(mSharedPreferences.getBoolean("hearseEnable", false))
	val hearseEmail = MutableStateFlow<String>(mSharedPreferences.getString("hearseMail", "")!!)
	val hearseName = MutableStateFlow<String>(mSharedPreferences.getString("hearseName", "")!!)
	val hearseToken = MutableStateFlow<String>(mSharedPreferences.getString("hearseID", "")!!)
	val hearseKeepBones = MutableStateFlow<Boolean>(mSharedPreferences.getBoolean("hearseKeepUploaded", false))

	val fallbackRenderer = MutableStateFlow<Boolean>(mSharedPreferences.getBoolean("fallbackRenderer", false))

	public fun updateTileset(newValue: String) {
		val edit = mSharedPreferences.edit()
		tileset.value = newValue
		edit.putString("tileset", newValue).apply()
		if (newValue == "custom") {
			edit.putInt("tileW", mSharedPreferences.getInt("customTileW", -1)).apply()
			edit.putInt("tileH", mSharedPreferences.getInt("customTileH", -1)).apply()
		} else if (newValue != "TTY") {
			val (tileW, tileH) = parseTileSize(newValue)
			edit.putInt("tileW", tileW).apply()
			edit.putInt("tileH", tileH).apply()
		}
	}
	public fun updateCustomTileW(newValue: Int) {
		customTileW.value = newValue
		if (tileset.value == "custom") {
			tileW.value = newValue
			tileH.value = newValue
		}
	}
	public fun updateCustomTileH(newValue: Int) {
		customTileH.value = newValue
		if (tileset.value == "custom") {
			tileH.value = newValue
		}
	}
	public fun updateFullscreen(newValue: Boolean) {
		mSharedPreferences.edit().putBoolean("fullscreen", newValue).apply()
	}
	public fun updateImmersiveMode(newValue: Boolean) {
		mSharedPreferences.edit().putBoolean("immersive", newValue).apply()
		immersiveMode.value = newValue
	}
	public fun updateVolumeDownAction(newValue: String) {
		mSharedPreferences.edit().putString("voldown", newValue).apply()
		volumeDownAction.value = newValue
	}
	public fun updateVolumeUpAction(newValue: String) {
		mSharedPreferences.edit().putString("volup", newValue).apply()
		volumeUpAction.value = newValue
	}
	public fun updateLockView(newValue: Boolean) {
		mSharedPreferences.edit().putBoolean("lockView", newValue).apply()
		lockView.value = newValue
	}
	public fun updateMonospaceMode(newValue: Boolean) {
		mSharedPreferences.edit().putBoolean("monospace", newValue).apply()
		monospaceMode.value = newValue
	}
	public fun updateStatusBackgroundOpacity(newValue: Int) {
		mSharedPreferences.edit().putInt("statusOpacity", newValue).apply()
		statusBackgroundOpacity.value = newValue
	}
	public fun updateFallbackRenderer(newValue: Boolean) {
		mSharedPreferences.edit().putBoolean("fallbackRenderer", newValue).apply()
		fallbackRenderer.value = newValue
	}
}

public class SettingsViewModel(context: Context) : ViewModel() {
	companion object {
		val CONTEXT_KEY	 = CreationExtras.Key<Context>()
		val Factory: ViewModelProvider.Factory = viewModelFactory {
			initializer {
				SettingsViewModel(this[CONTEXT_KEY] as Context)
			}
		}
	}
	private val settingsPreferences = SettingsPreferences(context)
	
	val tileset = settingsPreferences.tileset
	val customTileW = settingsPreferences.customTileW
	val customTileH = settingsPreferences.customTileH

	val fullscreen = settingsPreferences.fullscreen
	val immersiveMode = settingsPreferences.immersiveMode
	val volumeDownAction = settingsPreferences.volumeDownAction
	val volumeUpAction = settingsPreferences.volumeUpAction
	val lockView = settingsPreferences.lockView
	val monospaceMode = settingsPreferences.monospaceMode
	val statusBackgroundOpacity = settingsPreferences.statusBackgroundOpacity
	val mapBorderOpacity = settingsPreferences.mapBorderOpacity

	val alwaysShowOverlayInPortrait = settingsPreferences.alwaysShowOverlayInPortrait
	val alwaysShowOverlayInLandscape = settingsPreferences.alwaysShowOverlayInLandscape
	val overlayPortraitLocation = settingsPreferences.overlayPortraitLocation
	val overlayLandscapeLocation = settingsPreferences.overlayPortraitLocation
	val overlayOpacity = settingsPreferences.overlayOpacity
	val overlaySize = settingsPreferences.overlaySize
	val allowMapInputWithOverlay = settingsPreferences.allowMapInputWithOverlay

	val commandPanelActiveInPortraits = settingsPreferences.commandPanelActiveInPortraits
	val commandPanelActiveInLandscapes = settingsPreferences.commandPanelActiveInLandscapes
	val commandPanelNames = settingsPreferences.commandPanelNames
	val commandPanelPortraitLocations = settingsPreferences.commandPanelPortraitLocations
	val commandPanelLandscapeLocations = settingsPreferences.commandPanelLandscapeLocations
	val commandPanelOpacities = settingsPreferences.commandPanelOpacities
	val commandPanelSizes = settingsPreferences.commandPanelSizes

	val hearseEnabled = settingsPreferences.hearseEnabled
	val hearseEmail = settingsPreferences.hearseEmail
	val hearseName = settingsPreferences.hearseName
	val hearseToken = settingsPreferences.hearseToken
	val hearseKeepBones = settingsPreferences.hearseKeepBones

	val fallbackRenderer = settingsPreferences.fallbackRenderer

	public fun updateTileset(newValue: String) {
		settingsPreferences.updateTileset(newValue)
	}
	public fun updateCustomTileW(newValue: Int) {
		settingsPreferences.updateCustomTileW(newValue)
	}
	public fun updateCustomTileH(newValue: Int) {
		settingsPreferences.updateCustomTileW(newValue)
	}
	public fun updateFullscreen(newValue: Boolean) {
		settingsPreferences.updateFullscreen(newValue)
	}
	public fun updateImmersiveMode(newValue: Boolean) {
		settingsPreferences.updateImmersiveMode(newValue)
	}
	public fun updateVolumeDownAction(newValue: String) {
		settingsPreferences.updateVolumeDownAction(newValue)
	}
	public fun updateVolumeUpAction(newValue: String) {
		settingsPreferences.updateVolumeUpAction(newValue)
	}
	public fun updateLockView(newValue: Boolean) {
		settingsPreferences.updateLockView(newValue)
	}
	public fun updateMonospaceMode(newValue: Boolean) {
		settingsPreferences.updateMonospaceMode(newValue)
	}
	public fun updateStatusBackgroundOpacity(newValue: Int) {
		settingsPreferences.updateStatusBackgroundOpacity(newValue)
	}
	public fun updateFallbackRenderer(newValue: Boolean) {
		settingsPreferences.updateFallbackRenderer(newValue)
	}
}

fun parseTileSize(name: String): Pair<Int, Int> {
	val underscore = name.indexOf('_')
	if (underscore < 0) throw IllegalArgumentException("invalid tileset name: $name")
	val x = name.indexOf('x', startIndex = underscore)
	if (x > 0) {
		val widthStr = name.slice(underscore + 1 until x)
		val heightStr = name.slice(x + 1 until name.length)
		return Pair(widthStr.toInt(), heightStr.toInt())
	} else {
		val sizeStr = name.slice(underscore + 1 until name.length)
		return Pair(sizeStr.toInt(), sizeStr.toInt())
	}
}
