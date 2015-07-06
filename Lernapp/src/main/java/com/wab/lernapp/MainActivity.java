package com.wab.lernapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends FragmentActivity implements View.OnClickListener{

    static Activity activity;
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private static ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private Fragment fragment;
    private MenuItem menuItem;
    private int SETTINGS_REQUEST = 100;
    private int SETTINGS_COLOR_CHANGED = 101;
    public static int LEARNTIME_REQUEST = 200;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        System.out.println("on create");
		super.onCreate(savedInstanceState);
        ThemeUtils.onActivityCreateSetTheme(this , getThemeNumber());
        activity = this;

        Variables.loadVars(activity.getApplicationContext());

        setContentView(R.layout.activity_main);
        mTitle = mDrawerTitle = getTitle();

        mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[4];

        drawerItem[0] = new ObjectDrawerItem(R.drawable.ic_home, "Home");
        drawerItem[1] = new ObjectDrawerItem(R.drawable.ic_auto, "Automodus");
        drawerItem[2] = new ObjectDrawerItem(R.drawable.ic_chart, "Auswertungen");
        drawerItem[3] = new ObjectDrawerItem(R.drawable.ic_light, "Lerntipps");


        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.listview_item_row, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // home-fragment als Startseite
        fragment = new HomeFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().add(R.id.content_frame, fragment).commit();
        setTitle("Home");

        //Aufruf Lerntest
        startLerntest();
	}

    private void startLerntest(){
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        HashSet<String> defaultTypes = new HashSet<>();
        defaultTypes.add("leer");
        defaultTypes.add("leer");
        Set<String> didacticTypes = SP.getStringSet("preference_didactic_type", defaultTypes);
        if (didacticTypes.contains("leer"))
        {
            Intent intent = new Intent(MainActivity.this, LerntestActivity.class);
            startActivity(intent);

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
            return ThemeUtils.GREEN;
        }
    }

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        //SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setSubmitButtonEnabled(true);

        return true;
	}

    public boolean onOptionsItemSelected(MenuItem item) {
         // Handle presses on the action bar items
         int id = item.getItemId();
         if (id == R.id.action_settings) {
             Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
             startActivityForResult(intent, SETTINGS_REQUEST);
             return true;
         }
         if (mDrawerToggle.onOptionsItemSelected(item)) {
             return true;
         }

         CharSequence text;
         int duration;
         Context context;
         Toast toast;

         switch (id) {
             case R.id.action_filter:
                 context = getApplicationContext();
                 text = "Filtern kommt noch!";
                 duration = Toast.LENGTH_SHORT;
                 //currentColor = getResources().getColor(R.color.lightpink);
                 //setCurrentColor();
                 toast = Toast.makeText(context, text, duration);
                 toast.show();
                 return true;
             default:
                 return super.onOptionsItemSelected(item);
         }
	}

    @Override
    public void onClick(View v) {
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }

    private void selectItem(int position) {

        fragment = null;

        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new AutomodusFragment();
                break;
            case 2:
                fragment = new AuswertungenFragment();
                break;
            case 3:
                fragment = new LerntippFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    //Wenn ein Intent geschlossen wird, wird folgende Funktion ausgeführt
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        //Wenn von Settings Result erwartet

        if (requestCode == SETTINGS_REQUEST) {
        //TODO nochmal nach dem resultcode schauen
            if (resultCode == SETTINGS_COLOR_CHANGED)
            {

            }
        }
        else if(requestCode == LEARNTIME_REQUEST)
        {
            Variables.saveLearnTimeBoth();
        }
    }

    //soll im MOment nicht mehr verwendet werden, da kein Neustart der App erzwungen werden soll
   private void setCurrentColor(){
       ThemeUtils.changeToTheme(this, getThemeNumber());
   }

   public static void setDrawerSelected(int position)
    {
        mDrawerList.setItemChecked(position, true);
        mDrawerList.setSelection(position);
    }

    @Override
    public void onBackPressed()
    {
        if(mDrawerLayout.isDrawerOpen(mDrawerList))
        {
            mDrawerLayout.closeDrawer(mDrawerList);
        }
        else if(getFragmentManager().getBackStackEntryCount() > 0)
        {
            getFragmentManager().popBackStack();
        }
        else
        {
            super.onBackPressed();
        }
    }

    protected void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);
    }

    protected void onRestoreInstanceState(Bundle bundle)
    {
        getFragmentManager().beginTransaction().remove(fragment).commit();
        super.onRestoreInstanceState(bundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(menuItem != null)
        {
            menuItem.collapseActionView();
        }
    }
}
