package com.wab.lernapp;

import android.app.Activity;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Student on 01.06.2015.
 */
public class Test {
    String frage;
    int photoId;

    Test(String frage, int photoId) {
        this.frage = frage;
        this.photoId = photoId;
    }


    private static List<Test> test;


    public static List<Test> initializeData(Activity activity) {
        test = new ArrayList<>();
        Resources res = activity.getResources();
        String[] stringFragen = res.getStringArray (R.array.Fragen);
        int i = 0;
        int[] p = {R.drawable.wut, R.drawable.erfolg,R.drawable.todo,R.drawable.wasser,R.drawable.zeit,R.drawable.zusammenfassung,R.drawable.pause,R.drawable.zeit2,R.drawable.spicker,R.drawable.schlaf,R.drawable.belohnung,R.drawable.kaugummi,R.drawable.bewegung,R.drawable.ziel,R.drawable.nocellphone,R.drawable.relax,R.drawable.laufen,R.drawable.ernaehrung,R.drawable.pause2,R.drawable.arbeit,R.drawable.kaugummi2};
        while (stringFragen.length > i)
        {
            test.add(new Test(stringFragen[i], p[i]));
            i++;
        }
        return test;
    }
}