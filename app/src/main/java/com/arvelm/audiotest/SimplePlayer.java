package com.arvelm.audiotest;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by lidh on 16-12-19.
 */

public class SimplePlayer {
    private MediaPlayer mMediaPlayer;
    private static SimplePlayer mSimplePlayer ;

    private SimplePlayer() {

    }

    private SimplePlayer(Context context, int AudioStreamType){
        /*
        // no need to call prepare(); create() does that for you
        //setAudioStreamType() MUST be call BEFORE prepare().
        mMediaPlayer = MediaPlayer.create(this,R.raw.testtone);
        mMediaPlayer.setScreenOnWhilePlaying(true);
        mMediaPlayer.setVolume(1.0f,1.0f);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);
        */

        AssetFileDescriptor tone = context.getResources().openRawResourceFd(R.raw.testtone);

        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setScreenOnWhilePlaying(true);
            mMediaPlayer.setVolume(1.0f, 1.0f);
            mMediaPlayer.setAudioStreamType(AudioStreamType);
            try {
                mMediaPlayer.setDataSource(tone.getFileDescriptor(),
                        tone.getStartOffset(), tone.getLength());
                mMediaPlayer.setLooping(true);
                mMediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static SimplePlayer getSimplePlayer(Context context, int AUDIO_STREAM_TYPE) {
         if (mSimplePlayer ==null){
             mSimplePlayer = new SimplePlayer(context,AUDIO_STREAM_TYPE);
         }
        return mSimplePlayer;
    }

    public void start() {
        stop();
        mMediaPlayer.start();
    }


    /*
      When you call stop(), notice that you cannot call start() again
      until you prepare the MediaPlayer again
     */
    public void stop() {
        if (mMediaPlayer.isPlaying()){
            mMediaPlayer.stop();
            try {
                mMediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void release() {
        stop();
        mMediaPlayer.release();
        mSimplePlayer = null;
    }





}
