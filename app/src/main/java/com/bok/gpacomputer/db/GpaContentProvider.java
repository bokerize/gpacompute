package com.bok.gpacomputer.db;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bok.gpacomputer.entity.TranscriptLine;

/**
 * Created by JerichoJohn on 3/18/2017.
 */

public class GpaContentProvider extends ContentProvider {


    private static final int TRANSCRIPT_LINE = 10;
    private static final int TRANSCRIPT_LINE_LIST = 20;

    public static final UriMatcher uriMatcher = getUriMatcher();
//    public static final String BASE = "gpacomputer";

    private GpaDataSource dataSource;

    private static UriMatcher getUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(GpaContentContract.AUTH, GpaContentContract.TranscriptLine.BASE, TRANSCRIPT_LINE_LIST);
        uriMatcher.addURI(GpaContentContract.AUTH, GpaContentContract.TranscriptLine.BASE + "/#", TRANSCRIPT_LINE);

        return uriMatcher;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case TRANSCRIPT_LINE_LIST:
                return GpaContentContract.TranscriptLine.CONTENT_TYPE;
            case TRANSCRIPT_LINE:
                return GpaContentContract.TranscriptLine.CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Override
    public boolean onCreate() {
        dataSource = new GpaDataSource(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] args, @Nullable String order) {
        SQLiteDatabase db = dataSource.getReadableDatabase();
        SQLiteQueryBuilder builder= new SQLiteQueryBuilder();

        boolean useAuthorityUri = false;

        switch (uriMatcher.match(uri)) {
            case TRANSCRIPT_LINE_LIST:
                builder.setTables(TranscriptLine.TABLE_NAME);
                if (TextUtils.isEmpty(order)) {
                    order = TranscriptLine.COL_ID + " " + GpaContentContract.TranscriptLine.SORT_ORDER_DEFAULT;
                }
                break;
            case TRANSCRIPT_LINE:
                builder.setTables(TranscriptLine.TABLE_NAME);
                builder.appendWhere(TranscriptLine.COL_ID + " = " + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        Cursor cursor = builder.query(db, projection, selection, args, null, null, order);

        //if we want notifications
        if (useAuthorityUri) {
            cursor.setNotificationUri(getContext().getContentResolver(), GpaContentContract.TranscriptLine.CONTENT_URI);
        }else {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        if (uriMatcher.match(uri) != TRANSCRIPT_LINE_LIST) {
            throw new IllegalArgumentException("Unsupported URI for INSERT : "+ uri);
        }

        SQLiteDatabase db  = dataSource.getWritableDatabase();
        if (uriMatcher.match(uri) == TRANSCRIPT_LINE_LIST) {
            long id = db.insert(TranscriptLine.TABLE_NAME, null, contentValues);
            return getUriForId(id, uri);
        }
        throw new IllegalArgumentException("Unsupported URI for INSERT : "+ uri);
    }

    private Uri getUriForId(long id, Uri uri) {
        if (id > 0) {
            Uri itemUri = ContentUris.withAppendedId(uri, id);

            getContext().getContentResolver().notifyChange(itemUri, null);

            return itemUri;
        }
        throw new SQLException( " problem inserting intouri: " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] args) {
        SQLiteDatabase db = dataSource.getWritableDatabase();
        int delCount = 0;

        switch(uriMatcher.match(uri) ) {
            case TRANSCRIPT_LINE:
                String idStr = uri.getLastPathSegment();
                String where = TranscriptLine.COL_ID + " = " + idStr;
                if (!TextUtils.isEmpty(selection)) {
                    where += " AND " + selection;
                }
                delCount = db.delete(TranscriptLine.TABLE_NAME, where, args);
                break;
            case TRANSCRIPT_LINE_LIST:
                delCount = db.delete(TranscriptLine.TABLE_NAME, selection, args);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        if (delCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return delCount;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String where, @Nullable String[] args) {

        SQLiteDatabase db = dataSource.getWritableDatabase();
        int updateCount = 0;
        switch (uriMatcher.match(uri)) {
            case TRANSCRIPT_LINE_LIST:
                updateCount = db.update(TranscriptLine.TABLE_NAME, contentValues, where, args);
                break;
            case TRANSCRIPT_LINE:
                String idStr = uri.getLastPathSegment();
                String whereId = TranscriptLine.COL_ID + " = " + idStr;
                if (!TextUtils.isEmpty(where)) {
                    whereId += " AND " + where;
                }
                updateCount = db.update(TranscriptLine.TABLE_NAME, contentValues, whereId, args);
                break;
            default:
                throw new IllegalArgumentException("Unsuppored URI: " + uri);
        }

        if (updateCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updateCount;

    }



}
