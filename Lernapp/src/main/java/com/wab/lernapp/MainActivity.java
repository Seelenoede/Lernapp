package com.wab.lernapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements View.OnClickListener{

    static Activity activity;
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
   // public static int currentColor;
    private Fragment fragment;
    private int SETTINGS_REQUEST = 100;
    private int SETTINGS_COLOR_CHANGED = 101;
    private int SETTINGS_LEARNTYPE_CHANGED = 102;
    public static final String KEY_PREF_SYNC_CONN = "pref_syncConnectionType";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            return;
        }
        else
        {
            System.out.println("Es ist null");
        }
        System.out.println("in on create");
        //setCurrentColor();
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

        //home-fragment als Startseite
        fragment = new HomeFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().add(R.id.content_frame, fragment).commit();
        setTitle("Home");
	}

    private int getThemeNumber() {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String strFarbe = SP.getString("preference_appearance","@string/default_style_value");
        if (strFarbe.equals("Grün"))
        {
            return ThemeUtils.GREEN;
        }
        else if (strFarbe.equals("Lila"))
        {
            return ThemeUtils.PURPLE;
        }
        else
        {
            return ThemeUtils.PURPLE;
        }
    }

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    public boolean onOptionsItemSelected(MenuItem item) {
         // Handle presses on the action bar items
         int id = item.getItemId();
         if (id == R.id.action_settings) {
             Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
             startActivityForResult(intent,SETTINGS_REQUEST);
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
             case R.id.action_search:
                 context = getApplicationContext();
                 text = "Suche wird nicht unterstützt!";
                 duration = Toast.LENGTH_SHORT;
                 SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                 String strFarbe = SP.getString("preference_appearance","@string/default_style_value");
                 toast = Toast.makeText(context, strFarbe, duration);
                 toast.show();
                 ThemeUtils.changeToTheme(this,ThemeUtils.PURPLE);
                 //currentColor = getResources().getColor(R.color.lightgreen);
                // setCurrentColor();
                 return true;
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
        /*switch (v.getId()){
            case R.id.greenbutton:

               ThemeUtils.changeToTheme(this, ThemeUtils.GREEN);

                break;

            case R.id.purplebutton:

                ThemeUtils.changeToTheme(this, ThemeUtils.PURPLE);

                break;
    }*/
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
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

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
        System.out.println("In on activity result");
        super.onActivityResult(requestCode, resultCode, data);
        //Wenn von Settings Result erwartet
        if (requestCode == SETTINGS_REQUEST) {

            if (resultCode == SETTINGS_COLOR_CHANGED)
            {
                setCurrentColor();
            }
            else if (resultCode == SETTINGS_LEARNTYPE_CHANGED)
            {
                //Was auch immer passiert, wenn sich der Lerntyp ändert
            }
        }

    }

   private void setCurrentColor(){
       ThemeUtils.changeToTheme(this,getThemeNumber());
   }

   /* protected void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);
        System.out.println("on save instance");
    }

    protected void onRestoreInstanceState(Bundle bundle)
    {
        super.onRestoreInstanceState(bundle);
        System.out.println("on restore instance");
    }
    protected void onRestart()
    {
        super.onRestart();
        System.out.println("on restart");
    }
    protected void onStart() {
        super.onStart();
        System.out.println("on start");
    }
    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("on resume");
    }
    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("on pause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("on stop");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("on destroy");
    }*/
}
