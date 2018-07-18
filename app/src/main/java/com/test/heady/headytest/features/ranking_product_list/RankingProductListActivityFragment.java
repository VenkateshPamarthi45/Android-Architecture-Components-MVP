package com.test.heady.headytest.features.ranking_product_list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.heady.headytest.common.AdapterClickListener;
import com.test.heady.headytest.features.product_list.SubCategoryAdapter;
import com.test.heady.headytest.R;
import com.test.heady.headytest.common.Constants;
import com.test.heady.headytest.features.detail.ProductDetailActivity;
import com.test.heady.headytest.localdb.ApiResponseDatabase;
import com.test.heady.headytest.localdb.entities.RankingProductsEntity;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class RankingProductListActivityFragment extends Fragment implements AdapterClickListener {

    public static final String TAG = "RankingProductListActiv";

    private RecyclerView mProductRecyclerView;
    private RankingProductAdapter mAdapter;
    private RecyclerView mChildCategoryRecyclerView;

    public static RankingProductListActivityFragment newInstance(String rankingType) {

        Bundle args = new Bundle();
        args.putString(Constants.KEY_RANKING_TYPE, rankingType);
        RankingProductListActivityFragment fragment = new RankingProductListActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    String rankingType;
    private ApiResponseDatabase apiResponseDatabase;

    public RankingProductListActivityFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            rankingType = args.getString(Constants.KEY_RANKING_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.product_list, container, false);
        mProductRecyclerView = (RecyclerView) view.findViewById(R.id.productRecyclerview);
        apiResponseDatabase = Room.databaseBuilder(getContext(),
                ApiResponseDatabase.class, Constants.DATABASE_NAME).build();
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        mProductRecyclerView.setLayoutManager(mLayoutManager);
        mChildCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        showProductsListData();
        return view;
    }

    public void showProductsListData() {
        LiveData<List<RankingProductsEntity>> productsList = apiResponseDatabase.daoAccess().getRankingProductListOfType(rankingType);
        productsList.observe(this, new Observer<List<RankingProductsEntity>>() {
            @Override
            public void onChanged(@Nullable List<RankingProductsEntity> productVariantEntities) {
                updateUi(productVariantEntities);
            }
        });
    }

    private void updateUi(List<RankingProductsEntity> productEntities){
        mAdapter = new RankingProductAdapter(productEntities, this);
        mProductRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClickListener(Object object) {
        if(object instanceof  RankingProductsEntity){
            RankingProductsEntity productEntity = (RankingProductsEntity) object;
            Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
            intent.putExtra(Constants.KEY_PRODUCT_ID, productEntity.getProduct_id());
            startActivity(intent);
        }

    }
}

