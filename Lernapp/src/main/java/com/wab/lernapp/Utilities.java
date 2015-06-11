package com.wab.lernapp;

import java.io.File;

/**
 *
 * Created by Student on 09.06.2015.
 */
public class Utilities {

    /**
     * Function to convert milliseconds time to
     * Timer Format
     * Hours:Minutes:Seconds
     */
    public String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString;

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    /**
     * Function to get Progress percentage
     *
     * @param currentDuration current Duration
     * @param totalDuration total Duration
     */
    public int getProgressPercentage(long currentDuration, long totalDuration) {
        Double percentage;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        // calculating percentage
        percentage = (((double) currentSeconds) / totalSeconds) * 100;

        // return percentage
        return percentage.intValue();
    }

    /**
     * Function to change progress to timer
     *
     * @param progress      -
     * @param totalDuration returns current duration in milliseconds
     */
    public int progressToTimer(int progress, int totalDuration) {
        int currentDuration;
        totalDuration = totalDuration / 1000;
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        // return current duration in milliseconds
        return currentDuration * 1000;
    }

    public File[] removeDirectories(File[] inFiles)
    {
        int count = 0;
        for(File file : inFiles)
        {
            if (!file.isDirectory())
                count++;
        }
        File[] outFiles = new File[count];

        count=0;
        for(File file : inFiles)
        {
            if (!file.isDirectory())
                outFiles[count] = file;
        }

        return outFiles;
    }

    public File[] removeFiles(File[] inFiles)
    {
        int count = 0;
        for(File file : inFiles)
        {
            if (file.isDirectory())
                count++;
        }
        File[] outFiles = new File[count];

        count=0;
        for(File file : inFiles)
        {
            if (file.isDirectory())
                outFiles[count] = file;
        }

        return outFiles;
    }
}
