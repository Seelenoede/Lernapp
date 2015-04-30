package com.wab.lernapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.io.File;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by pmeyerbu on 23.04.2015.
 *
 * In this class files are get from phone and are opened
 */
public class FileHandler
{
    private static final String TAG = "FileHandler";

    public File[] allFiles;
    public HashMap<File, String> fileTypes;

    public FileHandler()
    {
        fileTypes = new HashMap<>();
        allFiles = getAllFiles();
        for(File file : allFiles)
        {
            String type = getMimeType(file);
            if(type != null)
            {
                fileTypes.put(file, type);
            }
            else
            {
                Log.w(TAG, "Could not find MimeType of File: " + file.getName());
            }
        }
    }

    /**
     * get all files that are stored in /sdcard/Lernapp/
     * @return files from /sdcard/Lernapp/
     */
    private static File[] getAllFiles()
    {
        //auf S5 interner Speicher, bei Nexus 5 keine Ahnung
        File storage = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Lernapp");
        File[] allFiles = new File[0];

        if (!storage.exists())
        {
            boolean success = storage.mkdirs();
            if (success)
            {
                Log.d(TAG, "Directory Created");
                allFiles = storage.listFiles();
                Log.v(TAG, "read all Files");
            }
            else
            {
                Log.e(TAG, "Could not create folder 'Lernapp'");
            }
        }
        else
        {
            allFiles = storage.listFiles();
            Log.v(TAG, "read all Files");
        }
        return allFiles;
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

        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        Variables.startDateTmpH = Integer.parseInt(sdf.format(new Date()));
        sdf = new SimpleDateFormat("mm:ss");
        Variables.startDateTmp = sdf.format(new Date());
        Variables.startTimeTmp = System.nanoTime();

        //If there is no app installed that can open PDF files an exception is thrown
        try
        {
            activity.startActivityForResult(intent, 0);
        }
        catch(ActivityNotFoundException e)
        {
            Toast toast = Toast.makeText(activity.getBaseContext(), "Keine App für PDF vorhanden", Toast.LENGTH_SHORT);
            toast.show();
            Log.e(TAG, "Keine App zum Anzeigen von PDFs vorhanden.");
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
