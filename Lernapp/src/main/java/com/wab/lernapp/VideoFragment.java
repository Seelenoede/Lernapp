package com.wab.lernapp;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.IOException;

/**
 * A fragment that contains a surface view to show videos
 *
 * TODO: give media player controls
 * class is not used
 *
 * @author pmeyerbu
 */
public class VideoFragment extends Fragment implements SurfaceHolder.Callback
{
    private static final String TAG = "VideoFragment";

    private int activityOrientation;
    MediaPlayer mediaPlayer;
    SurfaceView surfaceView;
    SurfaceHolder holder;

    /**
     * mandatory empty constructor
     */
    public VideoFragment()
    {
    }

    /**
     * used to initialize the fragment
     *
     * @param src Source file
     * @return videoFragment
     */
    public static VideoFragment newInstance(File src)
    {
        VideoFragment videoFragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putString("srcPath", src.getAbsolutePath());
        videoFragment.setArguments(args);
        return videoFragment;
    }

    /**
     * this fragment should always be in landscape mode
     *
     * @param activity base activity
     */
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        activityOrientation = activity.getRequestedOrientation();
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_video, container, false);

        try
        {
            //A NullPointerException is caught. So...
            //noinspection ConstantConditions
            getActivity().getActionBar().setTitle("Video");
        }
        catch(NullPointerException e)
        {
            Log.e(TAG, "Cannot set title");
        }

        mediaPlayer = new MediaPlayer();
        surfaceView = (SurfaceView) rootView.findViewById(R.id.surfaceView);
        holder = surfaceView.getHolder();
        holder.addCallback(this);

        return rootView;
    }

    /**
     * Starts video
     */
    public void startVideo()
    {
        //match video to surface and prevent stretching
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                int videoWidth = mediaPlayer.getVideoWidth();
                int videoHeight = mediaPlayer.getVideoHeight();
                float videoProportion = (float) videoWidth / (float) videoHeight;

                int surfaceWidth = surfaceView.getWidth();
                int surfaceHeight = surfaceView.getHeight();
                float surfaceProportion = (float) surfaceWidth / (float) surfaceHeight;

                android.view.ViewGroup.LayoutParams lp = surfaceView.getLayoutParams();

                if(videoProportion > surfaceProportion)
                {
                    lp.width = surfaceWidth;
                    lp.height = (int) ((float) surfaceWidth / videoProportion);
                }
                else
                {
                    lp.width = (int) (videoProportion * (float) surfaceHeight);
                    lp.height = surfaceHeight;
                }

                surfaceView.setLayoutParams(lp);
            }
        });
        //prevent phone to turn black
        mediaPlayer.setScreenOnWhilePlaying(true);
        try
        {
            mediaPlayer.setDataSource(getArguments().getString("srcPath"));
            mediaPlayer.prepare();
            mediaPlayer.start();
        }
        catch (IllegalArgumentException | IOException e)
        {
            Log.e(TAG, "Probleme beim Oeffnen des Videos");
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onDestroy()
    {
        //release and destroy the media player
        mediaPlayer.release();
        mediaPlayer = null;

        //restore previous orientation mode
        getActivity().setRequestedOrientation(activityOrientation);
        Variables.saveLearnTimeBoth();
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        holder = this.holder;
        mediaPlayer.setDisplay(holder);
        startVideo();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        // Auto-generated method
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        // Auto-generated method
    }
}
