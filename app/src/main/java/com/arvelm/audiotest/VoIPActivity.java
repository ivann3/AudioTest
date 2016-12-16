package com.arvelm.audiotest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class VoIPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.voIP);
        setContentView(R.layout.activity_vo_ip);
    }
}
