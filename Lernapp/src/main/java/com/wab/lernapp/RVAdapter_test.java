package com.wab.lernapp;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import java.util.List;

import static android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * Created by Student on 01.06.2015.
 */
public class RVAdapter_test extends RecyclerView.Adapter<RVAdapter_test.TestViewHolder>{

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_lerntest, viewGroup, false);
        TestViewHolder pvh = new TestViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(TestViewHolder tippViewHolder, int i) {
        tippViewHolder.frage.setText(fragen.get(i).frage);
        tippViewHolder.frageImage.setImageResource(fragen.get(i).photoId);
    }

    @Override
    public int getItemCount() {
            return fragen.size();
    }

    List<Test> fragen;

    RVAdapter_test(List<Test> fragen){
        this.fragen = fragen;
    }

    public static class TestViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView frage;
        ImageView frageImage;

        TestViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv_test);
            frage = (TextView)itemView.findViewById(R.id.textView_question);
            frageImage = (ImageView)itemView.findViewById(R.id.lerntest_image);
        }
    }

}