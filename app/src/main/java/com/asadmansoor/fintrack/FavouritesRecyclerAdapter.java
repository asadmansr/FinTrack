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

public class FavouritesRecyclerAdapter extends RecyclerView.Adapter<FavouritesRecyclerAdapter.ViewHolder> {

    private List<String> horizontalList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView currency_title_textView;
        public TextView currency_amount_textView;

        public ViewHolder(View view) {
            super(view);
            currency_title_textView = (TextView) view.findViewById(R.id.currency_title);
            currency_amount_textView = (TextView) view.findViewById(R.id.currency_amount);
        }
    }


    public FavouritesRecyclerAdapter(List<String> horizontalList) {
        this.horizontalList = horizontalList;
    }

    @Override
    public FavouritesRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourites, parent, false);

        return new FavouritesRecyclerAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FavouritesRecyclerAdapter.ViewHolder holder, final int position) {

        String currency_value = horizontalList.get(position);
        holder.currency_title_textView.setText((currency_value.split(","))[0]);
        holder.currency_amount_textView.setText((currency_value.split(","))[1]);

//        holder.textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }
}
