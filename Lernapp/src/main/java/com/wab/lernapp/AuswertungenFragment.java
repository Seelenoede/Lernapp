package com.wab.lernapp;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.NumberFormat;

/**
 * Created by Student on 14.04.2015.
 *
 * In this class the values from Variables are shown
 */
public class AuswertungenFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String text;
        View rootView = inflater.inflate(R.layout.fragment_auswertungen, container, false);

        TextView textAuto = (TextView) rootView.findViewById(R.id.textAutozeit);
        text = Long.toString(Variables.carTime);
        textAuto.setText(text + " Sekunden");

        TextView textLern = (TextView) rootView.findViewById(R.id.textLernzeit);
        text = Long.toString(Variables.learnTime);
        textLern.setText(text + " Sekunden");

        TextView textDurchschnitt = (TextView) rootView.findViewById(R.id.textDurchschnitt);
        text = Double.toString(Variables.averageGrade);
        textDurchschnitt.setText(text);

        doDrawTime(rootView);

        //Only if done any tests
        doDrawTests(rootView);

        return rootView;
    }

    /**
     * Draw the Graph for the learning times
     * @param view View to which the graph is drawn
     */
    private void doDrawTime(View view)
    {
        final GraphView graph = (GraphView) view.findViewById(R.id.graphZeit);

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
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(dataPoints);

        //Set horizontal bounds to show whole graph
        // -1 and 24 are used as padding
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(-1);
        graph.getViewport().setMaxX(24);

        //some more styling
        series.setSpacing(20);
        //TODO: series.setColor(enter Color here);

        //add values to graph
        graph.addSeries(series);

        //Format to show only integers
        final NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(0);
        nf.setMinimumIntegerDigits(1);
        nf.setMaximumIntegerDigits(3);

        //customize label layout
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    String label = nf.format(value);

                    //hide the -1 and 24 label
                    if((label.equals("-1")) || (label.equals("24")))
                    {
                        return "";
                    }
                    else
                    {
                        //show only integers
                        return nf.format(value);
                    }
                }
                else
                {
                    //show only integers
                    return nf.format(value);
                }
            }
        });

        //Show 26 labels (also includes the label for -1 and 24)
        graph.getGridLabelRenderer().setNumHorizontalLabels(26);

        //Show only horizontal grid lines
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
    }

    /**
     * Draw graph for all test results
     * @param view View to which the graph is drawn
     */
    private void doDrawTests(View view)
    {
        DataPoint[] points = new DataPoint[Variables.gradeCount];
        for(int i = 0; i<Variables.gradeCount; i++)
        {
            points[i] = new DataPoint(i + 1, Variables.allGrades[i]);
        }
        GraphView graph = (GraphView) view.findViewById(R.id.graphTests);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);
        graph.addSeries(series);

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(0);

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(nf, nf));
    }
}
