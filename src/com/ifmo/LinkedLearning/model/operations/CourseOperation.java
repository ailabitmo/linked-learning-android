package com.ifmo.LinkedLearning.model.operations;

import android.content.ContentValues;
import android.net.Uri;
import com.foxykeep.datadroid.requestmanager.Request;
import com.ifmo.LinkedLearning.model.Contract;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: Федор
 * Date: 24.09.13
 * Time: 23:59
 * To change this template use File | Settings | File Templates.
 */
public final class CourseOperation extends BaseOperation {

    @Override
    protected String buildQuery(Request request) {
        return "SELECT ?"+ Contract.Course.URI+" ?"+Contract.Course.NAME+" WHERE { \n" +
                "?"+Contract.Course.URI+" rdf:type aiiso:Course .\n" +
                "?"+Contract.Course.URI+" rdfs:label ?"+Contract.Course.NAME+" . }";
    }

    @Override
    protected ContentValues mapJSON(JSONObject object) throws JSONException {
        ContentValues values=new ContentValues();
        values.put(Contract.Modules.URI, object.getJSONObject(Contract.Modules.URI).getString("value"));
        values.put(Contract.Modules.NAME, object.getJSONObject(Contract.Modules.NAME).getString("value"));
        return values;
    }

    @Override
    protected Uri getBaseURI() {
        return Contract.Course.CONTENT_URI;
    }

}