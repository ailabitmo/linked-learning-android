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
public final class TermOperation extends BaseOperation {

    @Override
    protected String buildQuery(Request request) {
//        return "SELECT ?"+ Contract.Term.URI+" ?"+Contract.Term.NAME+" ?"+Contract.Term.PARENT+" WHERE { \n" +
//                "?"+Contract.Term.URI+" rdf:type learningRu:Term .\n" +
//                "?"+Contract.Term.URI+"  learningRu:isTermOf ?"+ Contract.Term.PARENT+".\n" +
//                " OPTIONAL {?"+Contract.Term.URI+" rdfs:label ?"+Contract.Term.NAME+
//                " . FILTER (langMatches(lang(?"+Contract.Term.NAME+"), \"ru\")) }" +
//                " OPTIONAL {?"+Contract.Term.URI+" rdfs:label ?"+Contract.Term.NAME+" } }";
        return SPARQLQueryHelper.queryALLTerms();
    }

    @Override
    protected ContentValues mapJSON(JSONObject object) throws JSONException {
        ContentValues values=new ContentValues();
        values.put(Contract.Term.URI, object.getJSONObject(Contract.Term.URI).getString("value"));
        values.put(Contract.Term.NAME, object.getJSONObject(Contract.Term.NAME).getString("value"));
        values.put(Contract.Term.PARENT, object.getJSONObject(Contract.Term.PARENT).getString("value"));

        return values;
    }

    @Override
    protected Uri getBaseURI() {
        return Contract.Term.CONTENT_URI;
    }

}