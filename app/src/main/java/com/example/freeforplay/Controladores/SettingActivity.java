package com.example.freeforplay.Controladores;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.freeforplay.Fragments.SettingsFragments;
import com.example.freeforplay.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_constraint);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.setting_container, new SettingsFragments())
                .commit();
    }
}
