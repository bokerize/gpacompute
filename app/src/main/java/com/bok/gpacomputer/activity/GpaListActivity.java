package com.bok.gpacomputer.activity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bok.gpacomputer.R;
import com.bok.gpacomputer.db.GpaContentContract;
import com.bok.gpacomputer.db.TranscriptLineHelper;
import com.bok.gpacomputer.entity.TranscriptLine;
import com.bok.gpacomputer.view.TranscriptLineRecyclerViewAdapter;

import java.util.List;

import static com.bok.gpacomputer.util.Util.ACT_FLAG_OPEN_TASK_LINE;

public class GpaListActivity extends AppCompatActivity implements TranscriptLineRecyclerViewAdapter.ItemClickListener, TranscriptLineRecyclerViewAdapter.ItemLongClickListener {

    private TranscriptLineRecyclerViewAdapter adapter;
    private FloatingActionButton fabAdd;

    private static final String TAG = "GpaListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa_list);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvTranscriptList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(GpaContentContract.TranscriptLine.CONTENT_URI, TranscriptLine.ALL_COLUMNS, null, null, null);
        List<TranscriptLine> transList = new TranscriptLineHelper().cursorToList(cursor);


        adapter = new TranscriptLineRecyclerViewAdapter(this, transList);
        adapter.setItemClickListener(this);
        adapter.setItemLongClickListener(this);
        recyclerView.setAdapter(adapter);

        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(view ->
            startActivityForResult(new Intent(GpaListActivity.this, TranscriptLineActivity.class), ACT_FLAG_OPEN_TASK_LINE)
//            startActivityForResult(new Intent(GpaListActivity.this, CombinedActivity.class), ACT_FLAG_OPEN_TASK_LINE)
        );
//        fabAdd.setOnLongClickListener(view ->
//                startActivityForResult(new Intent(GpaListActivity.this, CombinedActivity.class), ACT_FLAG_OPEN_TASK_LINE)
//        );
        fabAdd.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                startActivityForResult(new Intent(GpaListActivity.this, CombinedActivity.class), ACT_FLAG_OPEN_TASK_LINE);
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent editTranscriptLine = new Intent(this, TranscriptLineActivity.class);

        TranscriptLine tLine = adapter.getItem(position);
        editTranscriptLine.putExtra("ID", tLine.getId());

        startActivityForResult(editTranscriptLine, ACT_FLAG_OPEN_TASK_LINE);
    }

    @Override
    public boolean onItemLongClick(View view, int position) {

        TranscriptLine tLine = adapter.getItem(position);

        Uri uri = ContentUris.withAppendedId(GpaContentContract.TranscriptLine.CONTENT_URI, tLine.getId());
        getContentResolver().delete(uri, BaseColumns._ID + " = ?", new String[] {String.valueOf(tLine.getId())});

        Toast.makeText(this, "record deleted", Toast.LENGTH_SHORT);

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACT_FLAG_OPEN_TASK_LINE) {

            if (resultCode == RESULT_OK) {
                adapter.notifyDataSetChanged();
                if (data != null && "Y".equals(data.getStringExtra("is_refresh_list"))) {

                    refreshData();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void refreshData() {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(GpaContentContract.TranscriptLine.CONTENT_URI, TranscriptLine.ALL_COLUMNS, null, null, null);
        List<TranscriptLine> transList = new TranscriptLineHelper().cursorToList(cursor);
        adapter.updateData(transList);
    }


}
