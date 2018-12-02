package com.netcracker.sharlan.dao;

import com.netcracker.sharlan.bean.Category;

import java.util.Set;

public interface CategoryDao {

    Category findById(long id);

    Category save(Category category);

    void saveAll(Set<Category> categories);

    Category update(Category category);

    void delete(Category category);

//    void beginTransaction();
//
//    void endTransaction();
//
//    void cancel();
}
