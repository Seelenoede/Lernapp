package com.wab.lernapp;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import java.util.List;

/**
 * Created by Alexander on 27.04.2015.
 */

public class SettingsActivity extends PreferenceActivity {



    /**
     * For security it is necessary to check if a Fragment build inside of a Activity is valid (comes with API 19)
     * Just returning "true" works, but it's not the idea this method.
     */
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
        super.onCreate(savedInstanceState);

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
            PreferenceManager.setDefaultValues(getActivity(), R.xml.preference_appearance, false);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preference_appearance);
        }
    }
}
