package com.netcracker.sharlan.controller;

import com.netcracker.sharlan.entities.Category;
import com.netcracker.sharlan.entities.Offer;
import com.netcracker.sharlan.entities.Tag;
import com.netcracker.sharlan.exceptions.EntityNotFoundException;
import com.netcracker.sharlan.exceptions.EntityNotUpdatedException;
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
@RequestMapping("/api/v1/catalog")
@Api(value = "/api/v1/catalog", description = "Manage offers")
public class CatalogController {

    private static final Logger LOGGER = LogManager.getLogger(CatalogController.class.getName());

    OfferService offerService;
    CategoryService categoryService;
    TagService tagService;

    @Autowired
    public CatalogController(OfferService offerService, CategoryService categoryService, TagService tagService){
        this.offerService = offerService;
        this.categoryService = categoryService;
        this.tagService = tagService;
    }

    @ApiOperation(value = "Return all existing offers")
    @RequestMapping(value = "/offers", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Set<Offer> getAllOffers() {
        LOGGER.info("Searching for all offers");
        Set<Offer> allOffers = offerService.findAll();
        LOGGER.info("Found offers: " + allOffers);
        return allOffers;
    }

    @ApiOperation(value = "Return list of an offers by ids")
    @RequestMapping(value = "/offers/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Offer> getOffersByIds(@RequestParam List<Integer> ids) {
        LOGGER.info("Searching for offers by ids: " + ids);
        List<Offer> foundOffers = offerService.findById(ids);
        LOGGER.info("Found offers: " + foundOffers);
        return foundOffers;
    }

    @ApiOperation(value = "Return offer by id")
    @RequestMapping(value = "/offers/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Offer getOfferById(
            @ApiParam(value = "Id of an offer to lookup for", required = true)
            @PathVariable long id) {

        LOGGER.info("Searching for an offer with id: " + id);
        Offer offer = offerService.findById(id);
        if(offer == null) {
            LOGGER.info("Offer not found");
            throw new EntityNotFoundException(Offer.class, id);
        }
        LOGGER.info("Found offer: " + offer);
        return offer;
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
        LOGGER.info("Saving offer: " + offer);
        Offer savedOffer = offerService.save(offer);
        LOGGER.info("Saved offer id: " + offer.getId());
        return savedOffer;
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
        LOGGER.info("Updating offer: " + offer);
        Offer updatedOffer = offerService.update(offer);
        if(updatedOffer == null) {
            LOGGER.info("Can not update not existing offer");
            throw new EntityNotUpdatedException(Offer.class, offer.getId());
        }
        LOGGER.info("Updated offer: " + offer);
        return updatedOffer;
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
        LOGGER.info("New category: " + category + " for offer id: " + id );
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
        LOGGER.info("New tag: " + tag + " for offer id: " + id );
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
        LOGGER.info("Removing tag: " + tag + " for offer id: " + id );
        offerService.deleteTag(offerService.findById(id), tag);
    }

    @ApiOperation(value = "Delete offer by id")
    @RequestMapping(value = "/offers/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteOffer(
            @ApiParam(value = "Id of an offer to delete", required = true)
            @PathVariable long id) {
        LOGGER.info("Deleting offer with id: " + id);
        offerService.delete(id);
        LOGGER.info("Offer deleted");
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
        LOGGER.info("Search an offers by params. " +
                "Category id: " + categoryId +
                " | Tag id: " + tagId +
                " | Min price: " + minPrice +
                " | Max price: " + maxPrice);
        Set<Offer> foundOffers = offerService.findByParams(categoryId, tagId, minPrice, maxPrice);
        LOGGER.info("Found offers: " + foundOffers);
        return foundOffers;
    }

    @ApiOperation(value = "Return all existing categories")
    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Category> getAllCategories() {
        LOGGER.info("Search for all categories");
        List<Category> foundCategories = categoryService.findAll();
        LOGGER.info("Found categories: "  + foundCategories);
        return foundCategories;
    }

    @ApiOperation(value = "Return category by id")
    @RequestMapping(value = "/categories/{id}", method = RequestMethod.GET)
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
    @RequestMapping(value = "/categories", method = RequestMethod.POST)
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
    @RequestMapping(value = "/categories", method = RequestMethod.PUT)
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
    @RequestMapping(value = "/categories/{id}", method = RequestMethod.DELETE)
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
    @RequestMapping(value = "/categories/list", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createCategories(
            @ApiParam(value = "List of categories", required = true)
            @RequestBody Set<Category> categories) {
        LOGGER.info("Saving categories: " + categories);
        categoryService.saveAll(categories);
        LOGGER.info("Categories saved");
    }

    @ApiOperation(value = "Return all existing tags")
    @RequestMapping(value = "/tags", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Tag> getAllTags() {
        LOGGER.info("Search for all tags");
        List<Tag> foundTags = tagService.findAll();
        LOGGER.info("Found tags: "  + foundTags);
        return foundTags;
    }

    @ApiOperation(value = "Return tag by id")
    @RequestMapping(value = "/tags/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Tag getTagById(
            @ApiParam(value = "Id of tag to lookup for", required = true)
            @PathVariable long id) {
        LOGGER.info("Search for a tag with id: " + id);
        Tag tag = tagService.findById(id);
        if(tag == null) {
            LOGGER.info("Tag not found");
            throw new EntityNotFoundException(Tag.class, id);
        }
        LOGGER.info("Found tag: " + tag);
        return tag;
    }

    @ApiOperation(
            value = "Update tag",
            notes = "Required tag instance"
    )
    @RequestMapping(value = "/tags", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Tag updateTag(
            @ApiParam(value = "Tag instance")
            @RequestBody Tag tag) {
        LOGGER.info("Updating tag: " + tag);
        LOGGER.info("Tag offers: " + tag.getOffers());
        Tag updatedTag = tagService.update(tag);
        if(updatedTag == null){
            LOGGER.info("Can not update not existing tag");
            throw new EntityNotUpdatedException(Category.class, tag.getId());
        }
        LOGGER.info("Updated tag: " + updatedTag);
        return updatedTag;
    }

    @ApiOperation(value = "Delete tag by id")
    @RequestMapping(value = "/tags/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteTag(
            @ApiParam(value = "Id of a tag to delete", required = true)
            @PathVariable long id) {
        LOGGER.info("Deleting tag with id: " + id);
        tagService.delete(id);
        LOGGER.info("Tag deleted");
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
        LOGGER.info("Saving tag: " + tag);
        Tag savedTag = tagService.save(tag);
        LOGGER.info("Saved tag: " + tag + " with id: " + tag.getId());
        return savedTag;
    }

    @ApiOperation(
            value = "Create list of tags",
            notes = "List of tags is required"
    )
    @RequestMapping(value = "/tags/list", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createTags(
            @ApiParam(value = "List of tags", required = true)
            @RequestBody Set<Tag> tags) {
        LOGGER.info("Saving tags: " + tags);
        tagService.saveAll(tags);
        LOGGER.info("Tags saved");
    }
}
