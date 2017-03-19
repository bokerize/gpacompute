package com.bok.gpacomputer.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bok.gpacomputer.R;
import com.bok.gpacomputer.db.GpaContentContract;
import com.bok.gpacomputer.db.TranscriptLineHelper;
import com.bok.gpacomputer.entity.TranscriptLine;
import com.bok.gpacomputer.view.TranscriptLineRecyclerViewAdapter;

import java.util.List;

public class GpaListActivity extends AppCompatActivity implements TranscriptLineRecyclerViewAdapter.ItemClickListener {

    private TranscriptLineRecyclerViewAdapter adapter;
    private FloatingActionButton fabAdd;

    private static final String TAG = "GpaListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa_list);

        Log.d(TAG, "list activity 1 " );

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(GpaContentContract.TranscriptLine.CONTENT_URI, TranscriptLine.ALL_COLUMNS, null, null, null);
        List<TranscriptLine> transList = new TranscriptLineHelper().cursorToList(cursor);

        Log.d(TAG, "list activity 2 list size " + transList.size());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvTranscriptList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TranscriptLineRecyclerViewAdapter(this, transList);
        adapter.setItemClickListener(this);
        recyclerView.setAdapter(adapter);

        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(view ->
            startActivity(new Intent(GpaListActivity.this, TranscriptLineActivity.class))
        );

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

        startActivity(editTranscriptLine);
    }



}
