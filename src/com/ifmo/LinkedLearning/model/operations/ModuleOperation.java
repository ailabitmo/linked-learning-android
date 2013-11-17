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
public final class ModuleOperation extends BaseOperation {

    @Override
    protected String buildQuery(Request request) {
//        return "SELECT ?"+ Contract.Modules.URI+" ?"+Contract.Modules.NAME+" ?"+Contract.Modules.NUMBER+" WHERE { \n" +
//                "?"+Contract.Modules.URI+" rdf:type aiiso:Module .\n" +
//                "<"+request.getString("courseURI")+">"+" learningRu:hasModule  ?"+Contract.Modules.URI+" .\n" +
//                "?"+Contract.Modules.URI+" learningRu:numberOfModule  ?"+Contract.Modules.NUMBER+" .\n" +
//                " OPTIONAL {?"+Contract.Modules.URI+" rdfs:label ?"+Contract.Modules.NAME+
//                " . FILTER (langMatches(lang(?"+Contract.Modules.NAME+"), \"ru\")) }" +
//                " OPTIONAL {?"+Contract.Modules.URI+" rdfs:label ?"+Contract.Modules.NAME+" } }";
        return SPARQLQueryHelper.queryALLModules();
    }

    @Override
    protected ContentValues mapJSON(JSONObject object) throws JSONException {
        ContentValues values=new ContentValues();
        values.put(Contract.Modules.URI, object.getJSONObject(Contract.Modules.URI).getString("value"));
        values.put(Contract.Modules.NAME, object.getJSONObject(Contract.Modules.NAME).getString("value"));
        values.put(Contract.Modules.NUMBER, object.getJSONObject(Contract.Modules.NUMBER).getString("value"));
        values.put(Contract.Modules.PARENT, object.getJSONObject(Contract.Modules.PARENT).getString("value"));
        return values;
    }

    @Override
    protected Uri getBaseURI() {
        return Contract.Modules.CONTENT_URI;
    }

}