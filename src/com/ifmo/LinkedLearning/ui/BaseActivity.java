package com.ifmo.LinkedLearning.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

/**
 * Created with IntelliJ IDEA.
 * User: Федор
 * Date: 03.10.13
 * Time: 22:04
 * To change this template use File | Settings | File Templates.
 */
public class BaseActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}