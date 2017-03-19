package com.bok.gpacomputer.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bok.gpacomputer.entity.TranscriptLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JerichoJohn on 3/18/2017.
 */

public class SqlHelper extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "alarm_db";

    public static final String CREATE_TABLE = "CREATE TABLE ";
    public static final String ID_TYPE = " INTEGER PRIMARY KEY ";

    public static final String TEXT_TYPE = " TEXT ";
    public static final String INTEGER_TYPE = " INTEGER ";
    public static final String REAL_TYPE = " REAL ";

    public SqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TranscriptLine.CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TranscriptLine.TABLE_NAME);
        onCreate(db);
    }


}
