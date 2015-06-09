package com.wab.lernapp;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;


public class AudioFragment extends Fragment implements MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener{

    private MediaPlayer mediaPlayer;
    private final String TAG = "AudioFragment";

    int play_mode;
    private SeekBar songProgressBar;
    private Handler mHandler = new Handler();
    private Utilities utils;
    private ImageView audioButton;
    private TextView songTotalDurationLabel;
    private TextView songCurrentDurationLabel;

    public static AudioFragment newInstance(File srcPath) {
        AudioFragment fragment = new AudioFragment();
        Bundle args = new Bundle();
        args.putString("srcPath", srcPath.getAbsolutePath());
        fragment.setArguments(args);
        return fragment;
    }

    public AudioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_audio, container, false);

        try
        {
            //A NullPointerException is caught. So...
            //noinspection ConstantConditions
            getActivity().getActionBar().setTitle("Audio Player");
        }
        catch(NullPointerException e)
        {
            Log.e(TAG, "Cannot set title");
        }

        mediaPlayer = new MediaPlayer();
        utils = new Utilities();
        audioButton = (ImageView) rootView.findViewById(R.id.audioButton);
        songTotalDurationLabel = (TextView) rootView.findViewById(R.id.songTotalDurationLabel);
        songCurrentDurationLabel = (TextView) rootView.findViewById(R.id.songCurrentDurationLabel);
        songProgressBar = (SeekBar) rootView.findViewById(R.id.audioBar);
        TextView songTitle = (TextView) rootView.findViewById(R.id.audioTitle);

        String srcPath = getArguments().getString("srcPath");
        songTitle.setText(srcPath.substring(srcPath.lastIndexOf('/') + 1, srcPath.lastIndexOf('.')));

        songProgressBar.setOnSeekBarChangeListener(this); // Important
        mediaPlayer.setOnCompletionListener(this); // Important
        audioButton.setOnClickListener(new AudioButtonClickListener());

        startAudio();
        //audioButton.setImageResource(R.drawable.ic_audio_pause);

        audioButton.setImageResource(android.R.drawable.ic_media_pause);
        updateProgressBar();

        return rootView;
    }

    /**
     * Starts video
     */
    private void startAudio()
    {
        try
        {
            mediaPlayer.setDataSource(getArguments().getString("srcPath"));
            mediaPlayer.prepare();
            mediaPlayer.start();
            play_mode = 1;
        }
        catch (IllegalArgumentException | IOException e)
        {
            Log.e(TAG, "Probleme beim Oeffnen des Audio Files");
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        play_mode = 0;
        audioButton.setImageResource(android.R.drawable.ic_media_play);
        //audioButton.setImageResource(R.drawable.ic_audio_play);
    }

    private class AudioButtonClickListener implements ImageView.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (play_mode)
            {
                case 0:
                    mediaPlayer.start();
                    play_mode = 1;
                    audioButton.setImageResource(android.R.drawable.ic_media_pause);
                    //audioButton.setImageResource(R.drawable.ic_audio_pause);
                    updateProgressBar();
                    break;
                case 1:
                    mediaPlayer.pause();
                    play_mode=0;
                    audioButton.setImageResource(android.R.drawable.ic_media_play);
                    //audioButton.setImageResource(R.drawable.ic_audio_play);
                    break;
                default:
                    Log.e(TAG, "Cannot identify play mode: " + play_mode);
                    mediaPlayer.release();
                    mediaPlayer = null;
            }
        }
    }

    /**
     * Update timer on seekbar
     * */
    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    /**
     * Background Runnable thread
     * */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mediaPlayer.getDuration();
            long currentDuration = mediaPlayer.getCurrentPosition();

            // Displaying Total Duration time
            songTotalDurationLabel.setText(""+utils.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
            songCurrentDurationLabel.setText(""+utils.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = utils.getProgressPercentage(currentDuration, totalDuration);
            //Log.d("Progress", ""+progress);
            songProgressBar.setProgress(progress);

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };

    /**
     *
     * */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {

    }

    /**
     * When user starts moving the progress handler
     * */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // remove message Handler from updating progress bar
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    /**
     * When user stops moving the progress hanlder
     * */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = mediaPlayer.getDuration();
        int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

        // forward or backward to certain seconds
        mediaPlayer.seekTo(currentPosition);

        // update timer progress again
        updateProgressBar();
    }

    @Override
    public void onDestroy()
    {
        mHandler.removeCallbacks(mUpdateTimeTask);
        //release and destroy the media player
        mediaPlayer.release();
        mediaPlayer = null;

        Variables.saveLearnTimeBoth();
        super.onDestroy();
    }
}
