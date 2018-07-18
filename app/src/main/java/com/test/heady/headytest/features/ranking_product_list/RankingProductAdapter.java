package com.test.heady.headytest.features.ranking_product_list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.heady.headytest.common.AdapterClickListener;
import com.test.heady.headytest.R;
import com.test.heady.headytest.localdb.entities.RankingProductsEntity;

import java.util.List;

public class RankingProductAdapter extends RecyclerView.Adapter<RankingProductAdapter.ViewHolder> {

    private List<RankingProductsEntity> productEntityList;
    private AdapterClickListener adapterClickListener;

    public RankingProductAdapter(List<RankingProductsEntity> productEntityList, AdapterClickListener clickListener) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.mTitleTextView.setText(productEntityList.get(position).getProduct_name());
        holder.mMoreTextView.setText(String.format("%s : %d", productEntityList.get(position).getType(), productEntityList.get(position).getValue()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterClickListener.onClickListener(productEntityList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return productEntityList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTitleTextView,mMoreTextView;

        public ViewHolder(View v) {
            super(v);
            mTitleTextView = v.findViewById(R.id.firstLineItem);
            mMoreTextView = v.findViewById(R.id.secondLineItem);
        }

    }
}
