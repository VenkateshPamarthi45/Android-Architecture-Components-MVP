package com.test.heady.headytest.networking;

import com.google.gson.annotations.SerializedName;
import com.test.heady.headytest.networking.PojoModels.CategoryPojoModel;
import com.test.heady.headytest.networking.PojoModels.RankingPojoModel;

import java.util.ArrayList;

public class ApiResponse {
    @SerializedName("categories")
    public ArrayList<CategoryPojoModel> categories = new ArrayList<>();
    @SerializedName("rankings")
    public ArrayList<RankingPojoModel> ranking = new ArrayList<>();
}
