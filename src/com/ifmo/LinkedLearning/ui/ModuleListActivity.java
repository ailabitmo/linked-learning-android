package com.ifmo.LinkedLearning.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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



public class ModuleListActivity extends BaseActivity implements PullToRefreshAttacher.OnRefreshListener {

    public static final String MODULE_URI="com.ifmo.LinkedLearning.MODULE_URI";
    public static final String MODULE_NAME="com.ifmo.LinkedLearning.MODULE_NAME";
    public static final String MODULE_NUMBER="com.ifmo.LinkedLearning.MODULE_NUMBER";


    private static final String[] PROJECTION = {
            Contract.Modules._ID,
            Contract.Modules.NAME,
            Contract.Modules.URI,
            Contract.Modules.NUMBER,
            Contract.Modules.PARENT

    };

    private ListView listView;
    private SimpleCursorAdapter adapter;
    private PullToRefreshAttacher mPullToRefreshAttacher;
    private RestRequestManager requestManager;

    private static final int LOADER_ID = 1;
    private String courseURI;

    private LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {

        @Override
        public Loader<Cursor> onCreateLoader(int loaderId, Bundle arg1) {
            return new CursorLoader(
                    ModuleListActivity.this,
                    Contract.Modules.CONTENT_URI,
                    PROJECTION,
                    arg1.getString("SELECTION"),
                    null,
                    Contract.Modules.NUMBER+" ASC"
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
            Toast.makeText(ModuleListActivity.this, R.string.server_not_found, Toast.LENGTH_SHORT).show();
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
        courseURI = intent.getStringExtra(CourseListActivity.COURSE_URI);

        listView = (ListView)findViewById(R.id.listView);
        adapter = new SimpleCursorAdapter(this,
                R.layout.learning_list_item,
                null,
                new String[]{ Contract.Modules.NAME },
                new int[]{ R.id.module_name },
                0);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor c = ((SimpleCursorAdapter)adapterView.getAdapter()).getCursor();
                c.moveToPosition(i);
                String uri = c.getString(c.getColumnIndex(Contract.Modules.URI));
                String name = c.getString(c.getColumnIndex(Contract.Modules.NAME));
                int number = c.getInt(c.getColumnIndex(Contract.Modules.NUMBER));

                //Intent intent = new Intent(view.getContext(), LectureListActivity.class);
                Intent intent = new Intent(view.getContext(), ModuleActivity.class);
                intent.putExtra(MODULE_URI,uri);
                intent.putExtra(MODULE_NAME,name);
                intent.putExtra(MODULE_NUMBER,number);

                startActivity(intent);
            }
        });

        Bundle selection = new Bundle();
        selection.putString("SELECTION",Contract.Lecture.PARENT+"='"+courseURI+"'");
        getLoaderManager().initLoader(LOADER_ID, selection, loaderCallbacks);
        requestManager = RestRequestManager.from(this);

        mPullToRefreshAttacher = PullToRefreshAttacher.get(this);
        mPullToRefreshAttacher.addRefreshableView(listView, this);
    }

    void update() {
        requestManager.execute(RequestFactory.getModuleRequest(courseURI), requestListener);
    }

    @Override
    public void onRefreshStarted(View view) {
        update();
    }



}