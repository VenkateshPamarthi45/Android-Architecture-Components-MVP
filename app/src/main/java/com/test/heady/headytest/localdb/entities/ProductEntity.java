package com.test.heady.headytest.localdb.entities;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ProductEntity {
    @PrimaryKey
    private int id;
    private String name;
    private String date_added;
    private String tax_name;
    private double tax_value;
    private int category_id;

    public ProductEntity(int id, String name, String date_added, String tax_name, double tax_value, int category_id) {
        this.id = id;
        this.name = name;
        this.date_added = date_added;
        this.tax_name = tax_name;
        this.tax_value = tax_value;
        this.category_id = category_id;
    }

    public ProductEntity() {
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
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

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public String getTax_name() {
        return tax_name;
    }

    public void setTax_name(String tax_name) {
        this.tax_name = tax_name;
    }

    public double getTax_value() {
        return tax_value;
    }

    public void setTax_value(double tax_value) {
        this.tax_value = tax_value;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date_added='" + date_added + '\'' +
                ", tax_name='" + tax_name + '\'' +
                ", tax_value=" + tax_value +
                ", category_id=" + category_id +
                '}';
    }
}
