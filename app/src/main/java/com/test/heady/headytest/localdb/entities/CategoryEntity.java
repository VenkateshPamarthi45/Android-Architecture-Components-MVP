package com.test.heady.headytest.localdb.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class CategoryEntity {

    @PrimaryKey
    private int id;
    private String name;
    private int parent_category_id;

    public CategoryEntity(int id, String name, int parent_category_id) {
        this.id = id;
        this.name = name;
        this.parent_category_id = parent_category_id;
    }

    public CategoryEntity() {
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

    public int getParent_category_id() {
        return parent_category_id;
    }

    public void setParent_category_id(int parent_category_id) {
        this.parent_category_id = parent_category_id;
    }

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parent_category_id=" + parent_category_id +
                '}';
    }
}
