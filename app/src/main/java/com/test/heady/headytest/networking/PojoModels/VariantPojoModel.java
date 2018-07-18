package com.test.heady.headytest.networking.PojoModels;

import com.google.gson.annotations.SerializedName;

public class VariantPojoModel {
    @SerializedName("id")
    public int id;
    @SerializedName("color")
    public String color;
    @SerializedName("size")
    public int size;
    @SerializedName("price")
    public int price;

    public VariantPojoModel(int id, String color, int size, int price) {
        this.id = id;
        this.color = color;
        this.size = size;
        this.price = price;
    }
}
