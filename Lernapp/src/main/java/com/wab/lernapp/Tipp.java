package com.wab.lernapp;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Student on 01.06.2015.
 */
public class Tipp {
    String tipp;
    int photoId;

    Tipp(String tipp,  int photoId) {
        this.tipp = tipp;
        this.photoId = photoId;
    }


    private static List<Tipp> tipps;

    // This method creates an ArrayList that has three Person objects
// Checkout the project associated with this tutorial on Github if
// you want to use the same images.
    public static List<Tipp> initializeData(Activity activity) {
        tipps = new ArrayList<>();
        Resources res = activity.getResources();
        String[] stringTipps = res.getStringArray (R.array.Lerntipps);
        int i = 0;
        int[] p = {R.drawable.wut, R.drawable.erfolg,R.drawable.todo,R.drawable.wasser,R.drawable.zeit,R.drawable.zusammenfassung,R.drawable.pause,R.drawable.zeit2,R.drawable.spicker,R.drawable.schlaf,R.drawable.belohnung};
        while (stringTipps.length > i)
        {
            tipps.add(new Tipp(stringTipps[i], p[i]));
            i++;
        }
        return tipps;
    }
}