package com.devbooks.sharlan.service;

import com.devbooks.sharlan.dao.CategoryDao;
import com.devbooks.sharlan.entities.Category;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private static final Logger LOGGER = LogManager.getLogger(CategoryServiceImpl.class.getName());

    private CategoryDao dao;

    @Override
    @Transactional
    public Category findById(long id) {
        LOGGER.info("Searching for a category by id: " + id);
        Category category = dao.findById(id);
        LOGGER.info("Found category : " + category);
        return category;
    }

    @Override
    @Transactional
    public Category save(Category category) {
        LOGGER.info("Saving category : " + category);
        category = dao.save(category);
        LOGGER.info("Saved category : " + category);
        return category;
    }

    @Override
    @Transactional
    public void saveAll(Set<Category> categories) {
        LOGGER.info("Saving categories : " + categories);
        dao.saveAll(categories);
        LOGGER.info("Saved categories : " + categories);
    }

    @Override
    @Transactional
    public Category update(Category category) {
        LOGGER.info("Updating category : " + category);
        if(category.getId() == null || dao.findById(category.getId()) == null){
            LOGGER.info("Category with id : " + category.getId() + " not found. Category not updated");
            return null;
        }
        Category updatedCategory = dao.update(category);
        LOGGER.info("Updated category : " + updatedCategory);
        return updatedCategory;
    }

    @Override
    @Transactional
    public void delete(Category category) {
        delete(category.getId());
    }

    @Override
    @Transactional
    public List<Category> findAll() {
        LOGGER.info("Searching for all categories");
        List<Category> list = dao.findAll();
        LOGGER.info("Founded categories: " + list);
        return list;
    }

    @Override
    @Transactional
    public void delete(long id) {
        LOGGER.info("Deleting category with id: " + id);
        dao.delete(id);
        LOGGER.info("Deleted category with id: " + id);
    }
}
