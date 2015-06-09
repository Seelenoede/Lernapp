package com.wab.lernapp;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class ThemeUtils

{

    private static int cTheme;



    public final static int GREEN = 1;

    public final static int YELLOW = 0;

    public final static int ORANGE = 2;

    public static void changeToTheme(Activity activity, int theme)

    {

        cTheme = theme;

        activity.finish();



        activity.startActivity(new Intent(activity, activity.getClass()));


    }

    public static void onActivityCreateSetTheme(Activity activity,int theme)

    {
        cTheme = theme;
        switch (cTheme)

        {

            default:
            case YELLOW:

                activity.setTheme(R.style.YellowTheme);

                break;

            case GREEN:

                activity.setTheme(R.style.GreenTheme);

                break;
            case ORANGE:

                activity.setTheme(R.style.OrangeTheme);

                break;

        }

    }

    public static int getCurrentTheme()
    {
        return cTheme;
    }
}