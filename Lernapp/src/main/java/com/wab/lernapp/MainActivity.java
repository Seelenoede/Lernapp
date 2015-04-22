package com.wab.lernapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

public class MainActivity extends Activity {
    //jetzt kommen die Variablen
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    public static int currentColor;
    private Fragment fragment;
    private View view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle = mDrawerTitle = getTitle();

        mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[4];

        drawerItem[0] = new ObjectDrawerItem(R.drawable.ic_home, "Home");
        drawerItem[1] = new ObjectDrawerItem(R.drawable.ic_auto, "Automodus");
        drawerItem[2] = new ObjectDrawerItem(R.drawable.ic_chart, "Auswertungen");
        drawerItem[3] = new ObjectDrawerItem(R.drawable.ic_einstellungen, "Einstellungen");

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
             Intent intent = new Intent(this, settings.class);
             startActivity(intent);
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

                 toast = Toast.makeText(context, text, duration);
                 toast.show();
                 currentColor = getResources().getColor(R.color.lightgreen);
                 setCurrentColor();
                 return true;
             case R.id.action_filter:
                 context = getApplicationContext();
                 text = "Filtern kommt noch!";
                 duration = Toast.LENGTH_SHORT;
                 currentColor = getResources().getColor(R.color.lightpink);
                 setCurrentColor();
                 toast = Toast.makeText(context, text, duration);
                 toast.show();
                 return true;
             default:
                 return super.onOptionsItemSelected(item);
         }
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
                fragment = new EinstellungenFragment();
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

    private void setCurrentColor(){
        mDrawerList.setBackgroundColor(currentColor);
        getActionBar().setBackgroundDrawable(new ColorDrawable(currentColor));
        getCurrentFocus().setBackgroundColor(currentColor);

        if(fragment!=null)
        {
            fragment.getView().setBackgroundColor(currentColor);
        }
    }
}
