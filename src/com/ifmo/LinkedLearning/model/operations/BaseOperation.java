package com.ifmo.LinkedLearning.model.operations;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.foxykeep.datadroid.exception.ConnectionException;
import com.foxykeep.datadroid.exception.CustomRequestException;
import com.foxykeep.datadroid.exception.DataException;
import com.foxykeep.datadroid.network.NetworkConnection;
import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.service.RequestService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Федор
 * Date: 24.09.13
 * Time: 23:59
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseOperation implements RequestService.Operation {

    private static final  String FORMAT="SPARQL/JSON";
    private static final String BASE_URL = "http://linkedlearning.tk/sparql";

    // Need override
    protected abstract  String buildQuery(Request request);
    protected abstract  ContentValues mapJSON(JSONObject object) throws JSONException;
    protected abstract Uri getBaseURI();


    @Override
    public Bundle execute(Context context, Request request)
            throws ConnectionException, DataException, CustomRequestException {
        NetworkConnection connection = new NetworkConnection(context, BASE_URL);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("format", FORMAT);
        params.put("query", buildQuery(request));
        Log.d("SPARQL",buildQuery(request));

        connection.setParameters(params);
        NetworkConnection.ConnectionResult result = connection.execute();
        ContentValues[] values;
        try {
            JSONObject body = new JSONObject(result.body);

            JSONArray jsonArray = body.getJSONObject("results").getJSONArray("bindings");
            values = new ContentValues[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); ++i) {
                values[i] = mapJSON(jsonArray.getJSONObject(i));
            }
        } catch (JSONException e) {
            throw new DataException(e.getMessage());
        }

        context.getContentResolver().delete(getBaseURI(), null, null);
        context.getContentResolver().bulkInsert(getBaseURI(), values);
        return null;
    }
}