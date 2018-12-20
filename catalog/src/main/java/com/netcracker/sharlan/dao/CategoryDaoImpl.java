package com.netcracker.sharlan.dao;

import com.netcracker.sharlan.bean.Category;

import java.util.Set;

public class CategoryDaoImpl extends AbstractDao<Category> implements CategoryDao {


    public CategoryDaoImpl(){
        setPersistentClass(Category.class);
    }

    @Override
    public Category findById(long id) {
        return findOneById(id);
    }

    @Override
    public Category save(Category category) {
        return create(category);
    }

    @Override
    public void saveAll(Set<Category> categories) {
        for (Category cat: categories) {
            create(cat);
        }
    }

    @Override
    public Category update(Category category) {
        return merge(category);
    }

    @Override
    public void delete(Category category) {
        remove(category);
    }

}
