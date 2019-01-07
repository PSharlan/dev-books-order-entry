package com.netcracker.sharlan.service;

import com.netcracker.sharlan.entities.Category;

import java.util.Set;

public interface CategoryService {

    Category findById(long id);

    Category save(Category category);

    void saveAll(Set<Category> categories);

    Category update(Category category);

    void delete(Category category);

}
