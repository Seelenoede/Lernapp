package com.wab.lernapp;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EntryAdapterHome extends ArrayAdapter<ItemHome> {

    private Context context;
    private ArrayList<ItemHome> items;
    private LayoutInflater vi;

    public EntryAdapterHome(Context context, ArrayList<ItemHome> items) {
        super(context,0, items);
        this.context = context;
        this.items = items;
        vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        final ItemHome i = items.get(position);
        if (i != null) {
            if(i.isSection()){
                SectionItemHome si = (SectionItemHome)i;
                v = vi.inflate(R.layout.list_item_section, null);

                v.setOnClickListener(null);
                v.setOnLongClickListener(null);
                v.setLongClickable(false);

                final TextView sectionView = (TextView) v.findViewById(R.id.list_item_section_text);
                sectionView.setText(si.getTitle());

            }else{
                EntryItemHome ei = (EntryItemHome)i;
                v = vi.inflate(R.layout.list_item_entry, null);
                final ImageView drawable = (ImageView)v.findViewById(R.id.list_item_entry_drawable);
                final TextView title = (TextView)v.findViewById(R.id.list_item_entry_name);


                if (title != null)
                    title.setText(ei.title);
                if (drawable != null)
                    drawable.setImageResource(ei.icon);
            }
        }
        return v;
    }

}