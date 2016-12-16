package com.arvelm.audiotest;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

import static android.view.KeyEvent.KEYCODE_VOLUME_DOWN;
import static android.view.KeyEvent.KEYCODE_VOLUME_UP;

public class InCallActivity extends AppCompatActivity {
    private AudioManager  mAudioManager;
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.inCall);
        setContentView(R.layout.activity_incall);

        setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);

        initAudioManager();

        initPlayer();
    }


    @Override
    protected void onResume() {
        super.onResume();
        /*
          set maximum volume in STREAM_VOICE_CALL
         */
        if (mAudioManager != null){
            int maxVoiceCallVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
            mAudioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL,maxVoiceCallVolume,0);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
            //Adjust the STREAM_VOICE_CALL volume,and show Toast
        if (mAudioManager != null) {
            if (keyCode == KEYCODE_VOLUME_DOWN) {
                mAudioManager.adjustStreamVolume(AudioManager.STREAM_VOICE_CALL,
                        AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
                showVoiceCallVolume();
                return true;
            }
            if(keyCode ==KEYCODE_VOLUME_UP){
                mAudioManager.adjustStreamVolume(AudioManager.STREAM_VOICE_CALL,
                        AudioManager.ADJUST_RAISE,AudioManager.FLAG_SHOW_UI);
                showVoiceCallVolume();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    private void showVoiceCallVolume(){
        int volume = mAudioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
        Toast.makeText(this, String.valueOf(volume), Toast.LENGTH_SHORT).show();
    }


    private void initAudioManager() {
        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.setMode(AudioManager.MODE_IN_CALL);

    }


    private void initPlayer() {
//        mMediaPlayer = MediaPlayer.create(this,R.raw.testtone);
//        mMediaPlayer.setScreenOnWhilePlaying(true);
//        mMediaPlayer.setVolume(1.0f,1.0f);
//        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);

        AssetFileDescriptor tone = this.getResources().openRawResourceFd(R.raw.testtone);
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setScreenOnWhilePlaying(true);
        mMediaPlayer.setVolume(1.0f,1.0f);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);
        try {
            mMediaPlayer.setDataSource(tone.getFileDescriptor(),
                    tone.getStartOffset(),tone.getLength());
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onPlay(View view) {
        switch (view.getId()){
            case R.id.speaker_button:
                mAudioManager.setSpeakerphoneOn(true);
                startPlayer();
                break;
            case R.id.receiver_button:
                mAudioManager.setSpeakerphoneOn(false);
                startPlayer();
                break;
            default:
                break;
        }
    }


    private void  startPlayer() {
        stopPlay();
        mMediaPlayer.start();
    }

    private void stopPlay(){
        if (mMediaPlayer.isPlaying()){
            mMediaPlayer.stop();
        }
    }

    private void releasePlayer() {
        mMediaPlayer.release();
    }

    private void resetAudioManager() {
        mAudioManager.setMode(AudioManager.MODE_NORMAL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAudioManager = null;
        stopPlay();
        releasePlayer();
        resetAudioManager();
    }
}
