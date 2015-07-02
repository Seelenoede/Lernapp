package com.wab.lernapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * Created by Student on 14.04.2015.
 */
public class HomeFragment extends Fragment {

    public static FileHandler fileHandler;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        fileHandler = new FileHandler();

        ListView mItemList = (ListView) rootView.findViewById(R.id.fileList);
        ArrayList<ItemHome> items = new ArrayList<>();

        for(File file : fileHandler.fileList.get(0))
        {
            items.add(new EntryItemHome(file.getName(), R.drawable.ic_folder));
        }
        if(fileHandler.fileList.get(fileHandler.fileList.size()-1).length > 0)
        {
            items.add(new EntryItemHome("Anderes", R.drawable.ic_folder));
        }

        EntryAdapterHome adapter = new EntryAdapterHome(rootView.getContext(), items);
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

    private void selectItem(int position)
    {
        fileHandler.openDirectory(position, this.getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().getActionBar().setTitle("Home");
        MainActivity.setDrawerSelected(0);
    }
}
