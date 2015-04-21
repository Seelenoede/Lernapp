package com.wab.lernapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Student on 14.04.2015.
 */
public class EinstellungenFragment extends Fragment {

    public EinstellungenFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_einstellungen, container, false);
        rootView.setBackgroundColor(((MainActivity)getActivity()).currentColor);
        return rootView;
    }
}
