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

        DataPoint[] dataPoints = new DataPoint[24];
        for(int i=0; i<24; i++)
        {
            if(Variables.learnTimes[i] != 0)
            {
                dataPoints[i] = new DataPoint(i, (double) Variables.learnTimes[i] / Variables.learnTime * 100);
            }
            else
            {
                dataPoints[i] = new DataPoint(i, 0);
            }
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);

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
