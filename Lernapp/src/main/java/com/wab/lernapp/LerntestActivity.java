/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wab.lernapp;

import com.wab.lernapp.wizard.model.AbstractWizardModel;
import com.wab.lernapp.wizard.model.ModelCallbacks;
import com.wab.lernapp.wizard.model.Page;
import com.wab.lernapp.wizard.ui.PageFragmentCallbacks;
import com.wab.lernapp.wizard.ui.ReviewFragment;
import com.wab.lernapp.wizard.ui.StepPagerStrip;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LerntestActivity extends FragmentActivity implements
        PageFragmentCallbacks,
        ReviewFragment.Callbacks,
        ModelCallbacks {
    private ViewPager mPager;
    private MyPagerAdapter mPagerAdapter;

    private boolean mEditingAfterReview;

    private AbstractWizardModel mWizardModel = new TestModel(this);

    private boolean mConsumePageSelectedEvent;

    private Button mNextButton;
    private Button mPrevButton;

    private List<Page> mCurrentPageSequence;
    private StepPagerStrip mStepPagerStrip;

    private Activity activity;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtils.onActivityCreateSetTheme(this, getThemeNumber());
        setContentView(R.layout.activity_test);
        activity = this;

        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }

        mWizardModel.registerListener(this);

        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);
        mStepPagerStrip = (StepPagerStrip) findViewById(R.id.strip);
        mStepPagerStrip.setOnPageSelectedListener(new StepPagerStrip.OnPageSelectedListener() {
            @Override
            public void onPageStripSelected(int position) {
                position = Math.min(mPagerAdapter.getCount() - 1, position);
                if (mPager.getCurrentItem() != position) {
                    mPager.setCurrentItem(position);
                }
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mPrevButton = (Button) findViewById(R.id.prev_button);

        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mStepPagerStrip.setCurrentPage(position);

                if (mConsumePageSelectedEvent) {
                    mConsumePageSelectedEvent = false;
                    return;
                }

                mEditingAfterReview = false;
                updateBottomBar();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPager.getCurrentItem() == mCurrentPageSequence.size()) {
                    ArrayList<Integer> resultList = new ArrayList<>();
                    //was passiert, wenn der Test abgeschlossen wird
                    for(Page page : mCurrentPageSequence)
                    {
                        Bundle pageData = page.getData();
                        String result = pageData.getString("_");

                        //wandel Ergebnis in Zahl um
                        switch (result)
                        {
                            case "Trifft voll und ganz zu":
                                resultList.add(3);
                                break;
                            case "Trifft zu":
                                resultList.add(2);
                                break;
                            case "Trifft nicht zu":
                                resultList.add(1);
                                break;
                            case "Trifft überhaupt nicht zu":
                                resultList.add(0);
                                break;
                            default:
                                resultList.add(0);
                                Log.e("Lerntest", "Ergebnis wurde nicht erkannt");
                        }
                    }
                    //Berechne Ergebnis
                    double earmindedPerc = 0;
                    for(int i=6; i<12; i++)
                    {
                        int result = resultList.get(i);
                        earmindedPerc += result;
                    }
                    earmindedPerc = (earmindedPerc/18)*100;

                    Variables.filterOptions[0] = earmindedPerc >= 50;

                    double eyemindedPerc = 0;
                    for(int i=0; i<6; i++)
                    {
                        int result = resultList.get(i);
                        eyemindedPerc += result;
                    }
                    eyemindedPerc = (eyemindedPerc/18)*100;

                    Variables.filterOptions[1] = eyemindedPerc >= 50;

                    if((eyemindedPerc < 50) && (earmindedPerc < 50))
                    {
                        Variables.filterOptions[0] = true;
                        Variables.filterOptions[1] = true;
                    }

                    //speichere Lerntyp
                    Variables.saveDidacticType();

                    activity.finish();

                }
                else {
                    if (mEditingAfterReview) {
                        mPager.setCurrentItem(mPagerAdapter.getCount() - 1);
                    }
                    else {
                        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                    }
                }
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
            }
        });

        onPageTreeChanged();
        updateBottomBar();
        firstTime();
    }

    private void firstTime() {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        HashSet<String> defaultTypes = new HashSet<>();
        defaultTypes.add("leer");
        defaultTypes.add("leer");
        Set<String> didacticTypes = SP.getStringSet("preference_didactic_type", defaultTypes);
        if (didacticTypes.contains("leer")) {
            System.out.println("in if");
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LerntestActivity.this);

            // set title
            alertDialogBuilder.setTitle("Dein Lerntyp");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Um die Inhalte auf dich zuzuschneiden möchten wir einen Lerntypentest durchführen.")
                    .setCancelable(false)
                    .setPositiveButton("Los geht's",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            System.out.println("in yes");
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("Überspringen",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            System.out.println("in if");
                            LerntestActivity.this.finish();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }
        else
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    LerntestActivity.this);

            // set title
            alertDialogBuilder.setTitle("Dein Lerntyp");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Möchtest du den Test nochmal machen und den aktuellen Lerntyp überschreiben?")
                    .setCancelable(false)
                    .setPositiveButton("Klar",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("Lieber nicht",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            LerntestActivity.this.finish();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }
    }
    private int getThemeNumber() {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String strFarbe = SP.getString("preference_appearance","@string/default_style_value");
        if (strFarbe.equals("Grün"))
        {
            return ThemeUtils.GREEN;
        }
        else if (strFarbe.equals("Orange"))
        {
            return ThemeUtils.ORANGE;
        }
        else if (strFarbe.equals("Gelb"))
        {
            return ThemeUtils.YELLOW;
        }
        else
        {
            return ThemeUtils.GREEN;
        }
    }

    @Override
    public void onPageTreeChanged() {
        mCurrentPageSequence = mWizardModel.getCurrentPageSequence();
        recalculateCutOffPage();
        mStepPagerStrip.setPageCount(mCurrentPageSequence.size() + 1); // + 1 = review step
        mPagerAdapter.notifyDataSetChanged();
        updateBottomBar();
    }

    private void updateBottomBar() {
        int position = mPager.getCurrentItem();
        if (position == mCurrentPageSequence.size()) {
            mNextButton.setText(R.string.finish);
            mNextButton.setBackgroundResource(R.drawable.finish_background);
            mNextButton.setTextAppearance(this, R.style.TextAppearanceFinish);
        } else {
            mNextButton.setText(mEditingAfterReview
                    ? R.string.review
                    : R.string.next);
            mNextButton.setBackgroundResource(R.drawable.selectable_item_background);
            TypedValue v = new TypedValue();
            getTheme().resolveAttribute(android.R.attr.textAppearanceMedium, v, true);
            mNextButton.setTextAppearance(this, v.resourceId);
            mNextButton.setEnabled(position != mPagerAdapter.getCutOffPage());
        }

        mPrevButton.setVisibility(position <= 0 ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWizardModel.unregisterListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle("model", mWizardModel.save());
    }

    @Override
    public AbstractWizardModel onGetModel() {
        return mWizardModel;
    }

    @Override
    public void onEditScreenAfterReview(String key) {
        for (int i = mCurrentPageSequence.size() - 1; i >= 0; i--) {
            if (mCurrentPageSequence.get(i).getKey().equals(key)) {
                mConsumePageSelectedEvent = true;
                mEditingAfterReview = true;
                mPager.setCurrentItem(i);
                updateBottomBar();
                break;
            }
        }
    }

    @Override
    public void onPageDataChanged(Page page) {
        if (page.isRequired()) {
            if (recalculateCutOffPage()) {
                mPagerAdapter.notifyDataSetChanged();
                updateBottomBar();
            }
        }
    }

    @Override
    public Page onGetPage(String key) {
        return mWizardModel.findByKey(key);
    }

    private boolean recalculateCutOffPage() {
        // Cut off the pager adapter at first required page that isn't completed
        int cutOffPage = mCurrentPageSequence.size() + 1;
        for (int i = 0; i < mCurrentPageSequence.size(); i++) {
            Page page = mCurrentPageSequence.get(i);
            if (page.isRequired() && !page.isCompleted()) {
                cutOffPage = i;
                break;
            }
        }

        if (mPagerAdapter.getCutOffPage() != cutOffPage) {
            mPagerAdapter.setCutOffPage(cutOffPage);
            return true;
        }

        return false;
    }

    public class MyPagerAdapter extends FragmentStatePagerAdapter {
        private int mCutOffPage;
        private Fragment mPrimaryItem;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            if (i >= mCurrentPageSequence.size()) {
                return new ReviewFragment();
            }

            return mCurrentPageSequence.get(i).createFragment();
        }

        @Override
        public int getItemPosition(Object object) {
            // TODO: be smarter about this
            if (object == mPrimaryItem) {
                // Re-use the current fragment (its position never changes)
                return POSITION_UNCHANGED;
            }

            return POSITION_NONE;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            mPrimaryItem = (Fragment) object;
        }

        @Override
        public int getCount() {
            if (mCurrentPageSequence == null) {
                return 0;
            }
            return Math.min(mCutOffPage + 1, mCurrentPageSequence.size() + 1);
        }

        public void setCutOffPage(int cutOffPage) {
            if (cutOffPage < 0) {
                cutOffPage = Integer.MAX_VALUE;
            }
            mCutOffPage = cutOffPage;
        }

        public int getCutOffPage() {
            return mCutOffPage;
        }
    }
}
