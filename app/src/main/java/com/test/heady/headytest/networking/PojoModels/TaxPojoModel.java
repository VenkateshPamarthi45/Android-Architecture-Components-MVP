package com.test.heady.headytest.networking.PojoModels;

import com.google.gson.annotations.SerializedName;

public class TaxPojoModel {

    @SerializedName("name")
    public String name;
    @SerializedName("value")
    public double value;

    public TaxPojoModel(String name, double value) {
        this.name = name;
        this.value = value;
    }
}
