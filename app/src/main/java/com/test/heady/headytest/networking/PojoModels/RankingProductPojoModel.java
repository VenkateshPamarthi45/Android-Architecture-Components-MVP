package com.test.heady.headytest.networking.PojoModels;

import com.google.gson.annotations.SerializedName;

public class RankingProductPojoModel {

    @SerializedName("id")
    public int id;
    @SerializedName("order_count")
    public int orderCount;
    @SerializedName("view_count")
    public int viewCount;
    @SerializedName("shares")
    public int shares;



}
