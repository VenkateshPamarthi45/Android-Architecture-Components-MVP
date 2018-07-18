package com.test.heady.headytest.networking.PojoModels;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CategoryPojoModel {

    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("products")
    public ArrayList<ProductPojoModel> products = new ArrayList<>();
    @SerializedName("child_categories")
    public ArrayList<Integer> childCategories = new ArrayList<>();

    public CategoryPojoModel(int id, String name, ArrayList<ProductPojoModel> products, ArrayList<Integer> childCategories) {
        this.id = id;
        this.name = name;
        this.products = products;
        this.childCategories = childCategories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProducts(ArrayList<ProductPojoModel> products) {
        this.products = products;
    }

    public ArrayList<ProductPojoModel> getProducts() {
        return products;
    }

    public ArrayList<Integer> getChildCategories() {
        return childCategories;
    }

    public void setChildCategories(ArrayList<Integer> childCategories) {
        this.childCategories = childCategories;
    }
}
