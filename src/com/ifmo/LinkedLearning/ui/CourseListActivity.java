package com.ifmo.LinkedLearning.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
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



public class CourseListActivity extends Activity implements PullToRefreshAttacher.OnRefreshListener {

    private static final String[] PROJECTION = {
            Contract.Course._ID,
            Contract.Course.NAME,
            Contract.Course.URI
    };

    private ListView listView;
    private SimpleCursorAdapter adapter;
    private PullToRefreshAttacher mPullToRefreshAttacher;

    private RestRequestManager requestManager;

    private static final int LOADER_ID = 2;
    public static final String COURSE_URI="com.ifmo.LinkedLearning.COURSE_URI";

    private LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {

        @Override
        public Loader<Cursor> onCreateLoader(int loaderId, Bundle arg1) {
            return new CursorLoader(
                    CourseListActivity.this,
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
            AlertDialog.Builder builder = new AlertDialog.Builder(CourseListActivity.this);
            builder.
                    setTitle(android.R.string.dialog_alert_title).
                    setMessage("Error data").
                    create().
                    show();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learning_list);

        listView = (ListView)findViewById(R.id.listView);
        adapter = new SimpleCursorAdapter(this,
                R.layout.learning_list_item,
                null,
                new String[]{ Contract.Course.NAME },
                new int[]{ R.id.module_name },
                0);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor c = ((SimpleCursorAdapter)adapterView.getAdapter()).getCursor();
                c.moveToPosition(i);
                String uri = c.getString(c.getColumnIndex(Contract.Course.URI));
                Intent intent = new Intent(view.getContext(), ModuleListActivity.class);
                intent.putExtra(COURSE_URI,uri);
                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(LOADER_ID, null, loaderCallbacks);
        requestManager = RestRequestManager.from(this);

        mPullToRefreshAttacher = PullToRefreshAttacher.get(this);
        mPullToRefreshAttacher.addRefreshableView(listView, this);
    }

    void update() {
        requestManager.execute(RequestFactory.getCourseRequest(), requestListener);
    }

    @Override
    public void onRefreshStarted(View view) {
        update();
    }
}