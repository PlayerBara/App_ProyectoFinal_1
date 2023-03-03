package com.example.freeforplay.Fragments;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;

import com.example.freeforplay.R;

public class SettingsFragments extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferencias, rootKey);
    }
}