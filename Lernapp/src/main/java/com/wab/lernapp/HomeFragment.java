package com.wab.lernapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;

/**
 * Created by Student on 14.04.2015.
 */
public class HomeFragment extends Fragment {

    File[] allFiles;
    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        rootView.setBackgroundColor(((MainActivity)getActivity()).currentColor);
        allFiles = FileHandler.getAllFiles();

        for (File file : allFiles)
        {

        }

        return rootView;
    }
}
