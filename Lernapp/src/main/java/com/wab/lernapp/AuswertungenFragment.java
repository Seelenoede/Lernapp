package com.wab.lernapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by Student on 14.04.2015.
 */
public class AuswertungenFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String text;
        View rootView = inflater.inflate(R.layout.fragment_auswertungen, container, false);

        TextView textAuto = (TextView) rootView.findViewById(R.id.textAutozeit);
        text = Long.toString(Variables.autoTime);
        textAuto.setText(text + " Sekunden");

        TextView textLern = (TextView) rootView.findViewById(R.id.textLernzeit);
        text = Long.toString(Variables.learnTime);
        textLern.setText(text + " Sekunden");

        TextView textDurchschnitt = (TextView) rootView.findViewById(R.id.textDurchschnitt);
        text = Double.toString(Variables.averageGrade);
        textDurchschnitt.setText(text);

        doDrawZeit(rootView);

        //Only if done any tests
        doDrawTests(rootView);

        return rootView;
    }

    private void doDrawZeit(View view)
    {
        GraphView graph = (GraphView) view.findViewById(R.id.graphZeit);
        //Need counter for y Value
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6),
                new DataPoint(5, 1),
                new DataPoint(6, 1),
                new DataPoint(7, 1),
                new DataPoint(8, 1),
                new DataPoint(9, 1),
                new DataPoint(10, 1),
                new DataPoint(11, 1),
                new DataPoint(12, 1),
                new DataPoint(13, 1),
                new DataPoint(14, 1),
                new DataPoint(15, 1),
                new DataPoint(16, 1),
                new DataPoint(17, 1),
                new DataPoint(18, 1),
                new DataPoint(19, 1),
                new DataPoint(20, 1),
                new DataPoint(21, 1),
                new DataPoint(22, 1),
                new DataPoint(23, 1)
        });

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(23);

        /*
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(100) oder soweit es nicht den Graphen behindert;
         */

        graph.addSeries(series);
    }

    private void doDrawTests(View view)
    {
        DataPoint[] points = {new DataPoint(2,2), new DataPoint(3,6)};
        //points = new DataPoint[anzahlTests];
        //Load test results
        GraphView graph = (GraphView) view.findViewById(R.id.graphTests);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);
        graph.addSeries(series);
    }
}
