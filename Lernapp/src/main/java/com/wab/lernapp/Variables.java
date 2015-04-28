package com.wab.lernapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Student on 28.04.2015.
 *
 * In this class the values for Auswertung are saved and loaded
 */
public class Variables
{
    /**
     * These vars are used for transmitting values from FileHandler to MainActivity
     * Not for real use
     */
    public static long startTimeTmp;
    public static int startDateTmpH;
    public static String startDateTmp;

    private static SharedPreferences mPrefs;

    /**
     * All vars that can be used everywhere
     *
     * TODO: add testResults
     */
    public static long[] learnTimes;
    public static long carTime;
    public static long learnTime;
    public static double averageGrade;

    public static void loadVars(Context context)
    {
        mPrefs = context.getSharedPreferences("Vars", 0);

        learnTimes = new long[24];
        carTime = mPrefs.getLong("carTime", 0);
        learnTime = mPrefs.getLong("learnTime", 0);
        averageGrade = Double.parseDouble(mPrefs.getString("averageGrade", "0.0"));

        for(int i=0; i<24; i++)
        {
            learnTimes[i] = mPrefs.getLong("learnTimes" + Integer.toString(i), 0);
        }
    }

    /**
     * save values to phone
     */
    public static void saveCarTime()
    {
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putLong("carTime", carTime).commit();
    }

    public static void saveLearnTime()
    {
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putLong("learnTime", learnTime).commit();
    }

    public static void saveAverageGrade()
    {
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("averageGrade", Double.toString(averageGrade)).commit();
    }

    public static void saveLearnTimes(int index)
    {
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putLong("learnTimes" + index, learnTimes[index]).commit();
    }

    /**
     * delete all saved values
     */
    public static void deleteValues()
    {
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

        //TODO: add testResults
    }
}
