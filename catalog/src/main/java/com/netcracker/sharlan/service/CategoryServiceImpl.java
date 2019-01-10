package com.netcracker.sharlan.service;

import com.netcracker.sharlan.dao.CategoryDao;
import com.netcracker.sharlan.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private CategoryDao dao;

    @Autowired
    public CategoryServiceImpl(CategoryDao dao){
        this.dao = dao;
    }

    @Override
    public Category findById(long id) {
        return dao.findById(id);
    }

    @Override
    public Category save(Category category) {
        return dao.save(category);
    }

    @Override
    public void saveAll(Set<Category> categories) {
        dao.saveAll(categories);
    }

    @Override
    public Category update(Category category) {
        return dao.update(category);
    }

    @Override
    public void delete(Category category) {
        dao.delete(category);
    }
}
