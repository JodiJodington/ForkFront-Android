package com.tbd.forkfront;

import androidx.preference.EditTextPreference;
import android.widget.EditText;
import android.text.InputType;
import android.content.res.TypedArray;
import android.content.Context;
import android.util.AttributeSet;


public class IntEditTextPreference extends EditTextPreference {
	int mValue;
	public IntEditTextPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOnBindEditTextListener(new EditTextPreference.OnBindEditTextListener() {
			@Override
			public void onBindEditText(EditText editText) {
				editText.setInputType(InputType.TYPE_CLASS_NUMBER);
			}
		});
	}
	public String getText() {
		return String.valueOf(mValue);
	}
	public void setText(String text) {
		mValue = Integer.parseInt(text);
	}
	protected void onSetInitialValue(Object defaultValue) {
		if (defaultValue == null) {
			mValue = 0;
		} else {
			mValue = (int)defaultValue;
		}
	}
	protected Object onGetDefaultValue(TypedArray a, int index) {
		return a.getInt(index, 0);
	}
}
