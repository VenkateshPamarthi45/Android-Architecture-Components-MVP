package com.test.heady.headytest.features.product_list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.heady.headytest.common.AdapterClickListener;
import com.test.heady.headytest.R;
import com.test.heady.headytest.localdb.entities.ProductEntity;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private List<ProductEntity> productEntityList;
    private AdapterClickListener adapterClickListener;

    public ProductListAdapter(List<ProductEntity> productEntityList, AdapterClickListener clickListener) {
        this.productEntityList = productEntityList;
        adapterClickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_view_item_one_line, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mTextView.setText(productEntityList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterClickListener.onClickListener(productEntityList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return productEntityList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

         TextView mTextView;

         ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.productName);
        }

    }
}
