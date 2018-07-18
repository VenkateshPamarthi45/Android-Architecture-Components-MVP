package com.test.heady.headytest.features.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.heady.headytest.common.AdapterClickListener;
import com.test.heady.headytest.R;
import com.test.heady.headytest.common.Constants;
import com.test.heady.headytest.localdb.ApiResponseDatabase;
import com.test.heady.headytest.localdb.entities.ProductEntity;
import com.test.heady.headytest.localdb.entities.ProductVariantEntity;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProductDetailActivityFragment extends Fragment implements AdapterClickListener{

    public static final String TAG = "ProductDetailActivityFr";

    private RecyclerView mRecyclerView;
    private ProductVariantsAdapter mAdapter;
    private TextView mProductDetailsTextView;

    public static ProductDetailActivityFragment newInstance(int productId) {

        Bundle args = new Bundle();
        args.putInt(Constants.KEY_PRODUCT_ID, productId);
        ProductDetailActivityFragment fragment = new ProductDetailActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    int productId;
    private ApiResponseDatabase apiResponseDatabase;

    public ProductDetailActivityFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            productId = args.getInt(Constants.KEY_PRODUCT_ID,0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mProductDetailsTextView = view.findViewById(R.id.productDetailsTextView);
        apiResponseDatabase = Room.databaseBuilder(getContext(),
                ApiResponseDatabase.class, Constants.DATABASE_NAME).build();
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        showProductVariantData();
        return view;
    }

    public void showProductVariantData() {
        LiveData<ProductEntity> productEntityLiveData = apiResponseDatabase.daoAccess().getLiveDataProductRecord(productId);
        productEntityLiveData.observe(this, new Observer<ProductEntity>() {
            @Override
            public void onChanged(@Nullable ProductEntity productEntity) {
                updateUiProductDetail(productEntity);
            }
        });

        LiveData<List<ProductVariantEntity>> variantsOfProduct = apiResponseDatabase.daoAccess().getVariantsOfProduct(productId);
        variantsOfProduct.observe(this, new Observer<List<ProductVariantEntity>>() {
            @Override
            public void onChanged(@Nullable List<ProductVariantEntity> productVariantEntities) {
                updateUiForVariants(productVariantEntities);
            }
        });
    }

    private void updateUiProductDetail(ProductEntity productEntity) {
        mProductDetailsTextView.setText(String.format("%s\n Tax : %s", productEntity.getName(), productEntity.getTax_value()));
    }

    private void updateUiForVariants(List<ProductVariantEntity> productVariantEntities){
        mAdapter = new ProductVariantsAdapter(productVariantEntities, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClickListener(Object object) {
    }
}

