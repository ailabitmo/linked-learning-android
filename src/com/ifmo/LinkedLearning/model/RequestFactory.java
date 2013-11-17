package com.ifmo.LinkedLearning.model;

import com.foxykeep.datadroid.requestmanager.Request;

/**
 * Created with IntelliJ IDEA.
 * User: Федор
 * Date: 25.09.13
 * Time: 0:12
 * To change this template use File | Settings | File Templates.
 */
    public final class RequestFactory {
        public static final int REQUEST_MODULES = 1;
        public static final int REQUEST_COURSES = 2;
        public static final int REQUEST_LECTURE = 3;
        public static final int REQUEST_TERM = 4;
        public static final int REQUEST_BIBO = 5;



    public static Request getCourseRequest() {
            Request request = new Request(REQUEST_COURSES);
            return request;
        }

        public static Request getModuleRequest(String courseURI) {
            Request request = new Request(REQUEST_MODULES);
            request.put("courseURI", courseURI);
            return request;
        }

        public static Request getLectureRequest(String moduleURI) {
            Request request = new Request(REQUEST_LECTURE);
            request.put("moduleURI", moduleURI);
            return request;
        }

        public static Request getTermRequest() {
            Request request = new Request(REQUEST_TERM);
            return request;
        }

    public static Request getBiboRequest() {
        Request request = new Request(REQUEST_BIBO);
        return request;
    }

        private RequestFactory() {
        }
    }

