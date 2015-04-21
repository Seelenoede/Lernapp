package com.wab.lernapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Student on 14.04.2015.
 */
public class AuswertungenFragment extends Fragment {

    public AuswertungenFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_auswertungen, container, false);
        rootView.setBackgroundColor(((MainActivity)getActivity()).currentColor);

        return rootView;
    }
}
