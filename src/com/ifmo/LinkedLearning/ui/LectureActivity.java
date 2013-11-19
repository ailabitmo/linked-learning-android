package com.ifmo.LinkedLearning.ui;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.ifmo.LinkedLearning.R;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

/**
 * Created with IntelliJ IDEA.
 * User: Федор
 * Date: 24.09.13
 * Time: 22:32
 * To change this template use File | Settings | File Templates.
 */



public class LectureActivity extends FragmentActivity implements ActionBar.TabListener {

    CollectionPagerAdapter mCollectionPagerAdapter;
    ViewPager mViewPager;

    private String lectureURI;
    private String lectureName;
    private String lectureVideoId;
    private int lectureNumber;
    private PullToRefreshAttacher mPullToRefreshAttacher;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.module_activity);

        Intent intent = getIntent();
        lectureURI = intent.getStringExtra(LectureListFragment.LECTURE_URI);
        lectureName = intent.getStringExtra(LectureListFragment.LECTURE_NAME);
        lectureNumber = intent.getIntExtra(LectureListFragment.LECTURE_NUMBER,0);
        lectureVideoId= intent.getStringExtra(LectureListFragment.LECTURE_VIDEO_ID);
        setTitle(lectureName);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mCollectionPagerAdapter =
                new CollectionPagerAdapter(
                        getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mCollectionPagerAdapter);

        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        getActionBar().setSelectedNavigationItem(position);
                    }
                });

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        };

        // Add 3 tabs, specifying the tab's text and TabListener
        actionBar.addTab(actionBar.newTab().setText("Инфо").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Термины").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Видео").setTabListener(tabListener));

        mPullToRefreshAttacher = PullToRefreshAttacher.get(this);

    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public class CollectionPagerAdapter extends FragmentPagerAdapter {
        public CollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment=null;
            Bundle args = null;
            switch(i){
                case 0:
                    fragment=new LectureInfoFragment();
                    args = new Bundle();
                    args.putString(LectureListFragment.LECTURE_NAME, lectureName);
                    args.putInt(LectureListFragment.LECTURE_NUMBER, lectureNumber);
                    fragment.setArguments(args);
                    break;
                case 1:
                    fragment= new TermListFragment();
                    args = new Bundle();
                    args.putString(LectureListFragment.LECTURE_URI, lectureURI);
                    fragment.setArguments(args);
                    break;
                case 2:
                    if(lectureVideoId.isEmpty()){
                        fragment=new TestsFragment();
                    }else{
                        fragment=new VideoListFragment();
                        args = new Bundle();
                        args.putString(LectureListFragment.LECTURE_VIDEO_ID, lectureVideoId);
                        fragment.setArguments(args);
                    }
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
    }

    public PullToRefreshAttacher getPullToRefreshAttacher(){
        return mPullToRefreshAttacher;
    }
}