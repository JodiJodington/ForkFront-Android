package com.tbd.forkfront;
import android.text.InputType;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.ListPreference;
import androidx.preference.EditTextPreference;
import android.widget.LinearLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.view.inputmethod.EditorInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.List;
import java.util.Arrays;


public class TilesetPreferenceFragment extends PreferenceFragmentCompat {
	private static final int GET_IMAGE_REQUEST = 342;
    private static final String TTY = "TTY";
    private CharSequence[] mEntries;
    private CharSequence[] mEntryValues;
	private TextView mTilesetPath;
	private EditText mTileW;
	private EditText mTileH;
	private ViewGroup mTilesetUI;
	private LinearLayout mRoot;
	private String mCustomTilesetPath;
	private Bitmap mCustomTileset;
	private ImageButton mBrowse;
	private boolean mTileWFocus;
	private boolean mTileHFocus;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		List<String> entryList = Arrays.asList(getContext().getResources().getStringArray(R.array.tileNames));
		List<String> entryValueList = Arrays.asList(getContext().getResources().getStringArray(R.array.tileValues));

		mEntries = new CharSequence[entryList.size() + 1];
		mEntryValues = new CharSequence[entryValueList.size() + 1];
		for (int i = 0; i < entryList.size(); i++) {
			mEntries[i] = entryList.get(i);
			mEntryValues[i] = entryValueList.get(i);
		}
		mEntries[entryList.size()] = mEntryValues[entryValueList.size()] = "custom";
	}
	@Override
	public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
		setPreferencesFromResource(R.xml.tileset_preferences, rootKey);
		EditTextPreference tileWPreference = findPreference("tileW");
		EditTextPreference tileHPreference = findPreference("tileH");
		ListPreference tilesetList = findPreference("tileset");

		EditTextPreference.OnBindEditTextListener callback = new EditTextPreference.OnBindEditTextListener() {
			@Override
			public void onBindEditText(EditText editText) {
				editText.setInputType(InputType.TYPE_CLASS_NUMBER);
			}
		};

		tileWPreference.setOnBindEditTextListener(callback);
		tileHPreference.setOnBindEditTextListener(callback);

		tilesetList.setEntries(mEntries);
		tilesetList.setEntryValues(mEntryValues);
		tilesetList.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				boolean shouldEnable = (String)newValue == "custom";
				tileWPreference.setEnabled(shouldEnable);
				tileHPreference.setEnabled(shouldEnable);
				return true;
			}
		});
	}
}
