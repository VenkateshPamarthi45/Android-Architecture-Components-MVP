package com.test.heady.headytest.features.product_list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.heady.headytest.common.AdapterClickListener;
import com.test.heady.headytest.R;
import com.test.heady.headytest.common.Constants;
import com.test.heady.headytest.features.detail.ProductDetailActivity;
import com.test.heady.headytest.localdb.ApiResponseDatabase;
import com.test.heady.headytest.localdb.entities.CategoryEntity;
import com.test.heady.headytest.localdb.entities.ProductEntity;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProductListActivityFragment extends Fragment implements AdapterClickListener {

    public static final String TAG = "ProductListActivityFrag";

    private RecyclerView mProductRecyclerView;
    private ProductListAdapter mAdapter;
    private RecyclerView mChildCategoryRecyclerView;
    private SubCategoryAdapter mCategoryAdapter;

    public static ProductListActivityFragment newInstance(int categoryId) {

        Bundle args = new Bundle();
        args.putInt(Constants.KEY_CATEGORY_ID, categoryId);
        ProductListActivityFragment fragment = new ProductListActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    int categoryId;
    private ApiResponseDatabase apiResponseDatabase;

    public ProductListActivityFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            categoryId = args.getInt(Constants.KEY_CATEGORY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.product_list, container, false);
        mProductRecyclerView = (RecyclerView) view.findViewById(R.id.productRecyclerview);
        mChildCategoryRecyclerView = (RecyclerView) view.findViewById(R.id.childCategoriesRecyclerview);
        apiResponseDatabase = Room.databaseBuilder(getContext(),
                ApiResponseDatabase.class, Constants.DATABASE_NAME).build();
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        mProductRecyclerView.setLayoutManager(mLayoutManager);
        mChildCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        showProductsListData();
        showChildCategoriesData();
        return view;
    }

    public void showChildCategoriesData() {
        LiveData<List<CategoryEntity>> categoryEntityList = apiResponseDatabase.daoAccess().getChildCategoriesOfCategory(categoryId);
        categoryEntityList.observe(this, new Observer<List<CategoryEntity>>() {
            @Override
            public void onChanged(@Nullable List<CategoryEntity> productVariantEntities) {
                updateUiForChildCategory(productVariantEntities);
            }
        });
    }

    public void showProductsListData() {
        LiveData<List<ProductEntity>> productsList = apiResponseDatabase.daoAccess().getProductListOfCategory(categoryId);
        productsList.observe(this, new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(@Nullable List<ProductEntity> productVariantEntities) {

                updateUi(productVariantEntities);
            }
        });
    }

    private void updateUiForChildCategory(List<CategoryEntity> categoryEntities){
        mCategoryAdapter = new SubCategoryAdapter(categoryEntities, this);
        mChildCategoryRecyclerView.setAdapter(mCategoryAdapter);
    }

    private void updateUi(List<ProductEntity> productEntities){
        Log.d(TAG, "updateUi() called with: productEntities = [" + productEntities + "]");
        mAdapter = new ProductListAdapter(productEntities, this);
        mProductRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClickListener(Object object) {
        if(object instanceof  ProductEntity){
            ProductEntity productEntity = (ProductEntity) object;
            Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
            intent.putExtra(Constants.KEY_PRODUCT_ID, productEntity.getId());
            startActivity(intent);
        }else if(object instanceof CategoryEntity){
            CategoryEntity categoryEntity = (CategoryEntity) object;
            Log.d(TAG, "onClickListener() called with: categoryEntity = [" + categoryEntity + "]");
            Intent intent = new Intent(getActivity(), ProductListActivity.class);
            intent.putExtra(Constants.KEY_CATEGORY_ID, categoryEntity.getId());
            startActivity(intent);
        }

    }
}

