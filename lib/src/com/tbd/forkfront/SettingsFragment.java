package com.tbd.forkfront;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build;
import android.os.Bundle;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceFragmentCompat;
import android.view.Window;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;
import android.view.Menu;
import android.view.View;
import android.view.LayoutInflater;
import androidx.core.view.ViewCompat;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import com.tbd.forkfront.TilesetPreference;

public class SettingsFragment extends PreferenceFragmentCompat
{
	@Override
	public void onCreatePreferences(Bundle savedInstanceState, String rootKey)
	{
		setPreferencesFromResource(R.xml.preferences, rootKey);
	}
}
