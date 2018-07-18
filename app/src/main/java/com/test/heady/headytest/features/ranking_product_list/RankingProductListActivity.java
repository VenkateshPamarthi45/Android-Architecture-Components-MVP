package com.test.heady.headytest.features.ranking_product_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.test.heady.headytest.R;
import com.test.heady.headytest.common.Constants;

public class RankingProductListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_holder);

        String rankingType ="";

        Intent intent = getIntent();
        if(intent != null){
            rankingType = intent.getStringExtra(Constants.KEY_RANKING_TYPE);
        }
        RankingProductListActivityFragment frag = RankingProductListActivityFragment.newInstance(rankingType);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment,frag,RankingProductListActivityFragment.TAG);
        transaction.commit();
    }

}
