package com.wab.lernapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class SearchActivity extends FragmentActivity {

    private final String TAG = "SearchActivity";
    FileHandler fileHandler;
    Activity activity;
    ArrayList<File> resultFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        resultFiles = new ArrayList<>();
        Log.d(TAG, "Search Activity created");
        ThemeUtils.onActivityCreateSetTheme(this , getThemeNumber());

        activity = this;
        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchFile(query.toLowerCase(), (ListView) findViewById(R.id.search_result));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
    
    private void searchFile(String query, ListView baseView)
    {
        ArrayList<ItemHome> items = new ArrayList<>();

        fileHandler = new FileHandler();

        for(int i=1; i<fileHandler.fileList.size(); i++)
        {
            File[] files = fileHandler.fileList.get(i);
            for(File file : files)
            {
                if(file.getName().toLowerCase().contains(query))
                {
                    resultFiles.add(file);
                    String mimeType = fileHandler.fileTypes.get(file);

                    if(mimeType.equals("application/pdf"))
                    {
                        items.add(new EntryItemHome(file.getName(), R.drawable.ic_pdf));
                    }
                    else
                    {
                        String shortType = mimeType.substring(0, mimeType.lastIndexOf('/'));
                        switch(shortType)
                        {
                            case "text":
                                items.add(new EntryItemHome(file.getName(), R.drawable.ic_text));
                                break;
                            case "audio":
                                items.add(new EntryItemHome(file.getName(), R.drawable.ic_audio));
                                break;
                            case "video":
                                items.add(new EntryItemHome(file.getName(), R.drawable.ic_video));
                                break;
                            case "folder":
                                break;
                            default:
                                Log.w(TAG, "Could not associate MIME-Type: " + mimeType);
                                items.add(new EntryItemHome(file.getName(), 0));
                        }
                    }
                }
            }
        }
        EntryAdapterHome adapter = new EntryAdapterHome(baseView.getContext(), items);
        baseView.setAdapter(adapter);
        baseView.setOnItemClickListener(new ListFileItemClickListener());
    }

    private class ListFileItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position, view);
        }

        private void selectItem(int position, View view)
        {
            File chosenFile = resultFiles.get(position);

            String mimeType = fileHandler.fileTypes.get(chosenFile);
            Variables.setStartTimes();
            if(mimeType.equals("application/pdf"))
            {
                fileHandler.openPDF(chosenFile, activity);
            }
            else
            {
                String shortType = mimeType.substring(0, mimeType.lastIndexOf('/'));
                switch(shortType)
                {
                    case "text":
                        //Open Text File
                        fileHandler.openText(chosenFile, activity);
                        break;
                    case "audio":
                        //Open Audio File
                        fileHandler.openAudio(chosenFile, activity);
                        break;
                    case "video":
                        //Open Video File
                        fileHandler.openVideo(chosenFile, activity);
                        break;
                    default:
                        Log.w(TAG, "Could not associate MIME-Type: " + mimeType);
                        Toast toast = Toast.makeText(view.getContext(), "Kann Datei nicht oeffnen", Toast.LENGTH_SHORT);
                        toast.show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        //Wenn von Settings Result erwartet

        if(requestCode == MainActivity.LEARNTIME_REQUEST)
        {
            Variables.saveLearnTimeBoth();
        }
    }
}
