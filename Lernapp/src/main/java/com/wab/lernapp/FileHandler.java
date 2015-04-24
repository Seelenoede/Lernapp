package com.wab.lernapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;

/**
 * Created by pmeyerbu on 23.04.2015.
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

    public void openPDF(File src, Context mContext)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(src), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        mContext.startActivity(intent);
    }

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
