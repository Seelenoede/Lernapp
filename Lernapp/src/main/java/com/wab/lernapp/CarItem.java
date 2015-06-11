package com.wab.lernapp;

public class CarItem implements ItemHome {

    public final String title;
    public final int icon;

    public CarItem(String title, int icon)
    {
        this.title = title;
        this.icon = icon;
    }

    @Override
    public boolean isSection() {
        return false;
    }

    @Override
    public boolean isCarItem() {
        return true;
    }
}