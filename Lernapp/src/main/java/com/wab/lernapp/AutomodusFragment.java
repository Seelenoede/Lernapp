package com.wab.lernapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Student on 14.04.2015.
 */
public class AutomodusFragment extends Fragment{

    long startTime;
    long endTime;

    public AutomodusFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        startTime = System.nanoTime();
        View rootView = inflater.inflate(R.layout.fragment_automodus, container, false);
        return rootView;
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        endTime = System.nanoTime();
        long delta = endTime - startTime;

        Variables.carTime += (delta/1e9);
        Variables.saveCarTime();
    }

    @Override
    public void onResume()
    {
        super.onResume();

        getActivity().getActionBar().setTitle("Automodus");
        MainActivity.setDrawerSelected(1);
    }
}
