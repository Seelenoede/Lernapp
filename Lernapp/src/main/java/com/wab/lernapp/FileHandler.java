package com.wab.lernapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by pmeyerbu on 23.04.2015.
 */
public class FileHandler
{
    private static final String TAG = "FileHandler";

    public static File[] getAllFiles()
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
            }
        }
        else
        {
            allFiles = storage.listFiles();
        }
        return allFiles;
    }

    public static void openPDF(File src, Context mContext)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(src), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        mContext.startActivity(intent);
    }

}
