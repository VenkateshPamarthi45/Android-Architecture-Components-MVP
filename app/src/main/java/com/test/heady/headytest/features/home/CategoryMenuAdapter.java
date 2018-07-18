package com.test.heady.headytest.features.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.heady.headytest.R;
import com.test.heady.headytest.common.AdapterClickListener;
import com.test.heady.headytest.localdb.entities.CategoryEntity;

import java.util.List;

public class CategoryMenuAdapter extends RecyclerView.Adapter<CategoryMenuAdapter.ViewHolder> {

    private List<CategoryEntity> categoryEntityList;
    private AdapterClickListener adapterClickListener;

    public CategoryMenuAdapter(List<CategoryEntity> categoryEntities, AdapterClickListener clickListener) {
        this.categoryEntityList = categoryEntities;
        adapterClickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_menu_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mTitleTextView.setText(categoryEntityList.get(position).getName());
        holder.mTitleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterClickListener.onClickListener(categoryEntityList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryEntityList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTitleTextView;
        public ViewHolder(TextView v) {
            super(v);
            mTitleTextView = v;
        }

    }
}
