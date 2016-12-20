package com.arvelm.audiotest;

import android.content.Context;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.KeyEvent.KEYCODE_VOLUME_DOWN;
import static android.view.KeyEvent.KEYCODE_VOLUME_UP;

public class InCallActivity extends AppCompatActivity {
    private AudioManager  mAudioManager;
    private SimplePlayer mPlayer;
    private static final int VOICE_CALL = AudioManager.STREAM_VOICE_CALL;

    private TextView volumeTextView;

    private String tag;
    private static final String SPEAKER = "SPEAKER ";
    private static final String RECEIVER = "RECEIVER ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.inCall);
        setContentView(R.layout.activity_incall);

        volumeTextView = (TextView) findViewById(R.id.volume_textView);

        setVolumeControlStream(VOICE_CALL);

        initAudioManager();

        mPlayer = SimplePlayer.getSimplePlayer(this,VOICE_CALL);
    }


    private void initAudioManager() {
        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.setMode(AudioManager.MODE_IN_CALL);
    }


    public void onPlay(View view) {
        switch (view.getId()){
            case R.id.speaker_button:
                mAudioManager.setSpeakerphoneOn(true);
                mAudioManager.setStreamVolume(VOICE_CALL,
                        mAudioManager.getStreamMaxVolume(VOICE_CALL),0);

                tag = SPEAKER;
                showVoiceCallVolume();

                mPlayer.start();
                break;

            case R.id.receiver_button:
                mAudioManager.setSpeakerphoneOn(false);
                mAudioManager.setStreamVolume(VOICE_CALL,
                        mAudioManager.getStreamMaxVolume(VOICE_CALL),0);

                tag = RECEIVER;
                showVoiceCallVolume();

                mPlayer.start();
                break;
            default:
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
            //Adjust the STREAM_VOICE_CALL volume,and show in volumeTextView
        if (tag == null || tag.isEmpty()){
            tag = "Volume ";
        }
        if (mAudioManager != null) {
            if (keyCode == KEYCODE_VOLUME_DOWN) {
                mAudioManager.adjustStreamVolume(VOICE_CALL,
                        AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
                showVoiceCallVolume();
                return true;
            }
            if(keyCode ==KEYCODE_VOLUME_UP){
                mAudioManager.adjustStreamVolume(VOICE_CALL,
                        AudioManager.ADJUST_RAISE,AudioManager.FLAG_SHOW_UI);
                showVoiceCallVolume();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    private void showVoiceCallVolume(){
        int volume = mAudioManager.getStreamVolume(VOICE_CALL);
        String text = tag + String.valueOf(volume);
        volumeTextView.setText(text);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        mPlayer.release();
        /*
         Normal audio mode: not ringing and no call established.
        */
        mAudioManager.setMode(AudioManager.MODE_NORMAL);
    }
}
