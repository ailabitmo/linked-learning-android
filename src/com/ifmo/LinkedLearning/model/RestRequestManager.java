package com.ifmo.LinkedLearning.model;

import android.content.Context;
import com.foxykeep.datadroid.requestmanager.RequestManager;

/**
 * Created with IntelliJ IDEA.
 * User: Федор
 * Date: 25.09.13
 * Time: 0:16
 * To change this template use File | Settings | File Templates.
 */
public final class RestRequestManager extends RequestManager {
    private RestRequestManager(Context context) {
        super(context, RestService.class);
    }

    private static RestRequestManager sInstance;

    public static RestRequestManager from(Context context) {
        if (sInstance == null) {
            sInstance = new RestRequestManager(context);
        }
        return sInstance;
    }
}
