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
public final class LectureOperation extends BaseOperation {

    @Override
    protected String buildQuery(Request request) {
//        return "SELECT ?"+ Contract.Lecture.URI+" ?"+Contract.Lecture.NAME+" ?"
//                +Contract.Lecture.PARENT+" ?"+Contract.Lecture.NUMBER+" WHERE { \n" +
//                "?"+Contract.Lecture.URI+" rdf:type learningRu:Lecture .\n" +
//                "?"+Contract.Lecture.PARENT+" learningRu:hasLecture  ?"+Contract.Lecture.URI+" .\n" +
//                "?"+Contract.Lecture.URI+" learningRu:numberOfLecture  ?"+Contract.Lecture.NUMBER+" .\n" +
//                " OPTIONAL {?"+Contract.Lecture.URI+" rdfs:label ?"+Contract.Lecture.NAME+
//                " . FILTER (langMatches(lang(?"+Contract.Lecture.NAME+"), \"ru\")) }" +
//                " OPTIONAL {?"+Contract.Lecture.URI+" rdfs:label ?"+Contract.Lecture.NAME+" } }";
        return SPARQLQueryHelper.queryALLLectures();
    }

    @Override
    protected ContentValues mapJSON(JSONObject object) throws JSONException {
        ContentValues values=new ContentValues();
        values.put(Contract.Lecture.URI, object.getJSONObject(Contract.Lecture.URI).getString("value"));
        values.put(Contract.Lecture.NAME, object.getJSONObject(Contract.Lecture.NAME).getString("value"));
        values.put(Contract.Lecture.NUMBER, object.getJSONObject(Contract.Lecture.NUMBER).getString("value"));
        values.put(Contract.Lecture.PARENT, object.getJSONObject(Contract.Lecture.PARENT).getString("value"));
        return values;
    }

    @Override
    protected Uri getBaseURI() {
        return Contract.Lecture.CONTENT_URI;
    }

}