package com.arvelm.audiotest;

import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import static android.view.KeyEvent.KEYCODE_VOLUME_DOWN;
import static android.view.KeyEvent.KEYCODE_VOLUME_UP;

public class MusicActivity extends AppCompatActivity {
    private SimplePlayer mPlayer;
    private AudioManager mAudioManager;
    private static final int  MUSIC = AudioManager.STREAM_MUSIC;
    private TextView volumeTextView;
    private static final String SPEAKER = "SPEAKER ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.music);
        setContentView(R.layout.activity_music);

        volumeTextView = (TextView) findViewById(R.id.volume_textView);

        setVolumeControlStream(MUSIC);

        initAudioManager();

        mPlayer = SimplePlayer.getSimplePlayer(this,MUSIC);

    }

    private void initAudioManager() {
        mAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
        mAudioManager.setMode(AudioManager.MODE_CURRENT);
    }

    public void onPlay(View view) {
        if (view.getId() == R.id.speaker_music){

            mAudioManager.setStreamVolume(MUSIC, mAudioManager.getStreamMaxVolume(MUSIC),0);
            mAudioManager.setSpeakerphoneOn(true);

            showVoiceCallVolume();

            mPlayer.start();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Adjust the STREAM_VOICE_CALL volume,and show in volumeTextView;
        if (mAudioManager != null) {
            if (keyCode == KEYCODE_VOLUME_DOWN) {
                mAudioManager.adjustStreamVolume(MUSIC,
                        AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
                showVoiceCallVolume();
                return true;
            }
            if(keyCode ==KEYCODE_VOLUME_UP){
                mAudioManager.adjustStreamVolume(MUSIC,
                        AudioManager.ADJUST_RAISE,AudioManager.FLAG_SHOW_UI);
                showVoiceCallVolume();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    private void showVoiceCallVolume(){
        int volume = mAudioManager.getStreamVolume(MUSIC);
        String text = SPEAKER + String.valueOf(volume);
        volumeTextView.setText(text);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAudioManager.setMode(AudioManager.MODE_NORMAL);
        mPlayer.release();
    }


}
