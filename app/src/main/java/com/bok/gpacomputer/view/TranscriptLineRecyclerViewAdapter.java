package com.bok.gpacomputer.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private ItemClickListener itemClickListener;
    private ItemLongClickListener itemLongClickListener;
    private Context context;
    private static final String TAG = "TranscriptLineRecycler";

    public TranscriptLineRecyclerViewAdapter(Context context, List<TranscriptLine> data) {
        this.context = context;
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

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener , View.OnLongClickListener {
        public TextView tvCourseNo;
        public TextView tvGrade;
        public GridLayout gridLayout;
        public LinearLayout linearLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            tvCourseNo = (TextView) itemView.findViewById(R.id.tvCourseNo);
            tvCourseNo.setOnClickListener(this);
            tvCourseNo.setOnLongClickListener(this);

            tvGrade = (TextView) itemView.findViewById(R.id.tvGrade);
            tvGrade.setOnClickListener(this);
            tvGrade.setOnLongClickListener(this);

            gridLayout = (GridLayout) itemView.findViewById(R.id.gridLayout);
            gridLayout.setClickable(true);
            gridLayout.setOnClickListener(this);
            gridLayout.setOnLongClickListener(this);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
            linearLayout.setClickable(true);
            linearLayout.setOnClickListener(this);
            linearLayout.setOnLongClickListener(this);

        }


        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
Log.d(TAG, "onClick -- 1");
                itemClickListener.onItemClick(view, getAdapterPosition());
    Log.d(TAG, "onClick --2 ");
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (itemLongClickListener  != null) {

                itemLongClickListener.onItemLongClick(view, getAdapterPosition());

                Toast.makeText(context, "record deleted", Toast.LENGTH_SHORT);

                removeOnAdapterPosition();

                Toast.makeText(context, "record deleted 2", Toast.LENGTH_SHORT);

            }
            return false;
        }

        public void removeOnAdapterPosition() {
            mData.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
            notifyItemRangeChanged(getAdapterPosition(), mData.size());

        }
    }



    public void updateData(List<TranscriptLine> tLineList) {
        mData.clear();
        mData.addAll(tLineList);
        notifyDataSetChanged();
    }
//    public void addItem(int position, ViewModel viewModel) {
//        items.add(position, viewModel);
//        notifyItemInserted(position);
//    }

//    public void removeItem(int position) {
//        items.remove(position);
//        notifyItemRemoved(position);
//    }

    public TranscriptLine getItem(int i) {
        return mData.get(i);
    }

    public interface ItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
    //allows click event to be caught
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setItemLongClickListener(ItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }


//    public interface RecyclerViewUtil {
//        void removeOnAdapterPosition();
//    }

}
