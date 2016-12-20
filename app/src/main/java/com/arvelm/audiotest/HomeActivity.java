package com.arvelm.audiotest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }


    public void onClickHomeButton(View view) {
        switch (view.getId()) {
            case R.id.music_button:
                startSubActivity(MusicActivity.class);
                break;

            case R.id.speakerDial_button:
                startSubActivity(InCallActivity.class);
                break;

            case R.id.voip_button:
                startSubActivity(VoIPActivity.class);
                break;
            default:
                break;
        }
    }


    private void startSubActivity(Class clazz) {
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }


}
