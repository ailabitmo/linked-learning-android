package com.ifmo.LinkedLearning.model;

/**
 * Created with IntelliJ IDEA.
 * User: Федор
 * Date: 24.09.13
 * Time: 23:43
 * To change this template use File | Settings | File Templates.
 */


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class RestProvider extends ContentProvider {
    final String TAG = getClass().getSimpleName();

    private static final String TABLE_MODULES = "modules";
    private static final String TABLE_COURSES = "courses";
    private static final String TABLE_LECTURES = "lectures";



    private static final String DB_NAME = "linkedlearning.db";
    private static final int DB_VERSION = 1;

    private static final UriMatcher sUriMatcher;

    private static final int PATH_ROOT = 0;
    private static final int PATH_MODULES = 1;
    private static final int PATH_COURSES= 2;
    private static final int PATH_LECTURES= 3;


    static {
        sUriMatcher = new UriMatcher(PATH_ROOT);
        sUriMatcher.addURI(Contract.AUTHORITY, Contract.Modules.CONTENT_PATH, PATH_MODULES);
        sUriMatcher.addURI(Contract.AUTHORITY, Contract.Course.CONTENT_PATH, PATH_COURSES);
        sUriMatcher.addURI(Contract.AUTHORITY, Contract.Lecture.CONTENT_PATH, PATH_LECTURES);


    }

    private DatabaseHelper mDatabaseHelper;

    class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql =
                    "create table " + TABLE_MODULES + " (" +
                            Contract.Modules._ID + " integer primary key autoincrement, " +
                            Contract.Modules.URI + " text, " +
                            Contract.Modules.NAME + " text, " +
                            Contract.Modules.NUMBER + " integer" +
                            ")";
            db.execSQL(sql);

            sql =
                    "create table " + TABLE_LECTURES + " (" +
                            Contract.Lecture._ID + " integer primary key autoincrement, " +
                            Contract.Lecture.URI + " text, " +
                            Contract.Lecture.NAME + " text, " +
                            Contract.Lecture.NUMBER + " integer" +
                            ")";
            db.execSQL(sql);

            sql =
                    "create table " + TABLE_COURSES + " (" +
                            Contract.Course._ID + " integer primary key autoincrement, " +
                            Contract.Course.URI + " text, " +
                            Contract.Course.NAME + " text" +
                            ")";
            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }

    }

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new DatabaseHelper(getContext(), DB_NAME, null, DB_VERSION);
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (sUriMatcher.match(uri)) {
            case PATH_MODULES: {
                Cursor cursor = mDatabaseHelper.getReadableDatabase().query(TABLE_MODULES, projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), Contract.Modules.CONTENT_URI);
                return cursor;
            }
            case PATH_LECTURES: {
                Cursor cursor = mDatabaseHelper.getReadableDatabase().query(TABLE_LECTURES, projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), Contract.Lecture.CONTENT_URI);
                return cursor;
            }
            case PATH_COURSES: {
                Cursor cursor = mDatabaseHelper.getReadableDatabase().query(TABLE_COURSES, projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), Contract.Course.CONTENT_URI);
                return cursor;
            }
            default:
                return null;
        }
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case PATH_MODULES:
                return Contract.Modules.CONTENT_TYPE;
            case PATH_LECTURES:
                return Contract.Lecture.CONTENT_TYPE;
            case PATH_COURSES:
                return Contract.Course.CONTENT_TYPE;
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (sUriMatcher.match(uri)) {
            case PATH_MODULES: {
                mDatabaseHelper.getWritableDatabase().insert(TABLE_MODULES, null, values);
                getContext().getContentResolver().notifyChange(Contract.Modules.CONTENT_URI, null);
                break;
            }
            case PATH_LECTURES: {
                mDatabaseHelper.getWritableDatabase().insert(TABLE_LECTURES, null, values);
                getContext().getContentResolver().notifyChange(Contract.Lecture.CONTENT_URI, null);
                break;
            }
            case PATH_COURSES: {
                mDatabaseHelper.getWritableDatabase().insert(TABLE_COURSES, null, values);
                getContext().getContentResolver().notifyChange(Contract.Course.CONTENT_URI, null);
                break;
            }
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (sUriMatcher.match(uri)) {
            case PATH_MODULES:
                return mDatabaseHelper.getWritableDatabase().delete(TABLE_MODULES, selection, selectionArgs);
            case PATH_LECTURES:
                return mDatabaseHelper.getWritableDatabase().delete(TABLE_LECTURES, selection, selectionArgs);
            case PATH_COURSES:
                return mDatabaseHelper.getWritableDatabase().delete(TABLE_COURSES, selection, selectionArgs);
            default:
                return 0;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        switch (sUriMatcher.match(uri)) {
            case PATH_MODULES:
                return mDatabaseHelper.getWritableDatabase().update(TABLE_MODULES, values, selection, selectionArgs);
            case PATH_LECTURES:
                return mDatabaseHelper.getWritableDatabase().update(TABLE_LECTURES, values, selection, selectionArgs);
            case PATH_COURSES:
                return mDatabaseHelper.getWritableDatabase().update(TABLE_COURSES, values, selection, selectionArgs);
            default:
                return 0;
        }
    }

}
