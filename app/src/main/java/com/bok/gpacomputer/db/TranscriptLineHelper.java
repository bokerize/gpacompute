package com.bok.gpacomputer.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.bok.gpacomputer.entity.TranscriptLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JerichoJohn on 3/19/2017.
 */

public class TranscriptLineHelper {
    private static final String TAG = "TranscriptLineHelper";

    public List<TranscriptLine> cursorToList(Cursor cursor) {

        Log.d(TAG, "cursorToList 1");

        List<TranscriptLine> list = new ArrayList<>();

        Log.d(TAG, "cursorToList 2");

        if (cursor != null && cursor.moveToFirst()) {

            Log.d(TAG, "cursorToList 3");

            do {
Log.d(TAG, "cursorToList 4");
                list.add(new TranscriptLine(cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getDouble(4))
                );
            } while(cursor.moveToNext());
        }
        return list;
    }

    public ContentValues toContentValues(TranscriptLine tLine) {

        ContentValues contentValues = new ContentValues();
        if (tLine.getId() != null) {
            contentValues.put(TranscriptLine.COL_ID, tLine.getId());
        }
        contentValues.put(TranscriptLine.COL_COURSE_NO, tLine.getCourseNo());
        contentValues.put(TranscriptLine.COL_COURSE_DESC, tLine.getCourseDesc());
        contentValues.put(TranscriptLine.COL_GRADE, tLine.getGrade());
        contentValues.put(TranscriptLine.COL_CREDIT, tLine.getCredit());

        return contentValues;
    }

}
