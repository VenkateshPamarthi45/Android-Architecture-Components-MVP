package com.test.heady.headytest.localdb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.test.heady.headytest.localdb.entities.CategoryEntity;
import com.test.heady.headytest.localdb.entities.ProductEntity;
import com.test.heady.headytest.localdb.entities.ProductVariantEntity;
import com.test.heady.headytest.localdb.entities.RankingProductsEntity;

@Database(entities = {CategoryEntity.class, ProductEntity.class, ProductVariantEntity.class, RankingProductsEntity.class}, version = 1)
public abstract class ApiResponseDatabase extends RoomDatabase{
    public abstract DaoAccess daoAccess();
}
