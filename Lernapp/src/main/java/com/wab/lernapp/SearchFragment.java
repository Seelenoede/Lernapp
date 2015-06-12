package com.wab.lernapp;

import android.app.Activity;
import android.app.Fragment;
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

public class SearchFragment extends Fragment{

    FileHandler fileHandler;
    ArrayList<File> resultFiles;
    Activity activity;
    private final String TAG = "SearchFragment";

    public static SearchFragment newInstance(String query) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString("searchQuery", query);
        fragment.setArguments(args);
        return fragment;
    }

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        resultFiles = new ArrayList<>();
        activity = getActivity();
        searchFile(getArguments().getString("searchQuery"), (ListView) rootView.findViewById(R.id.search_result));

        return rootView;
    }

    private void searchFile(String query, ListView baseView)
    {
        ArrayList<ItemHome> items = new ArrayList<>();

        fileHandler = new FileHandler();

        for(int i=1; i<fileHandler.fileList.size(); i++)
        {
            File[] files = fileHandler.fileList.get(i);
            for(File file : files)
            {
                if(file.getName().toLowerCase().contains(query))
                {
                    resultFiles.add(file);
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
                }
            }
        }
        EntryAdapterHome adapter = new EntryAdapterHome(baseView.getContext(), items);
        baseView.setAdapter(adapter);
        baseView.setOnItemClickListener(new ListFileItemClickListener());
    }

    private class ListFileItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position, view);
        }

        private void selectItem(int position, View view)
        {
            File chosenFile = resultFiles.get(position);

            String mimeType = fileHandler.fileTypes.get(chosenFile);
            Variables.setStartTimes();
            if(mimeType.equals("application/pdf"))
            {
                fileHandler.openPDF(chosenFile, activity);
            }
            else
            {
                String shortType = mimeType.substring(0, mimeType.lastIndexOf('/'));
                switch(shortType)
                {
                    case "text":
                        //Open Text File
                        fileHandler.openText(chosenFile, activity);
                        break;
                    case "audio":
                        //Open Audio File
                        fileHandler.openAudio(chosenFile, activity);
                        break;
                    case "video":
                        //Open Video File
                        fileHandler.openVideo(chosenFile, activity);
                        break;
                    default:
                        Log.w(TAG, "Could not associate MIME-Type: " + mimeType);
                        Toast toast = Toast.makeText(view.getContext(), "Kann Datei nicht oeffnen", Toast.LENGTH_SHORT);
                        toast.show();
                }
            }
        }
    }
}
