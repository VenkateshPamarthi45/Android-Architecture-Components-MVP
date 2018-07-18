package com.test.heady.headytest.localdb;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.test.heady.headytest.localdb.entities.CategoryEntity;
import com.test.heady.headytest.localdb.entities.ProductEntity;
import com.test.heady.headytest.localdb.entities.ProductVariantEntity;
import com.test.heady.headytest.localdb.entities.RankingProductsEntity;

import java.util.List;

@Dao
public interface DaoAccess {

    @Insert
    void insertCategoryRecord(CategoryEntity categoryEntity);

    @Query("SELECT * FROM CategoryEntity WHERE id =:category_id")
    CategoryEntity getCategoryRecord(int category_id);

    @Query("SELECT * FROM CategoryEntity WHERE parent_category_id =:category_id")
    LiveData<List<CategoryEntity>> getChildCategoriesOfCategory(int category_id);

    @Update
    void updateCategoryRecord(CategoryEntity categoryEntity);


    @Insert
    void insertProductRecord(ProductEntity productEntity);

    @Insert
    void insertRankingProductRecord(RankingProductsEntity productEntity);

    @Insert
    void insertProductVariantRecord(ProductVariantEntity productVariantEntity);

    @Query("SELECT * FROM ProductEntity WHERE id =:product_id")
    ProductEntity getProductRecord(int product_id);

    @Query("SELECT * FROM ProductEntity WHERE id =:product_id")
    LiveData<ProductEntity> getLiveDataProductRecord(int product_id);

    @Query("SELECT * FROM ProductEntity WHERE category_id =:category_id")
    LiveData<List<ProductEntity>> getProductListOfCategory(int category_id);

    @Query("SELECT * FROM RankingProductsEntity WHERE type =:type")
    LiveData<List<RankingProductsEntity>> getRankingProductListOfType(String type);

    @Query("SELECT * FROM ProductVariantEntity WHERE id =:variant_id")
    ProductVariantEntity getVariantRecord(int variant_id);

    @Query("SELECT * FROM ProductVariantEntity WHERE product_id =:product_id")
    LiveData<List<ProductVariantEntity>> getVariantsOfProduct(int product_id);

    @Query("SELECT * FROM CategoryEntity")
    LiveData<List<CategoryEntity>> getAllCategories();

}
