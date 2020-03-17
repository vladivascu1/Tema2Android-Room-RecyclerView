package com.example.tema2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tema2.models.Mark;

import java.util.List;

public class MarksDisplayAdapter extends RecyclerView.Adapter<MarksDisplayAdapter.ViewHolder> {
    private List<Mark> mDataset;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        RelativeLayout parentLayout;

        public ViewHolder(View v) {
            super(v);

            textView = v.findViewById(R.id.numeTextView);
            parentLayout = v.findViewById(R.id.parent_layout);

        }
    }

    public MarksDisplayAdapter(Context pContext, List<Mark> myDataset) {
        System.out.println("LENGTH:" + myDataset.size());
        context = pContext;
        mDataset = myDataset;
    }

    @Override
    public MarksDisplayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_mark, parent, false);

        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.textView.setText(mDataset.get(position).name + " " +  mDataset.get(position).mark);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
