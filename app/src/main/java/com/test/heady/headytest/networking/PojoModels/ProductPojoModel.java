package com.test.heady.headytest.networking.PojoModels;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductPojoModel {
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("date_added")
    public String dateAdded;
    @SerializedName("variants")
    public ArrayList<VariantPojoModel> variants = new ArrayList<>();
    @SerializedName("tax")
    public TaxPojoModel tax;


    public ProductPojoModel(int id, String name, String dateAdded, ArrayList<VariantPojoModel> variants, TaxPojoModel tax) {
        this.id = id;
        this.name = name;
        this.dateAdded = dateAdded;
        this.variants = variants;
        this.tax = tax;
    }
}
