package com.test.heady.headytest.features.home.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.test.heady.headytest.common.AdapterClickListener;
import com.test.heady.headytest.features.home.CategoryMenuAdapter;
import com.test.heady.headytest.features.home.presenter.HomePresenter;
import com.test.heady.headytest.features.product_list.ProductListActivity;
import com.test.heady.headytest.R;
import com.test.heady.headytest.features.ranking_product_list.RankingProductListActivity;
import com.test.heady.headytest.common.Constants;
import com.test.heady.headytest.localdb.ApiResponseDatabase;
import com.test.heady.headytest.localdb.entities.CategoryEntity;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomeActivityInterface,AdapterClickListener, View.OnClickListener {
    private static final String TAG = "HomeActivity";

    private HomePresenter homePresenter;
    private ApiResponseDatabase apiResponseDatabase;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private CategoryMenuAdapter mAdapter;
    private TextView mViewedTextView, mOrderedTextView, mSharedTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = findViewById(R.id.progress_bar);

        apiResponseDatabase = Room.databaseBuilder(getApplicationContext(),
                ApiResponseDatabase.class, Constants.DATABASE_NAME).build();
        homePresenter = new HomePresenter(this, apiResponseDatabase);
        homePresenter.getResponse();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mViewedTextView = findViewById(R.id.viewed_text_view);
        mOrderedTextView = findViewById(R.id.ordered_text_view);
        mSharedTextView = findViewById(R.id.shared_text_view);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mViewedTextView.setOnClickListener(this);
        mOrderedTextView.setOnClickListener(this);
        mSharedTextView.setOnClickListener(this);

    }

    @Override
    public void showProgressBar() {
        Log.d(TAG, "showProgressBar() called");
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        Log.d(TAG, "hideProgressBar() called");
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showProductVariantData() {
        LiveData<List<CategoryEntity>> categories = apiResponseDatabase.daoAccess().getAllCategories();
        categories.observe(this, new Observer<List<CategoryEntity>>() {
            @Override
            public void onChanged(@Nullable List<CategoryEntity> categoryEntities) {
                updateUi(categoryEntities);
            }
        });
    }

    public void updateUi(List<CategoryEntity> categoryEntities){
        mAdapter = new CategoryMenuAdapter(categoryEntities, this);
        mRecyclerView.setAdapter(mAdapter);
    }
    @Override
    public void onClickListener(Object object) {
        CategoryEntity categoryEntity = (CategoryEntity) object;
        Intent intent = new Intent(this, ProductListActivity.class);
        intent.putExtra(Constants.KEY_CATEGORY_ID, categoryEntity.getId());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.viewed_text_view:
                Intent intent = new Intent(this, RankingProductListActivity.class);
                intent.putExtra(Constants.KEY_RANKING_TYPE, Constants.VIEW_COUNT);
                startActivity(intent);
                break;
            case R.id.ordered_text_view:
                Intent orderedIntent = new Intent(this, RankingProductListActivity.class);
                orderedIntent.putExtra(Constants.KEY_RANKING_TYPE, Constants.ORDER_COUNT);
                startActivity(orderedIntent);
                break;
            case R.id.shared_text_view:
                Intent sharedIntent = new Intent(this, RankingProductListActivity.class);
                sharedIntent.putExtra(Constants.KEY_RANKING_TYPE, Constants.SHARE_COUNT);
                startActivity(sharedIntent);
                break;
        }
    }
}
