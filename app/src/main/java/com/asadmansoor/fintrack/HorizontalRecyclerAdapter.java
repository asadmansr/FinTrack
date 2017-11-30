package com.asadmansoor.fintrack;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by asadmansoor on 2017-11-29.
 */

public class HorizontalRecyclerAdapter extends RecyclerView.Adapter<HorizontalRecyclerAdapter.ViewHolder> {

    private List<String> horizontalList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.item_trending_tv);
        }
    }


    public HorizontalRecyclerAdapter(List<String> horizontalList) {
        this.horizontalList = horizontalList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trending, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.textView.setText(horizontalList.get(position));

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }
}
