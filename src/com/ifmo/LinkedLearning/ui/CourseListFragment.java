package com.ifmo.LinkedLearning.ui;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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



public class CourseListFragment extends ListFragment implements PullToRefreshAttacher.OnRefreshListener {

    private static final String[] PROJECTION = {
            Contract.Course._ID,
            Contract.Course.NAME,
            Contract.Course.URI
    };

    private SimpleCursorAdapter adapter;

    private PullToRefreshAttacher mPullToRefreshAttacher;
    private RestRequestManager requestManager;

    private static final int LOADER_ID = 2;
    public static final String COURSE_URI="com.ifmo.LinkedLearning.COURSE_URI";

    private LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {

        @Override
        public Loader<Cursor> onCreateLoader(int loaderId, Bundle arg1) {
            return new CursorLoader(
                    CourseListFragment.this.getActivity(),
                    Contract.Course.CONTENT_URI,
                    PROJECTION,
                    null,
                    null,
                    null
            );
        }

        @Override
        public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
            adapter.swapCursor(cursor);
            if (cursor.getCount() == 0) {
                update();
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> arg0) {
            adapter.swapCursor(null);
        }
    };

    RequestManager.RequestListener requestListener = new RequestManager.RequestListener() {

        @Override
        public void onRequestFinished(Request request, Bundle resultData) {
            mPullToRefreshAttacher.setRefreshComplete();
        }

        void showError() {
            mPullToRefreshAttacher.setRefreshComplete();
            Toast.makeText(CourseListFragment.this.getActivity(), R.string.server_not_found, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestDataError(Request request) {
            showError();
        }

        @Override
        public void onRequestCustomError(Request request, Bundle resultData) {
            showError();
        }

        @Override
        public void onRequestConnectionError(Request request, int statusCode) {
            showError();
        }
    };


    public void onViewCreated (View view, Bundle savedInstanceState){
        adapter = new SimpleCursorAdapter(this.getActivity(),
                R.layout.learning_list_item,
                null,
                new String[]{ Contract.Course.NAME },
                new int[]{ R.id.module_name },
                0);
        setListAdapter(adapter);

        getLoaderManager().initLoader(LOADER_ID, null,  loaderCallbacks);
        requestManager = RestRequestManager.from(this.getActivity());

        //mPullToRefreshAttacher = PullToRefreshAttacher.get(this.getActivity());
        mPullToRefreshAttacher = ((MainActivity)this.getActivity()).getPullToRefreshAttacher();
        mPullToRefreshAttacher.clearRefreshableViews();
        mPullToRefreshAttacher.addRefreshableView(getListView(), this);
    }



    void update() {
        requestManager.execute(RequestFactory.getCourseRequest(), requestListener);
    }

    @Override
    public void onRefreshStarted(View view) {
        update();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Cursor c = ((SimpleCursorAdapter) getListAdapter()).getCursor();
        c.moveToPosition(position);
        String uri = c.getString(c.getColumnIndex(Contract.Course.URI));
        Intent intent = new Intent(l.getContext(), ModuleListActivity.class);
        intent.putExtra(COURSE_URI, uri);
        startActivity(intent);    }

}