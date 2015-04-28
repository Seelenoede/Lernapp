package com.wab.lernapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Student on 28.04.2015.
 */
public class Variables
{
    public static long startTimeTmp;

    private static SharedPreferences mPrefs;
    public static long autoTime;
    public static long learnTime;
    public static double averageGrade;

    public static void loadVars(Context context)
    {
        mPrefs = context.getSharedPreferences("Vars", 0);

        autoTime = mPrefs.getLong("autoTime", 0);
        learnTime = mPrefs.getLong("learnTime", 0);
        averageGrade = Double.parseDouble(mPrefs.getString("averageGrade", "0.0"));
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
}
