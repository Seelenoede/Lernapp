package com.wab.lernapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Student on 28.04.2015.
 */
public class Variables
{
    //These vars are used for transmitting values from FileHandler to MainActivity
    public static long startTimeTmp;
    public static int startDateTmpH;
    public static String startDateTmp;

    public static long[] learnTimes;
    private static SharedPreferences mPrefs;
    public static long autoTime;
    public static long learnTime;
    public static double averageGrade;

    public static void loadVars(Context context)
    {
        mPrefs = context.getSharedPreferences("Vars", 0);

        learnTimes = new long[24];
        autoTime = mPrefs.getLong("autoTime", 0);
        learnTime = mPrefs.getLong("learnTime", 0);
        averageGrade = Double.parseDouble(mPrefs.getString("averageGrade", "0.0"));

        for(int i=0; i<24; i++)
        {
            learnTimes[i] = mPrefs.getLong("learnTimes" + Integer.toString(i), 0);
        }
    }

    public static void saveAutoTime()
    {
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putLong("autoTime", autoTime).commit();
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

}
