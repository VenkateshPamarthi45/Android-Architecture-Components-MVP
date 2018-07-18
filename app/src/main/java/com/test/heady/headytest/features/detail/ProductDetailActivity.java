package com.test.heady.headytest.features.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.test.heady.headytest.R;
import com.test.heady.headytest.common.Constants;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_holder);

        int productId = 0;

        Intent intent = getIntent();
        if(intent != null){
            productId = intent.getIntExtra(Constants.KEY_PRODUCT_ID,0);
        }
        ProductDetailActivityFragment frag = ProductDetailActivityFragment.newInstance(productId);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment,frag,ProductDetailActivityFragment.TAG);
        transaction.commit();
    }

}
