package com.wab.lernapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * Created by Student on 14.04.2015.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";


    //TODO: Load directory if needed
    public static FileHandler fileHandler;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        //rootView.setBackgroundColor(((MainActivity)getActivity()).currentColor);

        fileHandler = new FileHandler();

        ListView mItemList = (ListView) rootView.findViewById(R.id.fileList);
        ArrayList<ItemHome> items = new ArrayList<>();

        /*int count = 1;

        for(File file : fileHandler.fileList.get(0))
        {
            items.add(new SectionItemHome(file.getName()));
            for(File file1 : fileHandler.fileList.get(count))
            {
                String mimeType = fileHandler.fileTypes.get(file1);

                if(mimeType.equals("application/pdf"))
                {
                    items.add(new EntryItemHome(file1.getName(), R.drawable.ic_pdf));
                }
                else
                {
                    String shortType = mimeType.substring(0, mimeType.lastIndexOf('/'));
                    switch(shortType)
                    {
                        case "text":
                            items.add(new EntryItemHome(file1.getName(), R.drawable.ic_text));
                            break;
                        case "audio":
                            items.add(new EntryItemHome(file1.getName(), R.drawable.ic_audio));
                            break;
                        case "video":
                            items.add(new EntryItemHome(file1.getName(), R.drawable.ic_video));
                            break;
                        case "folder":
                            break;
                        default:
                            Log.w(TAG, "Could not associate MIME-Type: " + mimeType);
                            items.add(new EntryItemHome(file1.getName(), 0));
                    }
                }
            }
            count++;
        }

        items.add(new SectionItemHome("Sonstiges"));

        for(File file : fileHandler.fileList.get(fileHandler.fileList.size()-1))
        {
            String mimeType = fileHandler.fileTypes.get(file);

            if(mimeType.equals("application/pdf"))
            {
                items.add(new EntryItemHome(file.getName(), R.drawable.ic_pdf));
            }
            else
            {
                String shortType = mimeType.substring(0, mimeType.lastIndexOf('/'));
                switch(shortType)
                {
                    case "text":
                        items.add(new EntryItemHome(file.getName(), R.drawable.ic_text));
                        break;
                    case "audio":
                        items.add(new EntryItemHome(file.getName(), R.drawable.ic_audio));
                        break;
                    case "video":
                        items.add(new EntryItemHome(file.getName(), R.drawable.ic_video));
                        break;
                    case "folder":
                        break;
                    default:
                        Log.w(TAG, "Could not associate MIME-Type: " + mimeType);
                        items.add(new EntryItemHome(file.getName(), 0));
                }
            }
        }*/
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
        /*int count = 1;
        while(position > fileHandler.fileList.get(count).length)
        {
            position = position - fileHandler.fileList.get(count).length - 1;
            count++;
        }
        File chosenFile = fileHandler.fileList.get(count)[position-1];

        String mimeType = fileHandler.fileTypes.get(chosenFile);
        Variables.setStartTimes();
        if(mimeType.equals("application/pdf"))
        {
            fileHandler.openPDF(chosenFile, this.getActivity());
        }
        else
        {
            String shortType = mimeType.substring(0, mimeType.lastIndexOf('/'));
            switch(shortType)
            {
                case "text":
                    //Open Text File
                    fileHandler.openText(chosenFile, this.getActivity());
                    break;
                case "audio":
                    //Open Audio File
                    fileHandler.openAudio(chosenFile, this.getActivity());
                    break;
                case "video":
                    //Open Video File
                    fileHandler.openVideo(chosenFile, this.getActivity());
                    break;
                default:
                    Log.w(TAG, "Could not associate MIME-Type: " + mimeType);
                    Toast toast = Toast.makeText(view.getContext(), "Kann Datei nicht oeffnen", Toast.LENGTH_SHORT);
                    toast.show();
            }
        }*/
        fileHandler.openDirectory(position, this.getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().getActionBar().setTitle("Home");
        MainActivity.setDrawerSelected(0);
    }
}
