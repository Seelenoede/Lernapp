package com.wab.lernapp;

import android.app.Activity;

import android.content.Intent;


public class ThemeUtils

{

    private static int cTheme;



    public final static int GREEN = 1;

    public final static int PURPLE = 0;

    public static void changeToTheme(Activity activity, int theme)

    {

        cTheme = theme;

        activity.finish();



        activity.startActivity(new Intent(activity, activity.getClass()));


    }

    public static void onActivityCreateSetTheme(Activity activity)

    {

        switch (cTheme)

        {

            default:
            case PURPLE:

                activity.setTheme(R.style.PurpleTheme);

                break;

            case GREEN:

                activity.setTheme(R.style.GreenTheme);

                break;

        }

    }

}