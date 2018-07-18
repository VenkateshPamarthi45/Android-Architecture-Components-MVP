package com.test.heady.headytest.features.home.interactor;

import android.os.AsyncTask;
import android.util.Log;

import com.test.heady.headytest.common.Constants;
import com.test.heady.headytest.features.home.presenter.HomePresenterInterface;
import com.test.heady.headytest.localdb.ApiResponseDatabase;
import com.test.heady.headytest.localdb.entities.CategoryEntity;
import com.test.heady.headytest.localdb.entities.ProductEntity;
import com.test.heady.headytest.localdb.entities.ProductVariantEntity;
import com.test.heady.headytest.localdb.entities.RankingProductsEntity;
import com.test.heady.headytest.networking.ApiInterface;
import com.test.heady.headytest.networking.ApiManager;
import com.test.heady.headytest.networking.ApiResponse;
import com.test.heady.headytest.networking.PojoModels.CategoryPojoModel;
import com.test.heady.headytest.networking.PojoModels.ProductPojoModel;
import com.test.heady.headytest.networking.PojoModels.RankingPojoModel;
import com.test.heady.headytest.networking.PojoModels.RankingProductPojoModel;
import com.test.heady.headytest.networking.PojoModels.VariantPojoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeInterActor {

    private static final String TAG = "HomeInterActor";
    private ApiResponseDatabase apiResponseDatabase;

    public HomeInterActor(ApiResponseDatabase apiResponseDatabase) {
        this.apiResponseDatabase = apiResponseDatabase;
    }

    public void getApiResponse(final HomePresenterInterface presenterTasks){
        Log.d(TAG, "getApiResponse() called");
        ApiInterface apiService =
                ApiManager.getClient().create(ApiInterface.class);

        Call<ApiResponse> call = apiService.getResponse();
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse>call, Response<ApiResponse> response) {
                List<CategoryPojoModel> categories = response.body().categories;
                List<RankingPojoModel> ranking = response.body().ranking;
                Log.d(TAG, "Number of categories received: " + categories.size());
                addCategoriesSectionToDb(categories);
                addRankingSectionToDb(ranking);
                presenterTasks.responseReceived();
            }

            @Override
            public void onFailure(Call<ApiResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    private void addRankingSectionToDb(List<RankingPojoModel> rankingPojoModels){
        for(int i = 0; i < rankingPojoModels.size(); i++){
            RankingPojoModel rankingPojoModel = rankingPojoModels.get(i);
            String rankingName  = rankingPojoModel.ranking;
            Log.i(TAG, "addRankingSectionToDb: ranking name " + rankingName);
            RankingSectionDatabaseAsync databaseAsync = new RankingSectionDatabaseAsync();
            databaseAsync.execute(rankingPojoModel);
        }
    }

    private void addCategoriesSectionToDb(List<CategoryPojoModel> categoryPojoModels){
        for(int i = 0; i < categoryPojoModels.size(); i++){
            CategoryPojoModel categoryPojoModel = categoryPojoModels.get(i);
            int id  = categoryPojoModel.id;
            Log.i(TAG, "addCategoriesSectionToDb: category id " + id);
            DatabaseAsync databaseAsync = new DatabaseAsync();
            databaseAsync.execute(categoryPojoModel);
        }
    }

    private class RankingSectionDatabaseAsync extends AsyncTask<RankingPojoModel, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(RankingPojoModel... rankingPojoModel) {
            RankingPojoModel rankingModel = rankingPojoModel[0];
            Log.i(TAG, "doInBackground: string  " + rankingModel.ranking);
            String rankingTitle = rankingModel.ranking;
            for (int i = 0; i < rankingModel.rankingProductPojoModel.size(); i++) {
                RankingProductPojoModel rankingProductPojoModel = rankingModel.rankingProductPojoModel.get(i);
                int productId = rankingProductPojoModel.id;
                ProductEntity productEntity = apiResponseDatabase.daoAccess().getProductRecord(productId);
                if(productEntity != null){
                    String productName = productEntity.getName();
                    if(rankingTitle.equalsIgnoreCase(Constants.MOST_VIEWED_PRODUCTS)){
                        addRankingProductEntityInDb(productId,productName,Constants.VIEW_COUNT,rankingProductPojoModel.viewCount);
                    }else if(rankingTitle.equalsIgnoreCase(Constants.MOST_ORDERED_PRODUCTS)){
                        addRankingProductEntityInDb(productId,productName,Constants.ORDER_COUNT,rankingProductPojoModel.orderCount);
                    }else if(rankingTitle.equalsIgnoreCase(Constants.MOST_SHARED_PRODUCTS)){
                        addRankingProductEntityInDb(productId,productName,Constants.SHARE_COUNT,rankingProductPojoModel.shares);
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        private void addRankingProductEntityInDb(int id, String name, String type, int count){
            RankingProductsEntity addProductEntity = new RankingProductsEntity();
            addProductEntity.setProduct_id(id);
            addProductEntity.setProduct_name(name);
            addProductEntity.setType(type);
            addProductEntity.setValue(count);
            apiResponseDatabase.daoAccess().insertRankingProductRecord(addProductEntity);
        }

    }


    private class DatabaseAsync extends AsyncTask<CategoryPojoModel, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Perform pre-adding operation here.
        }

        @Override
        protected Void doInBackground(CategoryPojoModel... categoryPojoModel) {
            //Let's add some dummy data to the database.
            Log.i(TAG, "doInBackground: id  " + categoryPojoModel[0].id);
            CategoryEntity categoryEntity = apiResponseDatabase.daoAccess().getCategoryRecord(categoryPojoModel[0].id);
            if(categoryEntity == null){
                Log.i(TAG, "addCategoriesSectionToDb: category is null");
                addCategoryEntityInDb(categoryPojoModel[0].id, categoryPojoModel[0].name, 0);
                addChildCategoriesForCategory(categoryPojoModel[0]);
                for (int i = 0; i < categoryPojoModel[0].products.size(); i++) {
                    ProductPojoModel productPojoModel = categoryPojoModel[0].products.get(i);
                    checkIfProductPojoExistsInDb(categoryPojoModel[0].id, productPojoModel);
                }
            }else{
                Log.i(TAG, "addCategoriesSectionToDb: category exists");
                if(!categoryEntity.getName().equalsIgnoreCase(categoryPojoModel[0].name)){
                    Log.i(TAG, "doInBackground: category name updated " + categoryPojoModel[0].name);
                    categoryEntity.setName(categoryPojoModel[0].name);
                    apiResponseDatabase.daoAccess().updateCategoryRecord(categoryEntity);
                    for (int i = 0; i < categoryPojoModel[0].products.size(); i++) {
                        ProductPojoModel productPojoModel = categoryPojoModel[0].products.get(i);
                        checkIfProductPojoExistsInDb(categoryPojoModel[0].id, productPojoModel);
                    }
                }
                addChildCategoriesForCategory(categoryPojoModel[0]);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        private void checkIfProductPojoExistsInDb(int categoryId,ProductPojoModel productPojoModel){
            ProductEntity productEntity = apiResponseDatabase.daoAccess().getProductRecord(productPojoModel.id);
            if(productEntity == null){
                addProductEntityInDb(productPojoModel, productPojoModel.id,productPojoModel.name,productPojoModel.tax.name, productPojoModel.tax.value,productPojoModel.dateAdded, categoryId);
            }
        }

        private void addProductEntityInDb(ProductPojoModel productPojoModel,int id, String name, String taxName, double taxValue, String dateAdded, int category_id){
            ProductEntity addProductEntity = new ProductEntity();
            addProductEntity.setDate_added(dateAdded);
            addProductEntity.setId(id);
            addProductEntity.setName(name);
            addProductEntity.setTax_name(taxName);
            addProductEntity.setTax_value(taxValue);
            addProductEntity.setCategory_id(category_id);
            Log.i(TAG, "addProductEntityInDb: product added " + id);
            apiResponseDatabase.daoAccess().insertProductRecord(addProductEntity);
            for (int i = 0; i < productPojoModel.variants.size(); i++) {
                VariantPojoModel variantPojoModel = productPojoModel.variants.get(i);
                checkIfVariantPojoExistsInDb(variantPojoModel, id);
            }
        }

        private void checkIfVariantPojoExistsInDb(VariantPojoModel variantPojoModel, int productId){
            ProductVariantEntity productVariantEntity = apiResponseDatabase.daoAccess().getVariantRecord(variantPojoModel.id);
            if(productVariantEntity == null){
                addProductVariantEntityInDb(productId, variantPojoModel.id,variantPojoModel.price, variantPojoModel.size,variantPojoModel.color);
            }
        }

        private void addProductVariantEntityInDb(int productId,int id, int price, int size, String color){
            ProductVariantEntity addProductVariantEntity = new ProductVariantEntity();
            addProductVariantEntity.setColor(color);
            addProductVariantEntity.setId(id);
            addProductVariantEntity.setPrice(price);
            addProductVariantEntity.setProduct_id(productId);
            addProductVariantEntity.setSize(size);
            Log.i(TAG, "addProductEntityInDb: product variant added " + id);
            apiResponseDatabase.daoAccess().insertProductVariantRecord(addProductVariantEntity);
        }

        private void addCategoryEntityInDb(int id, String name, int parentCategoryId){
            CategoryEntity addCategory = new CategoryEntity();
            addCategory.setId(id);
            addCategory.setName(name);
            addCategory.setParent_category_id(parentCategoryId);
            apiResponseDatabase.daoAccess().insertCategoryRecord(addCategory);
        }

        private void addChildCategoriesForCategory(CategoryPojoModel categoryPojoModel){
            for (int i = 0; i < categoryPojoModel.childCategories.size(); i++) {
                int childCategory = categoryPojoModel.childCategories.get(i);
                Log.i(TAG, "doInBackground: child category " + childCategory);
                CategoryEntity childCategoryEntity = apiResponseDatabase.daoAccess().getCategoryRecord(childCategory);
                if(childCategoryEntity == null){
                    Log.i(TAG, "doInBackground: child category is null");
                    addCategoryEntityInDb(childCategory, "", categoryPojoModel.id);
                }else{
                    Log.i(TAG, "doInBackground: child category is exists");
                    int parentCategoryId = childCategoryEntity.getParent_category_id();
                    if(parentCategoryId != categoryPojoModel.id){
                        childCategoryEntity.setParent_category_id(categoryPojoModel.id);
                        Log.i(TAG, "doInBackground: parent category id updated " + categoryPojoModel.id);
                        apiResponseDatabase.daoAccess().updateCategoryRecord(childCategoryEntity);
                    }
                }
            }
        }

    }
}
