package com.netcracker.sharlan.dao;

import com.netcracker.sharlan.entities.Category;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class CategoryDaoImpl extends AbstractDao<Category> implements CategoryDao {


    public CategoryDaoImpl(){
        super(Category.class);
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

    @Override
    public List<Category> findAll() {
        return getAll();
    }

    @Override
    public void delete(long id) {
        remove(findById(id));
    }

}
