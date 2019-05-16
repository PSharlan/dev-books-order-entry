package com.devbooks.sharlan.service;

import com.devbooks.sharlan.entities.Category;

import java.util.List;
import java.util.Set;

/**
 * Provides the service for CRUD operations with categories.
 *
 * @see Category
 *
 * @author Pavel Sharlan
 * @version  1.0
 */
public interface CategoryService {

    /**
     * Returns a single category by id.
     *
     * @param id - category id
     * @return - category
     */
    Category findById(long id);

    /**
     * Returns all categories.
     *
     * @return - list of all categories.
     */
    List<Category> findAll();

    /**
     * Saves single category.
     *
     * @param category - category to save
     * @return saved category
     */
    Category save(Category category);

    /**
     * Saves set of categories.
     *
     * @param categories - categories to save
     */
    void saveAll(Set<Category> categories);

    /**
     * Updates single category.
     * Note: Method will return null if category would be not found in Database
     *
     * @param category - category to update
     * @return updated category
     */
    Category update(Category category);

    /**
     * Deletes single category.
     * Note: category should not be associated with any offers.
     *
     * @param category - category to delete
     */
    void delete(Category category);

    /**
     * Deletes single category.
     * Note: category should not be associated with any offers.
     *
     * @param id -  id of the category to delete
     */
    void delete(long id);
}
