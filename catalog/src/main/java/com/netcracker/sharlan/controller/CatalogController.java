package com.netcracker.sharlan.controller;

import com.netcracker.sharlan.entities.Category;
import com.netcracker.sharlan.entities.Offer;
import com.netcracker.sharlan.entities.Tag;
import com.netcracker.sharlan.service.CategoryService;
import com.netcracker.sharlan.service.OfferService;
import com.netcracker.sharlan.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api/v1/catalog")
@Api(value = "/api/v1/catalog", description = "Manage offers")
public class CatalogController {

    @Autowired
    OfferService offerService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    TagService tagService;

    @ApiOperation(value = "Return all existing offers")
    @RequestMapping(value = "/offers", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Set<Offer> getAllOffers() {
        return offerService.findAll();
    }

    @ApiOperation(value = "Return offer by id")
    @RequestMapping(value = "/offers/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Offer getOfferById(
            @ApiParam(value = "Id of an offer to lookup for", required = true)
            @PathVariable long id) {
        return offerService.findById(id);
    }

    @ApiOperation(
            value = "Create offer",
            notes = "Required offer instance"
    )
    @RequestMapping(value = "/offers", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Offer createOffer(
            @ApiParam(value = "Offer instance", required = true)
            @RequestBody Offer offer) {
        return offerService.save(offer);
    }

    @ApiOperation(
            value = "Update offer",
            notes = "Required offer instance"
    )
    @RequestMapping(value = "/offers", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Offer updatedOffer(
            @ApiParam(value = "Offer instance", required = true)
            @RequestBody Offer offer) {
        return offerService.update(offer);
    }

    @ApiOperation(
            value = "Update category of existing offer",
            notes = "Instance of existing category is required"
    )
    @RequestMapping(value = "/offers/{id}/categories", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updatedOfferCategory(
            @ApiParam(value = "Id of an offer to update", required = true)
            @PathVariable long id,
            @ApiParam(value = "Category instance", required = true)
            @RequestBody Category category) {
        offerService.updateCategory(offerService.findById(id), category);
    }

    @ApiOperation(
            value = "Add new tag to existing offer",
            notes = "Instance of existing tag is required"
    )
    @RequestMapping(value = "/offers/{id}/tags", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void addOfferTag(
            @ApiParam(value = "Id of an offer to update", required = true)
            @PathVariable long id,
            @ApiParam(value = "Tag instance", required = true)
            @RequestBody Tag tag) {
        offerService.addTag(offerService.findById(id), tag);
    }

    @ApiOperation(
            value = "Delete tag from existing offer",
            notes = "Instance of existing tag is required"
    )
    @RequestMapping(value = "/offers/{id}/tags", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteOfferTag(
            @ApiParam(value = "Id of an offer to update", required = true)
            @PathVariable long id,
            @ApiParam(value = "Tag instance", required = true)
            @RequestBody Tag tag) {
        offerService.deleteTag(offerService.findById(id), tag);
    }

    @ApiOperation(value = "Delete offer by id")
    @RequestMapping(value = "/offers/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteOffer(
            @ApiParam(value = "Id of an offer to delete", required = true)
            @PathVariable long id) {
        offerService.delete(id);
    }

    @ApiOperation(value = "Return offers filtered by parameters")
    @RequestMapping(value = "/offers/filter", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Set<Offer> getOffersByParams(
            @ApiParam(value = "Category id")
            @RequestParam long categoryId,
            @ApiParam(value = "Tag id")
            @RequestParam long tagId,
            @ApiParam(value = "Min price")
            @RequestParam double minPrice,
            @ApiParam(value = "Max price")
            @RequestParam double maxPrice) {
        return offerService.findByParams(categoryId, tagId, minPrice, maxPrice);
    }

    @ApiOperation(value = "Return all existing categories")
    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Category> getAllCategories() {
        return categoryService.findAll();
    }

    @ApiOperation(value = "Return category by id")
    @RequestMapping(value = "/categories/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Category getCategoryById(
            @ApiParam(value = "Id of a category to lookup for", required = true)
            @PathVariable long id) {
        return categoryService.findById(id);
    }

    @ApiOperation(
            value = "Create category",
            notes = "Required category instance"
    )
    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Category createCategory(
            @ApiParam(value = "Category instance")
            @RequestBody Category category) {
        return categoryService.save(category);
    }

    @ApiOperation(
            value = "Update category",
            notes = "Required category instance"
    )
    @RequestMapping(value = "/categories", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Category updatedCategory(
            @ApiParam(value = "Category instance")
            @RequestBody Category category) {
        return categoryService.update(category);
    }

    @ApiOperation(value = "Delete category by id")
    @RequestMapping(value = "/categories/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategory(
            @ApiParam(value = "Id of a category to delete", required = true)
            @PathVariable long id) {
        categoryService.delete(id);
    }

    @ApiOperation(
            value = "Create list of categories",
            notes = "List of categories is required"
    )
    @RequestMapping(value = "/categories/list", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createCategory(
            @ApiParam(value = "List of categories", required = true)
            @RequestBody Set<Category> categories) {
        categoryService.saveAll(categories);
    }

    @ApiOperation(value = "Return all existing tags")
    @RequestMapping(value = "/tags", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Tag> getAllTags() {
        return tagService.findAll();
    }

    @ApiOperation(value = "Return tag by id")
    @RequestMapping(value = "/tags/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Tag getTagById(
            @ApiParam(value = "Id of tag to lookup for", required = true)
            @PathVariable long id) {
        return tagService.findById(id);
    }

    @ApiOperation(
            value = "Create tag",
            notes = "Required tag instance"
    )
    @RequestMapping(value = "/tags", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Tag updateTag(
            @ApiParam(value = "Tag instance")
            @RequestBody Tag tag) {
        return tagService.update(tag);
    }

    @ApiOperation(value = "Delete tag by id")
    @RequestMapping(value = "/tags/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteTag(
            @ApiParam(value = "Id of a tag to delete", required = true)
            @PathVariable long id) {
        tagService.delete(id);
    }

    @ApiOperation(
            value = "Create tag",
            notes = "Required tag instance"
    )
    @RequestMapping(value = "/tags", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Tag createTag(
            @ApiParam(value = "Tag instance")
            @RequestBody Tag tag) {
        return tagService.save(tag);
    }

    @ApiOperation(
            value = "Create list of tags",
            notes = "List of tags is required"
    )
    @RequestMapping(value = "/tags/list", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createTag(
            @ApiParam(value = "List of tags", required = true)
            @RequestBody Set<Tag> tags) {
        tagService.saveAll(tags);
    }
}
