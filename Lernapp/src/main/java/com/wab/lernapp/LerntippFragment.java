package com.wab.lernapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

/**
 * Created by Student on 14.04.2015.
 */
public class LerntippFragment extends Fragment {

    public LerntippFragment() {
    }

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        List<Tipp> persons = Tipp.initializeData(getActivity());
        View rootView = inflater.inflate(R.layout.fragment_lerntipps, container, false);
        RecyclerView rv = (RecyclerView)rootView.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        RVAdapter adapter = new RVAdapter(persons);
        rv.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        getActivity().getActionBar().setTitle("Lerntipps");
        MainActivity.setDrawerSelected(3);
    }

}
