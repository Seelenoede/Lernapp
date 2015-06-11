package com.wab.lernapp;


import android.os.Bundle;
import android.app.Fragment;
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
 * A simple {@link Fragment} subclass.
 */
public class FileViewerFragment extends Fragment {

    FileHandler fileHandler;
    private final String TAG = "FileViewerFragment";

    public FileViewerFragment() {
        // Required empty public constructor
    }

    public static FileViewerFragment newInstance(int directory) {
        FileViewerFragment fragment = new FileViewerFragment();
        Bundle args = new Bundle();
        args.putInt("srcDir", directory);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_file_viewer, container, false);

        ListView mItemList = (ListView) rootView.findViewById(R.id.fileViewerList);
        ArrayList<ItemHome> items = new ArrayList<>();
        fileHandler = HomeFragment.fileHandler;
        int inputDir = getArguments().getInt("srcDir");

        if(inputDir == fileHandler.fileList.size()-2)
        {
            getActivity().setTitle("Anderes");
        }
        else
        {
            getActivity().setTitle(fileHandler.fileList.get(0)[inputDir].getName());
        }

        for(File file : fileHandler.fileList.get(inputDir+1))
        {
            String mimeType = fileHandler.fileTypes.get(file);

            if (mimeType.equals("application/pdf"))
            {
                items.add(new EntryItemHome(file.getName(), R.drawable.ic_pdf));
            }
            else
            {
                String shortType = mimeType.substring(0, mimeType.lastIndexOf('/'));
                switch (shortType)
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

        EntryAdapterHome adapter = new EntryAdapterHome(rootView.getContext(), items);
        mItemList.setAdapter(adapter);
        mItemList.setOnItemClickListener(new ListFileItemClickListener());

        return rootView;
    }

    private class ListFileItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position, view);
        }
    }

    private void selectItem(int position, View view)
    {
        int srcDir = getArguments().getInt("srcDir");

        File chosenFile = fileHandler.fileList.get(srcDir +1)[position];

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
        }
    }
}
