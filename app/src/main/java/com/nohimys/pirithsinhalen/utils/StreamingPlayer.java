package com.nohimys.pirithsinhalen.utils;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

import com.nohimys.pirithsinhalen.models.SimpleTime;

import java.io.IOException;

/**
 * Created by Nohim Sandeepa on 10/8/2017.
 */

public class StreamingPlayer {
    private static StreamingPlayer myInstance;
    private final String LOG_TAG = "StreamingPlayerClass";

    private MediaPlayer mediaPlayer;
    private OnStreamCallbackListener onStreamCallbackListener;

    private final int BUFFER_PLAY_DIFFERENCE_THRESHHOLD = 3;

    private int bufferedPercentage;

    private StreamingPlayer(){

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.e(LOG_TAG, "MediaPlayer on Prepared Called");
                //mp.start();
                if (onStreamCallbackListener != null) {
                    onStreamCallbackListener.onReadyToPlay();
                }
            }
        });

        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {

            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                if (onStreamCallbackListener != null) {
                    bufferedPercentage = percent;
                    onStreamCallbackListener.onBuffering(percent);
                }
                Log.i("Buffering", "" + percent);
            }
        });

        mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {
                if (onStreamCallbackListener != null) {
                    //onStreamCallbackListener.onTrackProgress(mp.getCurrentPosition());
                }
            }
        });
    }

    public static StreamingPlayer getInstance(){
        if(myInstance == null){
            myInstance = new StreamingPlayer();
        }
        return myInstance;
    }

    public void bufferTrack(final String link, OnStreamCallbackListener onStreamCallbackListener, Handler handler)
    {
        this.onStreamCallbackListener = onStreamCallbackListener;
        this.bufferedPercentage= 0;

        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(link);
                    mediaPlayer.prepareAsync();
                }
                catch (IOException ex){
                    Log.e(LOG_TAG,"Error adding the Url To MediaPlayer");
                    ex.printStackTrace();
                }
                catch (Exception ex){
                    Log.e(LOG_TAG,"Error Loading the Url in MediaPlayer");
                    ex.printStackTrace();
                }
            }
        });


    }

    public boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }

    public boolean isBuffered(int percentageValue){
        if(percentageValue > this.bufferedPercentage + this.BUFFER_PLAY_DIFFERENCE_THRESHHOLD){
            return true;
        }
        else{
            return false;
        }
    }

    public int getCurrentBufferedPercentage(){
        return this.bufferedPercentage - this.BUFFER_PLAY_DIFFERENCE_THRESHHOLD;
    }

    public void playOrPauseTrack(){
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
        else {
            mediaPlayer.pause();
        }
    }

    public void rewindBackTrack(){

    }

    public void fastForwardTrack(){

    }

    public SimpleTime getDuration(){
        return new SimpleTime(mediaPlayer.getDuration());
    }

    public interface OnStreamCallbackListener{
        void onBuffering(int percentage);
        void onTrackProgress(int percentage, SimpleTime simpleTime);
        void onReadyToPlay();
    }
}
