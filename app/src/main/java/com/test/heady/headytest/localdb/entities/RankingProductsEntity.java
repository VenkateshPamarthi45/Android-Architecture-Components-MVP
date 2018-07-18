package com.test.heady.headytest.localdb.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class RankingProductsEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String product_name;
    private String type;
    private int value;
    private int product_id;

    public RankingProductsEntity(int id, String product_name, String type, int value, int product_id) {
        this.id = id;
        this.product_name = product_name;
        this.type = type;
        this.value = value;
        this.product_id = product_id;
    }

    public RankingProductsEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
}
