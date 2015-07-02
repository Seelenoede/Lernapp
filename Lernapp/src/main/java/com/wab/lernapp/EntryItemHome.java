package com.wab.lernapp;

/**
 *
 * Created by Student on 02.06.2015.
 */
public class EntryItemHome implements ItemHome {

    public final String title;
    public final int icon;

    public EntryItemHome(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    @Override
    public boolean isSection() {
        return false;
    }

    @Override
    public boolean isCarItem() { return false; }
}