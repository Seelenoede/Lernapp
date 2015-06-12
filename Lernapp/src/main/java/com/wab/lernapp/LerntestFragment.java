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
public class LerntestFragment extends Fragment {

    public LerntestFragment() {
    }

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        List<Test> test = Test.initializeData(getActivity());
        View rootView = inflater.inflate(R.layout.fragment_lerntest, container, false);
        RecyclerView rv = (RecyclerView)rootView.findViewById(R.id.content_rv);
        rv.setHasFixedSize(false);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        RVAdapter_test adapter = new RVAdapter_test(test);
        rv.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        getActivity().getActionBar().setTitle("Lerntest");
        MainActivity.setDrawerSelected(3);
    }

}
