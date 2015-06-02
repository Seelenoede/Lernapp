package com.wab.lernapp;

/**
 * Created by Student on 02.06.2015.
 */
public class SectionItemHome implements ItemHome {

    private final String title;

    public SectionItemHome(String title) {
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    @Override
    public boolean isSection() {
        return true;
    }

}
