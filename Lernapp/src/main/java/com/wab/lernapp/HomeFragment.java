package com.wab.lernapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;

/**
 * Created by Student on 14.04.2015.
 */
public class HomeFragment extends Fragment {

    File[] allFiles;
    private ListView mItemList;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        rootView.setBackgroundColor(((MainActivity)getActivity()).currentColor);

        allFiles = FileHandler.getAllFiles();

        mItemList = (ListView) rootView.findViewById(R.id.fileList);

        ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[allFiles.length - 1];

        int count = 0;

        for(File file : allFiles)
        {
            //drawerItem[count] = new ObjectDrawerItem(icon, "Name");
            count++;
        }

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(rootView.getContext(), R.layout.listview_item_row, drawerItem);
        mItemList.setAdapter(adapter);
        mItemList.setOnItemClickListener(new ListFileItemClickListener());

        return rootView;
    }

    private class ListFileItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }

    private void selectItem(int position) {

    }
}
