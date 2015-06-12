package com.wab.lernapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

/**
 * Created by Student on 12.06.2015.
 */
public class LerntestActivity extends FragmentActivity {
    private final String TAG = "TestActivity";
    Activity activity;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtils.onActivityCreateSetTheme(this, getThemeNumber());
        setContentView(R.layout.activity_test);
        Log.d(TAG, "Test Activity created");

        activity = this;

        getActionBar().setTitle("Lerntest");

        fragment = new LerntestFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().add(R.id.content_frame_test, fragment).commit();

    }

  //  @Override
   /* public boolean onCreateOptionsMenu(Menu menu) {
        // kein menu
        return true;
    }*/

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    private int getThemeNumber() {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String strFarbe = SP.getString("preference_appearance","@string/default_style_value");
        if (strFarbe.equals("Grün"))
        {
            return ThemeUtils.GREEN;
        }
        else if (strFarbe.equals("Orange"))
        {
            return ThemeUtils.ORANGE;
        }
        else if (strFarbe.equals("Gelb"))
        {
            return ThemeUtils.YELLOW;
        }
        else
        {
            return ThemeUtils.YELLOW;
        }
    }


}
