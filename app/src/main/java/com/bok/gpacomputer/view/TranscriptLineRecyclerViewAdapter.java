package com.bok.gpacomputer.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bok.gpacomputer.R;
import com.bok.gpacomputer.entity.TranscriptLine;

import java.util.Collections;
import java.util.List;

/**
 * Created by JerichoJohn on 3/19/2017.
 */

public class TranscriptLineRecyclerViewAdapter extends RecyclerView.Adapter<TranscriptLineRecyclerViewAdapter.ViewHolder> {

    private List<TranscriptLine> mData = Collections.emptyList();
    private LayoutInflater mInflater;

    private static final String TAG = "TranscriptLineRecycler";

    public TranscriptLineRecyclerViewAdapter(Context context, List<TranscriptLine> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    //inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d(TAG, "onCreateviewHolder list size " + mData.size());

        View view = mInflater.inflate(R.layout.transcript_list_recycler, parent, false);
        ViewHolder viewHolder = new TranscriptLineRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        TranscriptLine tLine = mData.get(position);
Log.d(TAG, "on bind view holder " + position + " [" + tLine.getCourseNo() + "]");
        holder.tvCourseNo.setText(tLine.getCourseNo());
        holder.tvGrade.setText(tLine.getGrade());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCourseNo;
        public TextView tvGrade;

        public ViewHolder(View itemView) {
            super(itemView);

            tvCourseNo = (TextView) itemView.findViewById(R.id.tvCourseNo);
            tvGrade = (TextView) itemView.findViewById(R.id.tvGrade);
        }
    }


    public TranscriptLine getItem(int i) {
        return mData.get(i);
    }

}
