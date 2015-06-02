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

    FileHandler fileHandler;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        //rootView.setBackgroundColor(((MainActivity)getActivity()).currentColor);

        fileHandler = new FileHandler();

        ListView mItemList = (ListView) rootView.findViewById(R.id.fileList);
        ArrayList<ItemHome> items = new ArrayList<>();

        int count = 1;

        for(File file : fileHandler.fileList.get(0))
        {
            if(file.isDirectory())
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
        }

        items.add(new SectionItemHome("Sonstiges"));

        for(File file : fileHandler.fileList.get(0))
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
        int count = 0;
        while(position > fileHandler.fileList.get(count).length-1)
        {
            position-=fileHandler.fileList.get(count).length;
            count++;
        }
        File chosenFile = fileHandler.fileList.get(count)[position];

        String mimeType = fileHandler.fileTypes.get(chosenFile);
        Context context = view.getContext();
        Variables.setStartTimes();
        if(mimeType.equals("application/pdf"))
        {
            fileHandler.openPDF(chosenFile, this.getActivity());
        }
        else
        {
            String shortType = mimeType.substring(0, mimeType.lastIndexOf('/'));
            int duration;
            String text;
            Toast toast;

            switch(shortType)
            {
                case "text":
                    //Open Text File
                    openText(chosenFile);
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
                    text = "Kann Datei nicht oeffnen";
                    duration = Toast.LENGTH_SHORT;
                    toast = Toast.makeText(context, text, duration);
                    toast.show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().getActionBar().setTitle("Home");
        MainActivity.setDrawerSelected(0);
    }

    private void openText(File chosenFile)
    {
        Variables.chosenFile = chosenFile;
        Fragment fragment = new TextViewFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
    }
}
