package com.devbooks.sharlan.dao;

import com.devbooks.sharlan.entities.Category;

import java.util.List;
import java.util.Set;

public interface CategoryDao {

    Category findById(long id);

    Category save(Category category);

    void saveAll(Set<Category> categories);

    Category update(Category category);

    void delete(Category category);

    List<Category> findAll();

    void delete(long id);
}
