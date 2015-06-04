package com.wab.lernapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pmeyerbu on 23.04.2015.
 *
 * In this class files are get from phone and are opened
 */
public class FileHandler
{
    private static final String TAG = "FileHandler";

    public ArrayList<File[]> fileList;
    public HashMap<File, String> fileTypes;

    public FileHandler()
    {
        fileTypes = new HashMap<>();
        fileList = new ArrayList<>();
        fileList = getAllFiles();
        for(int i=1; i<fileList.size();i++)
        {
            File[] files = fileList.get(i);
            for(File file : files)
            {
                String type = getMimeType(file);
                if (type != null)
                {
                    fileTypes.put(file, type);
                }
                else if (file.isDirectory())
                {
                    fileTypes.put(file, "folder/directory");
                }
                else
                {
                    Log.w(TAG, "Could not find MimeType of File: " + file.getName());
                    fileTypes.put(file, "other/other");
                }
            }
        }
    }

    /**
     * get all files that are stored in /sdcard/Lernapp/
     * @return files from /sdcard/Lernapp/
     */
    private static ArrayList<File[]> getAllFiles()
    {
        //auf S5 interner Speicher, bei Nexus 5 keine Ahnung
        File storage = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Lernapp");
        File[] allFiles = new File[0];
        ArrayList<File[]> fileList = new ArrayList<>();

        if (!storage.exists())
        {
            boolean success = storage.mkdirs();
            if (success)
            {
                Log.d(TAG, "Directory Created");
                allFiles = storage.listFiles();
                Log.v(TAG, "read all Files");

                int count = 0;
                ArrayList<File> directories = new ArrayList<>();
                for(File file : allFiles)
                {
                    if (file.isDirectory())
                        directories.add(file);
                    else
                        count++;
                }
                File[] tmpfiles = new File[directories.size()];
                for(int i=0; i<directories.size();i++)
                {
                    tmpfiles[i] = directories.get(i);
                }
                fileList.add(tmpfiles);

                for(File file : allFiles)
                {
                    if(file.isDirectory())
                    {
                        fileList.add(file.listFiles());
                    }
                }

                File[] otherFiles = new File[count];
                count = 0;
                for(File file : allFiles) {
                    if (!file.isDirectory()) {
                        otherFiles[count] = file;
                    }
                }
                fileList.add(otherFiles);
            }
            else
            {
                Log.e(TAG, "Could not create folder 'Lernapp'");
                fileList.add(allFiles);
            }
        }
        else
        {
            allFiles = storage.listFiles();
            Log.v(TAG, "read all Files");
            //The following is to ensure that no directories are in fileList
            int count = 0;
            ArrayList<File> directories = new ArrayList<>();
            for(File file : allFiles)
            {
                if (file.isDirectory())
                    directories.add(file);
                else
                    count++;
            }
            File[] tmpfiles = new File[directories.size()];
            for(int i=0; i<directories.size();i++)
            {
                tmpfiles[i] = directories.get(i);
            }
            fileList.add(tmpfiles);
            
            for(File file : allFiles)
            {
                if(file.isDirectory())
                {
                    fileList.add(file.listFiles());
                }
            }

            File[] otherFiles = new File[count];
            count = 0;
            for(File file : allFiles) {
                if (!file.isDirectory()) {
                    otherFiles[count] = file;
                    count++;
                }
            }
            fileList.add(otherFiles);
        }
        return fileList;
    }

    /**
     * Opens PDF file using an Intent
     * @param src Source file
     * @param activity Activity from caller
     */
    public void openPDF(File src, Activity activity)
    {
        Log.d(TAG, "Open PDF");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(src), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        //If there is no app installed that can open PDF files an exception is thrown
        try
        {
            activity.startActivityForResult(intent, MainActivity.LEARNTIME_REQUEST);
        }
        catch(ActivityNotFoundException e)
        {
            Toast toast = Toast.makeText(activity.getBaseContext(), "Keine App für PDF vorhanden", Toast.LENGTH_SHORT);
            toast.show();
            Log.e(TAG, "Keine App zum Anzeigen von PDFs vorhanden.");
        }
    }

    /**
     * Opens a video file
     *
     * @param src Input file
     * @param baseActivity ö
     */
    public void openVideo(File src, Activity baseActivity)
    {
        Log.d(TAG, "Open Video");

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(src), "video/*");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        try
        {
            baseActivity.startActivityForResult(intent, MainActivity.LEARNTIME_REQUEST);
        }
        catch(ActivityNotFoundException e)
        {
            Toast toast = Toast.makeText(baseActivity.getBaseContext(), "Keine App für Videos vorhanden", Toast.LENGTH_SHORT);
            toast.show();
            Log.e(TAG, "Keine App zum Anzeigen von Videos vorhanden.");
        }

//        Fragment fragment = VideoFragment.newInstance(src);
//
//        FragmentManager fragmentManager = baseActivity.getFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    /**
     * Opens audio files
     * @param src Input file
     * @param baseActivity base activity
     */
    public void openAudio(File src, Activity baseActivity)
    {
        Log.d(TAG, "Open Audio");

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(src), "audio/*");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        try
        {
            baseActivity.startActivityForResult(intent, MainActivity.LEARNTIME_REQUEST);
        }
        catch(ActivityNotFoundException e)
        {
            Toast toast = Toast.makeText(baseActivity.getBaseContext(), "Keine App für Audio vorhanden", Toast.LENGTH_SHORT);
            toast.show();
            Log.e(TAG, "Keine App zum Abspielen von Audiodateien vorhanden.");
        }
    }

    /**
     * Get Mime Type from file
     * @param file Input File
     * @return MimeType
     */
    private static String getMimeType(File file)
    {
        String type = null;
        String extension = null;
        try
        {
            extension = MimeTypeMap.getFileExtensionFromUrl(file.toURI().toURL().toString());
        }
        catch (MalformedURLException e)
        {
            Log.e(TAG, "Problem mit dem Datei-Pfad");
        }
        if(extension != null)
        {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension);
        }

        return type;
    }
}
