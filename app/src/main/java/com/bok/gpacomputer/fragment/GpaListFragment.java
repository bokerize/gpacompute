package com.bok.gpacomputer.fragment;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.bok.gpacomputer.R;
import com.bok.gpacomputer.activity.TranscriptLineActivity;
import com.bok.gpacomputer.db.GpaContentContract;
import com.bok.gpacomputer.db.TranscriptLineHelper;
import com.bok.gpacomputer.entity.TranscriptLine;
import com.bok.gpacomputer.view.TranscriptLineRecyclerViewAdapter;

import java.util.List;

import static com.bok.gpacomputer.util.Util.ACT_FLAG_OPEN_TASK_LINE;

/**
 * Created by JerichoJohn on 4/14/2017.
 */

public class GpaListFragment extends Fragment implements TranscriptLineRecyclerViewAdapter.ItemClickListener, TranscriptLineRecyclerViewAdapter.ItemLongClickListener {


    private static final String TAG = "GpaListFragment";

    private OnItemSelectedListener itemSelectedListener;
    private TranscriptLineRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_list, viewGroup, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
    }


    public void loadData() {
        Log.d(TAG, "onActivityCreated 1");
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.rvFrag);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Log.d(TAG, "onActivityCreated 2");
        ContentResolver contentResolver = getContext().getContentResolver();
        Cursor cursor = contentResolver.query(GpaContentContract.TranscriptLine.CONTENT_URI, TranscriptLine.ALL_COLUMNS, null, null, null);
        List<TranscriptLine> transList = new TranscriptLineHelper().cursorToList(cursor);

        Log.d(TAG, "onActivityCreated 3 [" + transList.size() + "]");

        adapter = new TranscriptLineRecyclerViewAdapter(getContext(), transList);
//        adapter.setItemClickListener(this);
//        adapter.setItemLongClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    public interface OnItemSelectedListener {
        public void onListItemSelected(String link);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            this.itemSelectedListener = (OnItemSelectedListener) context;

        } else {
            throw new ClassCastException("Context must implement GpaListFragment.OnItemSelectedListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.itemSelectedListener = null;
    }

    public void updateDetail(String uri) {

        itemSelectedListener.onListItemSelected(uri);
    }


    @Override
    public void onItemClick(View view, int position) {
//        Intent editTranscriptLine = new Intent(this, TranscriptLineActivity.class);
//
//        TranscriptLine tLine = adapter.getItem(position);
//        editTranscriptLine.putExtra("ID", tLine.getId());
//
//        startActivityForResult(editTranscriptLine, ACT_FLAG_OPEN_TASK_LINE);
    }

    @Override
    public boolean onItemLongClick(View view, int position) {

//        TranscriptLine tLine = adapter.getItem(position);
//
//        Uri uri = ContentUris.withAppendedId(GpaContentContract.TranscriptLine.CONTENT_URI, tLine.getId());
//        getContentResolver().delete(uri, BaseColumns._ID + " = ?", new String[] {String.valueOf(tLine.getId())});
//
//        Toast.makeText(this, "record deleted", Toast.LENGTH_SHORT);

        return true;
    }


}
