package com.arvelm.audiotest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }

    public void onClickHomeButton(View view) {
        switch (view.getId()) {
            case R.id.speaker_button:
                Toast.makeText(this,"Music",Toast.LENGTH_SHORT).show();
                break;
            case R.id.fmRadio_button:
                Toast.makeText(this,"FmRadio",Toast.LENGTH_SHORT).show();
                break;
            case R.id.speakerDial_button:
                Toast.makeText(this,"SpeakerDial",Toast.LENGTH_SHORT).show();
                break;
            case R.id.receiverDial_button:
                Toast.makeText(this,"ReceiverDial",Toast.LENGTH_SHORT).show();
                break;
            case R.id.voip_button:
                Toast.makeText(this,"VoIP",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }


}
