package com.ifmo.LinkedLearning.ui;

import android.app.ActionBar;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.requestmanager.RequestManager;
import com.ifmo.LinkedLearning.R;
import com.ifmo.LinkedLearning.model.Contract;
import com.ifmo.LinkedLearning.model.RequestFactory;
import com.ifmo.LinkedLearning.model.RestRequestManager;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

/**
 * Created with IntelliJ IDEA.
 * User: Федор
 * Date: 24.09.13
 * Time: 22:32
 * To change this template use File | Settings | File Templates.
 */



public class ModuleActivity extends FragmentActivity implements ActionBar.TabListener {

    CollectionPagerAdapter mCollectionPagerAdapter;
    ViewPager mViewPager;

    private String moduleURI;
    private String moduleName;
    private int moduleNumber;
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
        moduleURI = intent.getStringExtra(ModuleListActivity.MODULE_URI);
        moduleName = intent.getStringExtra(ModuleListActivity.MODULE_NAME);
        moduleNumber = intent.getIntExtra(ModuleListActivity.MODULE_NUMBER,0);

        setTitle(moduleName);

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
        actionBar.addTab(actionBar.newTab().setText("Лекции").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Тесты").setTabListener(tabListener));

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
                    fragment=new ModuleInfoFragment();
                    args = new Bundle();
                    args.putString(ModuleListActivity.MODULE_NAME, moduleName);
                    args.putInt(ModuleListActivity.MODULE_NUMBER, moduleNumber);
                    fragment.setArguments(args);
                    break;
                case 1:
                    fragment= new LectureListFragment();
                    args = new Bundle();
                    args.putString(ModuleListActivity.MODULE_URI, moduleURI);
                    fragment.setArguments(args);
                    break;
                case 2:
                    fragment=new TestsFragment();
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