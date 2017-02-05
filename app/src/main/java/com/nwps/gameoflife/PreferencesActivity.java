package com.nwps.gameoflife;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class PreferencesActivity extends PreferenceFragment{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
