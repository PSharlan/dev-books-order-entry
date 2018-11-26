package com.netcracker.sharlan.hibernate.dao;

import com.netcracker.sharlan.bean.Category;
import com.netcracker.sharlan.bean.Offer;

import java.util.Set;

public interface CategoryDao {

    Category findById(int id);

    Category save(Category category);

    void saveAll(Set<Category> categories);

    Category update(Category category);

    void delete(Category category);

    void beginTransaction();

    void endTransaction();

    void cancel();
}
