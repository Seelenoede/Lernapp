package com.wab.lernapp;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
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

    private static final String TAG = "AuswertungFragment";

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

        rootView.findViewById(R.id.graphZeit).setVisibility(View.INVISIBLE);

        for(long i : Variables.learnTimes)
        {
            if (i != 0)
            {
                doDrawTime(rootView);
                rootView.findViewById(R.id.graphZeit).setVisibility(View.VISIBLE);
                break;
            }
        }


        if(Variables.gradeCount>0)
        {
            doDrawTests(rootView);
        }
        else
        {
            rootView.findViewById(R.id.graphTests).setVisibility(View.INVISIBLE);
        }

        return rootView;
    }

    /**
     * Draw the Graph for the learning times
     * TODO: compress graph now in the horizontal
     * @param view View to which the graph is drawn
     */
    private void doDrawTime(View view)
    {
        Log.d(TAG, "Zeichne Lernzeit-Graph");
        final GraphView graph = (GraphView) view.findViewById(R.id.graphZeit);

        int firstTime = -1;
        int lastTime = 0;

        DataPoint[] dataPoints = new DataPoint[24];
        for(int i=0; i<24; i++)
        {
            if(Variables.learnTimes[i] != 0)
            {
                dataPoints[i] = new DataPoint(i, (double) Variables.learnTimes[i] / Variables.learnTime * 100);

                if(firstTime == -1)
                {
                    firstTime = i;
                }
                lastTime = i;
            }
            else
            {
                dataPoints[i] = new DataPoint(i, 0);
            }
        }
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(dataPoints);

        //Set horizontal bounds to show whole graph
        // -1 and +1 are used as padding
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(firstTime - 1);
        graph.getViewport().setMaxX(lastTime + 1);

        //some more styling
        series.setSpacing(20);

        //TODO: in future try to get colorAccent
        switch(ThemeUtils.getCurrentTheme())
        {
            case ThemeUtils.GREEN:
                series.setColor(getResources().getColor(R.color.pink));
                break;
            case ThemeUtils.PURPLE:
                series.setColor(getResources().getColor(R.color.lightgelb));
                break;
            default:
                series.setColor(getResources().getColor(R.color.darkred));
        }

        //add values to graph
        graph.addSeries(series);

        //Format to show only integers
        final NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(0);
        nf.setMinimumIntegerDigits(1);
        nf.setMaximumIntegerDigits(3);

        final int padLeft = firstTime - 1;
        final int padRight = lastTime + 1;

        //customize label layout and remove first and last index
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    String label = nf.format(value);

                    //hide the -1 and 24 label
                    if((label.equals(String.valueOf(padLeft))) || (label.equals(String.valueOf(padRight))))
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

        //this is to ensure that the labels stand right below their bars
        graph.getGridLabelRenderer().setNumHorizontalLabels(padRight - padLeft + 1);

        //Show only horizontal grid lines
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
    }

    /**
     * Draw graph for all test results
     * @param view View to which the graph is drawn
     */
    private void doDrawTests(View view)
    {
        Log.d(TAG, "Zeichne Testergebnis-Graph");
        DataPoint[] points = new DataPoint[Variables.gradeCount];
        for(int i = 0; i<Variables.gradeCount; i++)
        {
            points[i] = new DataPoint(i + 1, Variables.allGrades[i]);
        }
        GraphView graph = (GraphView) view.findViewById(R.id.graphTests);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);

        //TODO: in future try to get colorAccent
        switch(ThemeUtils.getCurrentTheme())
        {
            case ThemeUtils.GREEN:
                series.setColor(getResources().getColor(R.color.pink));
                break;
            case ThemeUtils.PURPLE:
                series.setColor(getResources().getColor(R.color.lightgelb));
                break;
            default:
                series.setColor(getResources().getColor(R.color.darkred));
        }

        graph.addSeries(series);

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(0);

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(nf, nf));
    }

    @Override
    public void onResume()
    {
        super.onResume();

        getActivity().getActionBar().setTitle("Auswertungen");
        MainActivity.setDrawerSelected(2);
    }
}
