package com.test.heady.headytest.features.product_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.test.heady.headytest.R;
import com.test.heady.headytest.common.Constants;

public class ProductListActivity extends AppCompatActivity {

    private static final String TAG = "ProductListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        int categoryId = 0;

        Intent intent = getIntent();
        if(intent != null){
            categoryId = intent.getIntExtra(Constants.KEY_CATEGORY_ID,0);
            Log.i(TAG, "onCreate: category id " + categoryId);
        }
        ProductListActivityFragment frag = ProductListActivityFragment.newInstance(categoryId);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment,frag,ProductListActivityFragment.TAG);
        transaction.commit();
    }

}
