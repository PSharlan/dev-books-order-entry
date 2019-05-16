package com.devbooks.sharlan.controller;

import com.devbooks.sharlan.dto.CategoryDto;
import com.devbooks.sharlan.exception.EntityNotUpdatedException;
import com.devbooks.sharlan.service.CategoryService;
import com.devbooks.sharlan.entities.Category;
import com.devbooks.sharlan.exception.EntityNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/catalog/categories")
@Api(value = "/api/v1/catalog/categories", description = "Manage categories")
public class CategoryController {

    private static final Logger LOGGER = LogManager.getLogger(CategoryController.class.getName());

    private CategoryService categoryService;
    private ModelMapper modelMapper;

    @Autowired
    public CategoryController(ModelMapper modelMapper, CategoryService categoryService){
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "Return all existing categories")
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> getAllCategories() {
        LOGGER.info("Search for all categories");
        List<Category> foundCategories = categoryService.findAll();
        LOGGER.info("Found categories: "  + foundCategories);
        return foundCategories.stream()
                .map(category -> convertToDto(category))
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Return category by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto getCategoryById(
            @ApiParam(value = "Id of a category to lookup for", required = true)
            @PathVariable long id) {
        LOGGER.info("Searching for a category by id: " + id);
        Category category = categoryService.findById(id);
        if(category == null) {
            LOGGER.info("Category not found");
            throw new EntityNotFoundException(Category.class, id);
        }
        LOGGER.info("Found category: " + category);
        return convertToDto(category);
    }

    @ApiOperation(
            value = "Create category",
            notes = "Required category instance"
    )
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(
            @ApiParam(value = "Category instance")
            @Valid @RequestBody CategoryDto categoryDto) {
        Category category = convertToEntity(categoryDto);
        LOGGER.info("Saving category: " + category);
        Category savedCategory = categoryService.save(category);
        LOGGER.info("Saved category: " + savedCategory + " with id: " + savedCategory.getId());
        return convertToDto(savedCategory);
    }

    @ApiOperation(
            value = "Update category",
            notes = "Required category instance"
    )
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto updatedCategory(
            @ApiParam(value = "Category instance")
            @Valid @RequestBody CategoryDto categoryDto) {
        Category category = convertToEntity(categoryDto);
        LOGGER.info("Updating category: " + category);
        Category updatedCategory = categoryService.update(category);
        if(updatedCategory == null) {
            LOGGER.info("Can not update not existing category");
            throw new EntityNotUpdatedException(Category.class, category.getId());
        }
        LOGGER.info("Updated category: " + updatedCategory);
        return convertToDto(updatedCategory);
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
            @Valid @RequestBody Set<CategoryDto> categoriesDto) {
        Set<Category> categories = categoriesDto.stream()
                .map(category -> convertToEntity(category))
                .collect(Collectors.toSet());
        LOGGER.info("Saving categories: " + categories);
        categoryService.saveAll(categories);
        LOGGER.info("Categories saved");
    }

    private CategoryDto convertToDto(Category category) {
        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        return categoryDto;
    }

    private Category convertToEntity(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        return category;
    }
}
