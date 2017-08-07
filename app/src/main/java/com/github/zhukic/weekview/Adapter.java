package com.github.zhukic.weekview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author Vladislav Zhukov (https://github.com/zhukic)
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(position + 1);
    }

    @Override
    public int getItemCount() {
        return 23;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(int position) {
            if (position < 10) {
                ((TextView) itemView).setText("0" + position + ":00");
            } else {
                ((TextView) itemView).setText(position + ":00");
            }
        }

    }

}
