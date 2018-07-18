package com.test.heady.headytest.features.detail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.heady.headytest.common.AdapterClickListener;
import com.test.heady.headytest.R;
import com.test.heady.headytest.localdb.entities.ProductVariantEntity;

import java.util.List;

public class ProductVariantsAdapter extends RecyclerView.Adapter<ProductVariantsAdapter.ViewHolder> {

    private List<ProductVariantEntity> productEntityList;
    private AdapterClickListener adapterClickListener;

     ProductVariantsAdapter(List<ProductVariantEntity> productEntityList, AdapterClickListener clickListener) {
        this.productEntityList = productEntityList;
        adapterClickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_view_item_two_line, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mColorTextView.setText(productEntityList.get(position).getColor());
        holder.mPriceTextView.setText(String.format("Rs. %d", productEntityList.get(position).getPrice()));
        holder.mColorTextView.setOnClickListener(new View.OnClickListener() {
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
        private TextView mColorTextView;
        private TextView mPriceTextView;

         ViewHolder(View v) {
            super(v);
            mColorTextView = v.findViewById(R.id.firstLineItem);
            mPriceTextView = v.findViewById(R.id.secondLineItem);
        }

    }
}
