package com.wab.lernapp;

import android.app.Fragment;
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

        ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[fileHandler.allFiles.length];

        int count = 0;

        for(File file : fileHandler.allFiles)
        {
            String mimeType = fileHandler.fileTypes.get(file);

            if(mimeType.equals("application/pdf"))
            {
                drawerItem[count] = new ObjectDrawerItem(R.drawable.ic_pdf, file.getName());
            }
            else
            {
                String shortType = mimeType.substring(0, mimeType.lastIndexOf('/'));
                switch(shortType)
                {
                    case "text":
                        drawerItem[count] = new ObjectDrawerItem(R.drawable.ic_text, file.getName());
                        break;
                    case "audio":
                        drawerItem[count] = new ObjectDrawerItem(R.drawable.ic_audio, file.getName());
                        break;
                    case "video":
                        drawerItem[count] = new ObjectDrawerItem(R.drawable.ic_video, file.getName());
                        break;
                    default:
                        Log.w(TAG, "Could not associate MIME-Type: " + mimeType);
                }
            }
            count++;
        }

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(rootView.getContext(), R.layout.listview_item_row, drawerItem);
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
        File chosenFile = fileHandler.allFiles[position];
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
                    text = "Noch keine Unterstützung für Textfiles";
                    duration = Toast.LENGTH_SHORT;
                    toast = Toast.makeText(context, text, duration);
                    toast.show();
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
            }
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();

        getActivity().getActionBar().setTitle("Home");
        MainActivity.setDrawerSelected(0);
    }
}
