package com.wab.lernapp;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

/**
 * shows only audio files and the items are bigger than in the home fragment
 *
 * Created by Student on 14.04.2015.
 */
public class AutomodusFragment extends Fragment{

    long startTime;
    long endTime;
    private final String TAG = "AutomodusFragment";
    FileHandler fileHandler;
    ArrayList<File> resultFiles;

    public AutomodusFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "Start Automodus");
        startTime = System.nanoTime();
        View rootView = inflater.inflate(R.layout.fragment_automodus, container, false);
        ListView mItemList = (ListView) rootView.findViewById(R.id.carList);

        ArrayList<ItemHome> items = new ArrayList<>();
        resultFiles = new ArrayList<>();

        fileHandler = new FileHandler();

        for(int i=1; i<fileHandler.fileList.size(); i++)
        {
            File[] files = fileHandler.fileList.get(i);
            for(File file : files)
            {
                String mimeType = fileHandler.fileTypes.get(file);
                String shortType = mimeType.substring(0, mimeType.lastIndexOf('/'));

                if(shortType.equals("audio"))
                {
                    resultFiles.add(file);
                    items.add(new CarItem(file.getName(), R.drawable.ic_audio));
                }
            }
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

        private void selectItem(int position)
        {
            File chosenFile = resultFiles.get(position);
            Variables.setStartTimes();

            //Open Audio File
            fileHandler.openAudio(chosenFile, getActivity());
        }
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
