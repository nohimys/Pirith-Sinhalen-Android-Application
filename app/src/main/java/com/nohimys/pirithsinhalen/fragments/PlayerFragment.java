package com.nohimys.pirithsinhalen.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.nohimys.pirithsinhalen.R;
import com.nohimys.pirithsinhalen.models.SimpleTime;
import com.nohimys.pirithsinhalen.utils.StreamingPlayer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerFragment extends Fragment implements StreamingPlayer.OnStreamCallbackListener, View.OnClickListener {

    private String trackName;
    private String trackLink;

    @BindView(R.id.textview_player_heading)
    TextView textviewPlayerHeading;

    @BindView(R.id.textview_description)
    TextView textviewDescription;

    @BindView(R.id.image_button_play_pause_player)
    ImageButton imageButtonPlayPausePlayer;

    @BindView(R.id.image_button_backward)
    ImageButton imageButtonBackward;

    @BindView(R.id.image_button_forward)
    ImageButton imageButtonForward;

    //private AudioWaveView audioWaveViewDownloadBuffer;
    // private AudioWaveView audioWaveViewTrackProgress;

    @BindView(R.id.seekBarMain)
    SeekBar seekBarAudioProgress;

    private Handler handler;

    public PlayerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View resultView =inflater.inflate(R.layout.fragment_player, container, false);

        ButterKnife.bind(this, resultView);

        this.handler = new Handler();

        textviewPlayerHeading.setText(trackName);
        textviewDescription.setText(trackLink);

        imageButtonPlayPausePlayer.setOnClickListener(this);
        imageButtonBackward.setOnClickListener(this);
        imageButtonForward.setOnClickListener(this);

        imageButtonPlayPausePlayer.setAlpha(0.5f);
        imageButtonPlayPausePlayer.setEnabled(false);
        imageButtonBackward.setAlpha(0.5f);
        imageButtonBackward.setEnabled(false);
        imageButtonForward.setAlpha(0.5f);
        imageButtonBackward.setEnabled(false);

        seekBarAudioProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar,final int progress, boolean fromUser) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Get progress of buffer bar and only this is less than that, set the value
                        if(seekBarAudioProgress.getProgress() > progress) {
                            textviewDescription.setText("Seeked Position: " + String.valueOf(progress));
                            seekBarAudioProgress.setProgress(progress);
                        }
                    }
                });
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarAudioProgress.setSecondaryProgress(0);
        seekBarAudioProgress.setProgress(0);

        //imageButtonPlayPausePlayer.setVisibility(View.INVISIBLE);

//        audioWaveViewDownloadBuffer = (AudioWaveView)resultView.findViewById(R.id.audio_wave_view_buffer);
//        audioWaveViewDownloadBuffer.setProgress(40.0f);
//
//        audioWaveViewTrackProgress = (AudioWaveView)resultView.findViewById(R.id.audio_wave_view_track_progress);
//        audioWaveViewTrackProgress.setProgress(0.0f);
//        audioWaveViewTrackProgress.setOnProgressListener(new OnProgressListener() {
//            @Override
//            public void onStartTracking(float v) {
//
//            }
//
//            @Override
//            public void onStopTracking(final float v) {
////                handler.post(new Runnable() {
////                    @Override
////                    public void run() {
////                        textviewDescription.setText("Seeked Position: " + String.valueOf(v));
////                        audioWaveViewTrackProgress.setProgress(v);
////                    }
////                });
//            }
//
//            @Override
//            public void onProgressChanged(final float v, boolean b) {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        //Get progress of buffer bar and only this is less than that, set the value
//                        if(audioWaveViewTrackProgress.getProgress() > v) {
//                            textviewDescription.setText("Seeked Position: " + String.valueOf(v));
//                            audioWaveViewTrackProgress.setProgress(v);
//                        }
//                    }
//                });
//
//            }
//        });
//        audioWaveViewTrackProgress.setProgress(0.0f);

        //To incease the hight of the seeking and buffering bar
        byte[] scaledData = new byte[2000];
        for (int i=0; i< 499; i++){
            scaledData[i] = 20;
        }
        for (int i=500; i< 999; i++){
            scaledData[i] = 15;
        }
        for (int i=1000; i< 1499; i++){
            scaledData[i] = 20;
        }
        for (int i=1500; i< 1999; i++){
            scaledData[i] = 15;
        }

//        audioWaveViewTrackProgress.setScaledData(scaledData);
//        audioWaveViewDownloadBuffer.setScaledData(scaledData);


        //progressBarAudioBuffer = (ProgressBar)resultView.findViewById(R.id.progress_bar_audio_buffer);
        //progressBarAudioBuffer.setProgress(0);

        //Glide.with(resultView.getContext().getApplicationContext()).load(R.mipmap.ic_pansil_maluwa_cover_image).fitCenter().into((ImageView)resultView.findViewById(R.id.image_view_player_cover_photo));

        StreamingPlayer.getInstance().bufferTrack(trackLink, this,this.handler);
        textviewDescription.setText(trackLink + "\n" + StreamingPlayer.getInstance().getDuration().getTimeAsString());

        return resultView;
    }

    /**
     * Called when a fragment is first attached to its context.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Bundle args = getArguments();
        if(args != null){
            trackName = args.getString("trackName");
            trackLink = args.getString("trackLink");
        }
    }

    @Override
    public void onBuffering(final int percentage) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                seekBarAudioProgress.setSecondaryProgress(percentage);
                //progressBarAudioBuffer.setProgress(percentage);
                //audioWaveViewDownloadBuffer.setProgress(percentage);
            }
        });
    }

    @Override
    public void onTrackProgress(final int percentage, final SimpleTime simpleTime) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                textviewDescription.setText(percentage + "==" + simpleTime.getTimeAsString());
                seekBarAudioProgress.setProgress(percentage);
            }
        });
    }

    @Override
    public void onReadyToPlay() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                //imageButtonPlayPausePlayer.setVisibility(View.VISIBLE);
                imageButtonPlayPausePlayer.setAlpha(1.0f);
                imageButtonPlayPausePlayer.setEnabled(true);
                imageButtonBackward.setAlpha(1.0f);
                imageButtonBackward.setEnabled(true);
                imageButtonForward.setAlpha(1.0f);
                imageButtonBackward.setEnabled(true);
            }
        });
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.image_button_play_pause_player:
                if(StreamingPlayer.getInstance().isPlaying()){
                    imageButtonPlayPausePlayer.setImageResource(R.drawable.ic_player_button_play);
                }
                else {
                    imageButtonPlayPausePlayer.setImageResource(R.drawable.ic_player_button_pause);
                }
                StreamingPlayer.getInstance().playOrPauseTrack();
                break;
            case R.id.image_button_backward:
                StreamingPlayer.getInstance().rewindBackTrack();
                break;
            case R.id.image_button_forward:
                StreamingPlayer.getInstance().fastForwardTrack();
                break;
        }
    }
}
