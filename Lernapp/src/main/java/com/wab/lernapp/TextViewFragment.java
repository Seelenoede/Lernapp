package com.wab.lernapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * Created by Student on 14.04.2015.
 */
public class TextViewFragment extends Fragment {

    private View rootView;

    public TextViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_textview, container, false);
        openText(Variables.chosenFile);
        return rootView;
    }

    private void openText(File chosenFile)
    {

//Read text from file
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(chosenFile));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            //TODO: You'll need to add proper error handling here
        }

//Find the view by its id
        ScrollView scrollView_content = (ScrollView) rootView.findViewById(R.id.scrollView_content);
        TextView tv_content = (TextView)scrollView_content.findViewById(R.id.textView_content);


//Set the text
        tv_content.setText(text);
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        Variables.saveLearnTimeBoth();
    }
}
