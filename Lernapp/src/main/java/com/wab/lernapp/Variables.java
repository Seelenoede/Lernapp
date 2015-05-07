package com.wab.lernapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Student on 28.04.2015.
 *
 * In this class the values for Auswertung are saved and loaded
 */
public class Variables
{
    private static final String TAG = "Variables";

    /**
     * the start values only to save the start time
     */
    private static long startTime;
    private static String startDate;

    private static SharedPreferences mPrefs;

    /**
     * All vars that can be used everywhere
     */
    public static long[] learnTimes;
    public static long carTime;
    public static long learnTime;
    public static double averageGrade;
    public static double[] allGrades;
    public static int gradeCount;

    public static void loadVars(Context context)
    {
        Log.d(TAG, "Load Variables");
        mPrefs = context.getSharedPreferences("Vars", 0);

        learnTimes = new long[24];
        carTime = mPrefs.getLong("carTime", 0);
        learnTime = mPrefs.getLong("learnTime", 0);
        averageGrade = Double.parseDouble(mPrefs.getString("averageGrade", "0.0"));
        gradeCount = mPrefs.getInt("gradeCount", 0);
        allGrades = new double[gradeCount];

        if(gradeCount!=0)
        {
            for(int i = 0; i<gradeCount; i++)
            {
                allGrades[i] = Double.parseDouble(mPrefs.getString("allGrades" + i, "0.0"));
            }
        }

        for(int i=0; i<24; i++)
        {
            learnTimes[i] = mPrefs.getLong("learnTimes" + Integer.toString(i), 0);
        }
    }

    public static void reloadGrades()
    {
        for(int i = 0; i<gradeCount; i++)
        {
            allGrades[i] = Double.parseDouble(mPrefs.getString("allGrades" + i, "0.0"));
        }
    }

    /**
     * save values to phone
     */
    public static void saveCarTime()
    {
        Log.d(TAG, "Save state car time: " + carTime);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putLong("carTime", carTime).commit();
    }

    private static void saveLearnTime()
    {
        Log.d(TAG, "Save state learn time: " + learnTime);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putLong("learnTime", learnTime).commit();
    }

    public static void saveAverageGrade()
    {
        Log.d(TAG, "Save state average grade: " + averageGrade);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("averageGrade", Double.toString(averageGrade)).commit();
    }

    private static void saveLearnTimes(int index)
    {
        Log.d(TAG, "Save state learn time " + index + ": " + learnTimes[index]);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putLong("learnTimes" + index, learnTimes[index]).commit();
    }

    public static void saveGrade(double grade)
    {
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("allGrades" + gradeCount, Double.toString(grade));

        gradeCount++;
        mEditor.putInt("gradeCount", gradeCount);

        reloadGrades();
    }

    /**
     * delete all grades
     *
     * extra function because saving a grade is a little different than saving variables
     */
    private static void deleteGrades()
    {
        SharedPreferences.Editor mEditor = mPrefs.edit();
        for(int i=0; i<gradeCount; i++)
        {
            mEditor.putString("allGrades" + i, "0.0");
        }

        mEditor.putInt("gradeCount", gradeCount);
    }

    /**
     * delete all saved values
     */
    public static void deleteValues()
    {
        Log.d(TAG, "Delete grades");
        carTime = 0;
        learnTime = 0;
        averageGrade = 0.0;

        for(int i=0; i<24; i++)
        {
            learnTimes[i] = 0;
            saveLearnTimes(i);
        }

        saveCarTime();
        saveAverageGrade();
        saveLearnTime();
        deleteGrades();
    }

    public static void setStartTimes()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.GERMANY);
        startDate = sdf.format(new Date());

        //the actual time
        startTime = System.nanoTime();
    }

    /**
     * save learnTime and learnTimes
     */
    public static void saveLearnTimeBoth()
    {
        long endTime = System.nanoTime();
        long delta = endTime - startTime;
        long timeLearned = 0;
        timeLearned += delta / 1e9; //in seconds
        learnTime += timeLearned;
        saveLearnTime();

        int startDateH = Integer.parseInt(startDate.substring(0, 2));
        int startDateM = Integer.parseInt(startDate.substring(3, 5));
        int startDateS = Integer.parseInt(startDate.substring(6));
        SimpleDateFormat sdf = new SimpleDateFormat("HH", Locale.GERMANY);
        int endDateH = Integer.parseInt(sdf.format(new Date()));

        /**
         * Check if closed in different hour (e.g. starts 09:00 and ends 11:00)
         *
         * No: learnTimes are just saved
         *
         * Yes: look below (go to next comment block)
         */
        if (endDateH == startDateH)
        {
            learnTimes[endDateH] += timeLearned;
            saveLearnTimes(endDateH);
        }
        else
        {
            /**
             * First add the remainders of the first hour to learnTimes[start hour]
             *
             * then... (see next comment block)
             */
            long restM = timeLearned / 60; //in minutes
            int timeDiff = 1; //hours in advance of start time

            learnTimes[startDateH] += (60 - startDateM) * 60 - startDateS;
            saveLearnTimes(startDateH);
            restM = restM - 60 + startDateM;
            timeLearned = timeLearned - (60 - startDateM) * 60 + startDateS;

            /**
             * ... look if there more than 60 minutes left
             *
             * if there are then just add 60 minutes to the hour
             *
             * if not then add the rest to the last hour
             */
            while (restM != 0)
            {
                if (restM >= 60)
                {
                    learnTimes[startDateH + timeDiff] += 60 * 60;
                    saveLearnTimes(startDateH + timeDiff);
                    restM = restM - 60;
                    timeLearned = timeLearned - 3600;
                    timeDiff++;
                }
                else
                {
                    timeLearned = timeLearned - restM * 60;
                    learnTimes[startDateH + timeDiff] += restM * 60 + timeLearned;
                    saveLearnTimes(startDateH + timeDiff);
                    restM = 0;
                }
            }
        }
    }
}
