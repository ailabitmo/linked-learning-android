package com.ifmo.LinkedLearning.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
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



public class TermListActivity extends BaseActivity implements PullToRefreshAttacher.OnRefreshListener {

    private static final String[] PROJECTION = {
            Contract.Term._ID,
            Contract.Term.NAME,
            Contract.Term.URI,
            Contract.Term.PARENT


    };

    private ListView listView;
    private SimpleCursorAdapter adapter;
    private PullToRefreshAttacher mPullToRefreshAttacher;

    private RestRequestManager requestManager;

    private static final int LOADER_ID = 4;

    private String lectureURI;

    private LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {

        @Override
        public Loader<Cursor> onCreateLoader(int loaderId, Bundle arg1) {
            return new CursorLoader(
                    TermListActivity.this,
                    Contract.Term.CONTENT_URI,
                    PROJECTION,
                    arg1.getString("SELECTION"),
                    null,
                    Contract.Term.NAME+" ASC"
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
            Toast.makeText(TermListActivity.this, R.string.server_not_found, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learning_list);

        Intent intent = getIntent();
        lectureURI = intent.getStringExtra(LectureListActivity.LECTURE_URI);

        listView = (ListView)findViewById(R.id.listView);
        adapter = new SimpleCursorAdapter(this,
                R.layout.learning_list_item,
                null,
                new String[]{ Contract.Term.NAME },
                new int[]{ R.id.module_name },
                0);
        listView.setAdapter(adapter);

        Bundle selection = new Bundle();
        selection.putString("SELECTION",Contract.Term.PARENT+"='"+lectureURI+"'");
        getLoaderManager().initLoader(LOADER_ID, selection, loaderCallbacks);
        requestManager = RestRequestManager.from(this);

        mPullToRefreshAttacher = PullToRefreshAttacher.get(this);
        mPullToRefreshAttacher.addRefreshableView(listView, this);
    }

    void update() {
        requestManager.execute(RequestFactory.getTermRequest(), requestListener);
    }

    @Override
    public void onRefreshStarted(View view) {
        update();
    }
}