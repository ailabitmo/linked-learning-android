package com.ifmo.LinkedLearning.model.operations;

import android.content.ContentValues;
import android.net.Uri;
import android.util.Log;
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
public final class BiboOperation extends BaseOperation {

    @Override
    protected String buildQuery(Request request) {
//        return "SELECT ?"+ Contract.Term.URI+" ?"+Contract.Term.NAME+" ?"+Contract.Term.PARENT+" WHERE { \n" +
//                "?"+Contract.Term.URI+" rdf:type learningRu:Term .\n" +
//                "?"+Contract.Term.URI+"  learningRu:isTermOf ?"+ Contract.Term.PARENT+".\n" +
//                " OPTIONAL {?"+Contract.Term.URI+" rdfs:label ?"+Contract.Term.NAME+
//                " . FILTER (langMatches(lang(?"+Contract.Term.NAME+"), \"ru\")) }" +
//                " OPTIONAL {?"+Contract.Term.URI+" rdfs:label ?"+Contract.Term.NAME+" } }";
        return SPARQLQueryHelper.queryALLBibos();
    }

    @Override
    protected ContentValues mapJSON(JSONObject object) throws JSONException {
        ContentValues values=new ContentValues();
        values.put(Contract.Bibo.URI, object.getJSONObject(Contract.Bibo.URI).getString("value"));
        values.put(Contract.Bibo.NAME, object.getJSONObject(Contract.Bibo.NAME).getString("value"));
        try{
            values.put(Contract.Bibo.PARENT, object.getJSONObject(Contract.Bibo.PARENT).getString("value"));
        }catch (JSONException e){
            values.put(Contract.Bibo.PARENT, "");
        }
        try{
            values.put(Contract.Bibo.PDF, object.getJSONObject(Contract.Bibo.PDF).getString("value"));
        }catch (JSONException e){
            values.put(Contract.Bibo.PDF, "");
        }
        try{
            values.put(Contract.Bibo.AUTHOR_LIST, object.getJSONObject(Contract.Bibo.AUTHOR_LIST).getString("value"));
        }catch (JSONException e){
            values.put(Contract.Bibo.AUTHOR_LIST, "");
        }
        try{
            values.put(Contract.Bibo.PUBLICATION, object.getJSONObject(Contract.Bibo.PUBLICATION).getString("value"));
        }catch (JSONException e){
            values.put(Contract.Bibo.PUBLICATION, "");
        }

        return values;
    }

    @Override
    protected Uri getBaseURI() {
        return Contract.Bibo.CONTENT_URI;
    }

}