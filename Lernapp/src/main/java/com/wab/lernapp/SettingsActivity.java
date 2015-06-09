package com.wab.lernapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Alexander on 27.04.2015.
 */

public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    /**
     * For security it is necessary to check if a Fragment build inside of a Activity is valid (comes with API 19)
     * Just returning "true" works, but it's not the idea this method.
     */
    private int COLOR_CHANGED = 101;
    private int LEARNTYPE_CHANGED = 102;
    @Override
    protected boolean isValidFragment(String SettingsActivity$PrefsFragDidacticType) {
        /**
         * Fixme: isVaildFragment - Sicherheitscheck seit API19, wird derzeit umgangen
         * Es ist die Frage wie man diese Funktion richtig aufruft/nutzt
         * muss ich für jedes Fragment diese Funktion erneut anwenden/überschreiben?
         */

        //return SettingsActivity.class.getName().equals(SettingsActivity$PrefsFragDidacticType);
        return true;
    }

    /**
     * using "Bundle savedInstanceState" to save non-persistent data in a Bundle
     * This prevents the data from being lost if the activity needs to be recreated (e.g. orientation change)
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.onActivityCreateSetTheme(this,getThemeNumber());
        super.onCreate(savedInstanceState);
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }
    /**
     * Populate the activity with the top-level headers.
     * Headers are defined in resource "preference_headers.xml"
     * Loading headers using a list.
    */
    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.preference_headers, target);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        /*int duration;
        Context context;
        Toast toast;
        duration = Toast.LENGTH_SHORT;
        context = getApplicationContext();
        toast = Toast.makeText(context, key , duration);
        toast.show();*/
        //Intent returnIntent = new Intent();
        if (key.equals("preference_appearance"))
        {
            //das ist wenn Theme gleich geändert werden soll
            //setCurrentColor(sharedPreferences);
            //setResult(COLOR_CHANGED, returnIntent);
        }
    }

    /**
     * This Fragment shows the preferences for the first header.
     * Loading preferences fragment to edit didactic type settings
     * "Bundle savedInstanceState" see first OnCreate method in this activity
     */
    //TODO: Back-Button verweist nicht auf "letztes" Fragment
    public static class PrefsFragDidacticType extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

            //Set default values
            PreferenceManager.setDefaultValues(getActivity(), R.xml.preference_lerntyp, false);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preference_lerntyp);
        }
    }

    /**
     * This is a second-level fragment.
     * It shows up the "didactic type test" - Activity/Fragment
     */
    public static class PrefsFragDidacticTypeTest extends PreferenceFragment{
        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);

            //TODO: Lerntyptest als Fragment der Einstellungen oder als eigene Activity?
        }
    }



    /**
     * This Fragment shows the preferences for the second header.
     * Loading preferences fragment to edit Appearance settings
     * "Bundle savedInstanceState" see first OnCreate method in this activity
     */
    public static class PrefsFragAppearance extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            //Set default values
           // PreferenceManager.setDefaultValues(getActivity(), R.xml.preference_appearance, true);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preference_appearance);
        }
    }

    /**
     * This Fragment shows the preferences for the impressum header.
     * Loading preferences fragment to edit Appearance settings
     * "Bundle savedInstanceState" see first OnCreate method in this activity
     */
    public static class PrefsFragImpressum extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference_impressum);
        }
    }

    //zum einstellen des Themes abhängig von der Einstellung; wird im Mom nicht genutzt
    private void setCurrentColor(SharedPreferences SP) {

        String strFarbe = SP.getString("preference_appearance", "@string/default_style_value");
        if (strFarbe.equals("Grün"))
        {
            ThemeUtils.changeToTheme(this, ThemeUtils.GREEN);
        }
        else if (strFarbe.equals("Orange"))
        {
            ThemeUtils.changeToTheme(this, ThemeUtils.ORANGE);
        }
        else if (strFarbe.equals("Yellow"))
        {
            ThemeUtils.changeToTheme(this, ThemeUtils.YELLOW);
        }
    }

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
