package com.ifmo.LinkedLearning.ui;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.ifmo.LinkedLearning.R;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Федор
 * Date: 18.11.13
 * Time: 0:08
 * To change this template use File | Settings | File Templates.
 */
public class BiboActivity extends BaseActivity {

    String bibo_uri;
    String bibo_name;
    String bibo_author_list;
    String bibo_publication;
    String bibo_pdf;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bibo_activity);

        bibo_uri = getIntent().getStringExtra(BiboAllListFragment.BIBO_URI);
        bibo_name = getIntent().getStringExtra(BiboAllListFragment.BIBO_NAME);
        bibo_author_list = getIntent().getStringExtra(BiboAllListFragment.BIBO_AUTHOR_LIST);
        bibo_publication = getIntent().getStringExtra(BiboAllListFragment.BIBO_PUBLICATION);
        bibo_pdf = getIntent().getStringExtra(BiboAllListFragment.BIBO_PDF);


        setTitle(bibo_name);
        ((TextView)findViewById(R.id.titleText)).setText(bibo_name);
        ((TextView)findViewById(R.id.authText)).setText(bibo_author_list);
        ((TextView)findViewById(R.id.pubText)).setText(bibo_publication);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bibo_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.download_pdf:
                downloadPDF();
                return true;
            default:

        }
        return super.onOptionsItemSelected(item);
    }

    private void downloadPDF(){

        if(bibo_pdf.isEmpty()){
            Toast.makeText(this, R.string.file_not_exist, Toast.LENGTH_SHORT).show();
            return;
        }

        if(!isDownloadManagerAvailable(this))
            return;

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(bibo_pdf));
        request.setDescription(bibo_name);
        request.setTitle("Загрузка PDF");
        // in order for this if to run, you must use the android 3.2 to compile your app
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, bibo_name+".pdf");

        // get download service and enqueue file
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    public static boolean isDownloadManagerAvailable(Context context) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
                return false;
            }
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setClassName("com.android.providers.downloads.ui", "com.android.providers.downloads.ui.DownloadList");
            List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent,
                    PackageManager.MATCH_DEFAULT_ONLY);
            return list.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}