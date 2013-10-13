package com.ifmo.LinkedLearning.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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



public class LectureListFragment extends ListFragment implements PullToRefreshAttacher.OnRefreshListener {

    private static final String[] PROJECTION = {
            Contract.Lecture._ID,
            Contract.Lecture.NAME,
            Contract.Lecture.URI,
            Contract.Lecture.NUMBER,
            Contract.Lecture.PARENT


    };

    private SimpleCursorAdapter adapter;
    private PullToRefreshAttacher mPullToRefreshAttacher;

    private RestRequestManager requestManager;

    private static final int LOADER_ID = 3;
    public static final String LECTURE_URI="com.ifmo.LinkedLearning.LECTURE_URI";

    private String moduleURI;

    private LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {

        @Override
        public Loader<Cursor> onCreateLoader(int loaderId, Bundle arg1) {
            return new CursorLoader(
                    LectureListFragment.this.getActivity(),
                    Contract.Lecture.CONTENT_URI,
                    PROJECTION,
                    arg1.getString("SELECTION"),
                    null,
                    Contract.Lecture.NUMBER+" ASC"
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
            Toast.makeText(LectureListFragment.this.getActivity(), R.string.server_not_found, Toast.LENGTH_SHORT).show();
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
    public void onViewCreated (View view, Bundle savedInstanceState){

        adapter = new SimpleCursorAdapter(this.getActivity(),
                R.layout.leacture_list_item,
                null,
                new String[]{ Contract.Modules.NAME },
                new int[]{ R.id.module_name },
                0);
        setListAdapter(adapter);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor c = ((SimpleCursorAdapter)adapterView.getAdapter()).getCursor();
                c.moveToPosition(i);
                String uri = c.getString(c.getColumnIndex(Contract.Modules.URI));
                Intent intent = new Intent(view.getContext(), TermListActivity.class);
                intent.putExtra(LECTURE_URI,uri);
                startActivity(intent);
            }
        });

        moduleURI=getArguments().getString(ModuleListActivity.MODULE_URI);
        Bundle selection = new Bundle();
        selection.putString("SELECTION",Contract.Lecture.PARENT+"='"+moduleURI+"'");
        getLoaderManager().initLoader(LOADER_ID, selection, loaderCallbacks);
        requestManager = RestRequestManager.from(this.getActivity());

//        mPullToRefreshAttacher = PullToRefreshAttacher.get(this.getActivity());
//        mPullToRefreshAttacher.addRefreshableView(getListView(), this);


        mPullToRefreshAttacher = ((ModuleActivity)this.getActivity()).getPullToRefreshAttacher();
        mPullToRefreshAttacher.clearRefreshableViews();
        mPullToRefreshAttacher.addRefreshableView(getListView(), this);
        //mPullToRefreshAttacher = ((MainActivity)this.getActivity()).getPullToRefreshAttacher();
        //mPullToRefreshAttacher.clearRefreshableViews();
        //mPullToRefreshAttacher.addRefreshableView(getListView(), this);
    }


    void update() {
        requestManager.execute(RequestFactory.getLectureRequest(moduleURI), requestListener);
    }

    @Override
    public void onRefreshStarted(View view) {
        update();
    }

}