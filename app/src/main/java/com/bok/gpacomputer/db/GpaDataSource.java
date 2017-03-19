package com.bok.gpacomputer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by JerichoJohn on 3/18/2017.
 */

public class GpaDataSource {

    private SQLiteDatabase db;
    private SqlHelper helper;

    public GpaDataSource(Context context) {
        helper= new SqlHelper(context);
    }


    public SQLiteDatabase getWritableDatabase() {
        return helper.getWritableDatabase();
    }

    public SQLiteDatabase getReadableDatabase() {
        return helper.getReadableDatabase();
    }

}
