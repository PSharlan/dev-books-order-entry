package com.netcracker.sharlan.controller;

import com.netcracker.sharlan.entity.Category;
import com.netcracker.sharlan.exception.EntityNotFoundException;
import com.netcracker.sharlan.exception.EntityNotUpdatedException;
import com.netcracker.sharlan.service.CategoryService;
import com.netcracker.sharlan.service.OfferService;
import com.netcracker.sharlan.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/catalog/categories")
@Api(value = "/api/v1/catalog/categories", description = "Manage categories")
public class CategoryController {

    private static final Logger LOGGER = LogManager.getLogger(CategoryController.class.getName());

    OfferService offerService;
    CategoryService categoryService;
    TagService tagService;

    @Autowired
    public CategoryController(OfferService offerService, CategoryService categoryService, TagService tagService){
        this.offerService = offerService;
        this.categoryService = categoryService;
        this.tagService = tagService;
    }

    @ApiOperation(value = "Return all existing categories")
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Category> getAllCategories() {
        LOGGER.info("Search for all categories");
        List<Category> foundCategories = categoryService.findAll();
        LOGGER.info("Found categories: "  + foundCategories);
        return foundCategories;
    }

    @ApiOperation(value = "Return category by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Category getCategoryById(
            @ApiParam(value = "Id of a category to lookup for", required = true)
            @PathVariable long id) {
        LOGGER.info("Searching for a category by id: " + id);
        Category category = categoryService.findById(id);
        if(category == null) {
            LOGGER.info("Category not found");
            throw new EntityNotFoundException(Category.class, id);
        }
        LOGGER.info("Found category: " + category);
        return category;
    }

    @ApiOperation(
            value = "Create category",
            notes = "Required category instance"
    )
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Category createCategory(
            @ApiParam(value = "Category instance")
            @RequestBody Category category) {
        LOGGER.info("Saving category: " + category);
        Category savedCategory = categoryService.save(category);
        LOGGER.info("Saved category: " + savedCategory + " with id: " + savedCategory.getId());
        return savedCategory;
    }

    @ApiOperation(
            value = "Update category",
            notes = "Required category instance"
    )
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Category updatedCategory(
            @ApiParam(value = "Category instance")
            @RequestBody Category category) {
        LOGGER.info("Updating category: " + category);
        Category updatedCategory = categoryService.update(category);
        if(updatedCategory == null) {
            LOGGER.info("Can not update not existing category");
            throw new EntityNotUpdatedException(Category.class, category.getId());
        }
        LOGGER.info("Updated category: " + updatedCategory);
        return updatedCategory;
    }

    @ApiOperation(value = "Delete category by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategory(
            @ApiParam(value = "Id of a category to delete", required = true)
            @PathVariable long id) {
        LOGGER.info("Deleting category with id: " + id);
        categoryService.delete(id);
        LOGGER.info("Category deleted");
    }

    @ApiOperation(
            value = "Create list of categories",
            notes = "List of categories is required"
    )
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createCategories(
            @ApiParam(value = "List of categories", required = true)
            @RequestBody Set<Category> categories) {
        LOGGER.info("Saving categories: " + categories);
        categoryService.saveAll(categories);
        LOGGER.info("Categories saved");
    }
}
