package com.wab.lernapp;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 *
 * Created by Student on 01.06.2015.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.TippViewHolder>{

    @Override
    public TippViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_lerntipps, viewGroup, false);
        return new TippViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TippViewHolder tippViewHolder, int i) {
        tippViewHolder.lerntipp.setText(lerntipps.get(i).tipp);
        tippViewHolder.lerntippImage.setImageResource(lerntipps.get(i).photoId);
    }

    @Override
    public int getItemCount() {
            return lerntipps.size();
    }

    List<Tipp> lerntipps;

    RVAdapter(List<Tipp> lerntipps){
        this.lerntipps = lerntipps;
    }

    public static class TippViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView lerntipp;
        ImageView lerntippImage;

        TippViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            lerntipp = (TextView)itemView.findViewById(R.id.lerntipp_textView);
            lerntippImage = (ImageView)itemView.findViewById(R.id.lerntipp_image);
        }
    }

}