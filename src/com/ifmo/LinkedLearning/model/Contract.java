package com.ifmo.LinkedLearning.model;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created with IntelliJ IDEA.
 * User: Федор
 * Date: 24.09.13
 * Time: 23:41
 * To change this template use File | Settings | File Templates.
 */
public final class Contract {
    public static final String AUTHORITY = "com.ifmo.LinkedLearning.model";

    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    public interface ModulesColumns {
        public static final String NAME = "name";
        public static final String URI = "uri";
        public static final String NUMBER = "number";

    }

    public static final class Modules implements BaseColumns, ModulesColumns {
        public static final String CONTENT_PATH = "modules";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_PATH);
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + CONTENT_PATH;
    }


    public interface LectureColumns {
        public static final String NAME = "name";
        public static final String URI = "uri";
        public static final String NUMBER = "number";

    }

    public static final class Lecture implements BaseColumns, LectureColumns {
        public static final String CONTENT_PATH = "lectures";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_PATH);
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + CONTENT_PATH;
    }


    public interface CourseColumns {
        public static final String NAME = "name";
        public static final String URI = "uri";
    }

    public static final class Course implements BaseColumns, CourseColumns {
        public static final String CONTENT_PATH = "courses";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_PATH);
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + CONTENT_PATH;
    }



}