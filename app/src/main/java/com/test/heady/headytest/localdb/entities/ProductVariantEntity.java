package com.test.heady.headytest.localdb.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ProductVariantEntity {
    @PrimaryKey
    private int id;
    private String color;
    private int size;
    private int price;
    private int product_id;

    public ProductVariantEntity(int id, String color, int size, int price, int product_id) {
        this.id = id;
        this.color = color;
        this.size = size;
        this.price = price;
        this.product_id = product_id;
    }

    public ProductVariantEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    @Override
    public String toString() {
        return "ProductVariantEntity{" +
                "id=" + id +
                ", color='" + color + '\'' +
                ", size=" + size +
                ", price=" + price +
                ", product_id=" + product_id +
                '}';
    }
}
