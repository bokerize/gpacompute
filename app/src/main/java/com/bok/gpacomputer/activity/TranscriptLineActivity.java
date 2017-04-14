package com.bok.gpacomputer.activity;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bok.gpacomputer.R;
import com.bok.gpacomputer.db.GpaContentContract;
import com.bok.gpacomputer.db.TranscriptLineHelper;
import com.bok.gpacomputer.entity.TranscriptLine;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Created by JerichoJohn on 3/19/2017.
 */

public class TranscriptLineActivity extends AppCompatActivity {

    private EditText etCourseNo;
    private EditText etCourseDesc;
    private EditText etGrade;
    private EditText etCredit;
    private TextView tvId;
    private CoordinatorLayout layout;
    private FloatingActionButton fabSave;

    private final static int TAG_ID = -71;
    private final static String TAG = "TranscriptLineActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transcript_line);

        layout = (CoordinatorLayout) findViewById(R.id.clTranscriptLine);
        layout.setTag(TAG_ID, null);

        tvId = (TextView) findViewById(R.id.tvId);
        etCourseNo = (EditText) findViewById(R.id.etCourseNo);
        etCourseDesc = (EditText) findViewById(R.id.etCourseDesc);
        etGrade = (EditText) findViewById(R.id.etGrade);
        etCredit = (EditText) findViewById(R.id.etCredit);

        fabSave = (FloatingActionButton) findViewById(R.id.fabSave);
        fabSave.setOnClickListener((view -> {
            startActivity(new Intent(TranscriptLineActivity.this, GpaListActivity.class));
        }));
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getIntent().hasExtra("ID")) {
            Long transLineId = getIntent().getExtras().getLong("ID");
            if (transLineId != null) {
                Uri uri = ContentUris.withAppendedId(GpaContentContract.TranscriptLine.CONTENT_URI, transLineId);
                Cursor cursor = getContentResolver().query(uri, TranscriptLine.ALL_COLUMNS, BaseColumns._ID + " = ? ", new String[] {String.valueOf(transLineId)}, null);

                TranscriptLine tLine = (new TranscriptLineHelper().cursorToList(cursor)).get(0);
                loadToScreen(tLine );

                tvId.setText( String.valueOf(transLineId) );
            }
        }
    }


    public void onPause() {
        Log.d(TAG, "Pause 1 --> " + isAllEmpty());
        if (!isAllEmpty()) {
            saveToDb();
        }
        Log.d(TAG, "Pause 2");

        super.onPause();
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed() 1 ");

        if (isAllEmpty()) {
            Log.d(TAG, "onBackPressed() 2 ALL EMPTY ");
            Long id = (Long) layout.getTag(TAG_ID);

            if (id != null) {
                deleteCurrent();
            }

        }

        Intent intent = new Intent();
        intent.putExtra("is_refresh_list", "Y");
        setResult(RESULT_OK, intent);

Log.d(TAG, "onBackPressed() 2 ");
        super.onBackPressed();

        finish();

    }

    public boolean isAllEmpty() {
        return (StringUtils.isEmpty(StringUtils.trim(etCourseNo.getText().toString()))
                && StringUtils.isEmpty(StringUtils.trim(etCourseDesc.getText().toString()))
                && StringUtils.isEmpty(StringUtils.trim(etGrade.getText().toString()))
                );
    }


    public void deleteCurrent() {
        Long id = (Long) layout.getTag(TAG_ID);
        if (id != null) {
            Uri uri = ContentUris.withAppendedId(GpaContentContract.TranscriptLine.CONTENT_URI, id);
            getContentResolver().delete(uri, BaseColumns._ID + " = ?", new String[] {String.valueOf(id)});
        }

    }
    public void saveToDb() {
        Log.d(TAG, "Save tO DB");
        TranscriptLine tLine = new TranscriptLine();

        Long id = (Long) layout.getTag(TAG_ID);
        tLine.setId(id);
        tLine.setCourseNo(etCourseNo.getText().toString());
        tLine.setCourseDesc(etCourseDesc.getText().toString());
        tLine.setGrade(etGrade.getText().toString());
        tLine.setCredit( NumberUtils.toDouble(etCredit.getText().toString(), 0));

        ContentValues contentValues = new TranscriptLineHelper().toContentValues(tLine);
        if (id != null) {
            Uri uri = ContentUris.withAppendedId(GpaContentContract.TranscriptLine.CONTENT_URI, id);
            getContentResolver().update(uri, contentValues, BaseColumns._ID + " = ?", new String[] {String.valueOf(id)});
        } else {
            Uri uri = getContentResolver().insert(GpaContentContract.TranscriptLine.CONTENT_URI, contentValues);
            tLine.setId(ContentUris.parseId(uri));
        }
        Toast.makeText(getApplicationContext(), "TEST SAVE tO DB ", Toast.LENGTH_LONG);

    }

    public void loadToScreen(TranscriptLine tLine ) {
        layout.setTag(TAG_ID, tLine.getId());

        etCourseNo.setText(tLine.getCourseNo());
        etCourseDesc.setText(tLine.getCourseDesc());
        etGrade.setText(tLine.getGrade());
        etCredit.setText( String.valueOf(tLine.getCredit()));
    }
}
