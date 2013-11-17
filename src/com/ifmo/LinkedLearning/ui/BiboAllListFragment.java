package com.ifmo.LinkedLearning.ui;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
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



public class BiboAllListFragment extends ListFragment implements PullToRefreshAttacher.OnRefreshListener {

    private static final String[] PROJECTION = {
            Contract.Bibo._ID,
            Contract.Bibo.URI,
            Contract.Bibo.NAME,
            Contract.Bibo.AUTHOR_LIST,
            Contract.Bibo.PUBLICATION,
            Contract.Bibo.PDF
    };

    private SimpleCursorAdapter adapter;
    private PullToRefreshAttacher mPullToRefreshAttacher;

    private RestRequestManager requestManager;

    private static final int LOADER_ID = 432;
    public static final String BIBO_URI="com.ifmo.LinkedLearning.BIBO_URI";
    public static final String BIBO_NAME="com.ifmo.LinkedLearning.BIBO_NAME";
    public static final String BIBO_AUTHOR_LIST="com.ifmo.LinkedLearning.BIBO_AUTHOR_LIST";
    public static final String BIBO_PUBLICATION="com.ifmo.LinkedLearning.BIBO_PUBLICATION";
    public static final String BIBO_PDF="com.ifmo.LinkedLearning.BIBO_PDF";


    private LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {

        @Override
        public Loader<Cursor> onCreateLoader(int loaderId, Bundle arg1) {
            return new CursorLoader(
                    BiboAllListFragment.this.getActivity(),
                    Contract.Bibo.CONTENT_URI,
                    PROJECTION,
                    null,
                    null,
                    Contract.Bibo.NAME+" ASC"
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
            Toast.makeText(BiboAllListFragment.this.getActivity(), R.string.server_not_found, Toast.LENGTH_SHORT).show();
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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState){

        adapter = new SimpleCursorAdapter(this.getActivity(),
                R.layout.learning_list_item,
                null,
                new String[]{ Contract.Bibo.NAME },
                new int[]{ R.id.module_name },
                0);
        setListAdapter(adapter);

        getLoaderManager().initLoader(LOADER_ID, null, loaderCallbacks);
        requestManager = RestRequestManager.from(this.getActivity());

//        mPullToRefreshAttacher = PullToRefreshAttacher.get(this.getActivity());
//        mPullToRefreshAttacher.addRefreshableView(getListView(), this);

        mPullToRefreshAttacher = ((MainActivity)this.getActivity()).getPullToRefreshAttacher();
        mPullToRefreshAttacher.clearRefreshableViews();
        mPullToRefreshAttacher.addRefreshableView(getListView(), this);
    }

    void update() {
        requestManager.execute(RequestFactory.getBiboRequest(), requestListener);
    }

    @Override
    public void onRefreshStarted(View view) {
        update();
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Cursor c = ((SimpleCursorAdapter) getListAdapter()).getCursor();
        c.moveToPosition(position);
        Intent intent = new Intent(l.getContext(), BiboActivity.class);
        intent.putExtra(BIBO_URI, c.getString(c.getColumnIndex(Contract.Bibo.URI)));
        intent.putExtra(BIBO_NAME, c.getString(c.getColumnIndex(Contract.Bibo.NAME)));
        intent.putExtra(BIBO_AUTHOR_LIST, c.getString(c.getColumnIndex(Contract.Bibo.AUTHOR_LIST)));
        intent.putExtra(BIBO_PUBLICATION, c.getString(c.getColumnIndex(Contract.Bibo.PUBLICATION)));
        intent.putExtra(BIBO_PDF, c.getString(c.getColumnIndex(Contract.Bibo.PDF)));

        startActivity(intent);
    }

}