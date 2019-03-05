package com.netcracker.sharlan.controller;

import com.netcracker.sharlan.entity.Category;
import com.netcracker.sharlan.entity.Tag;
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
@RequestMapping("/api/v1/catalog/tags")
@Api(value = "/api/v1/catalog/tags", description = "Manage tags")
public class TagController {

    private static final Logger LOGGER = LogManager.getLogger(TagController.class.getName());

    OfferService offerService;
    CategoryService categoryService;
    TagService tagService;

    @Autowired
    public TagController(OfferService offerService, CategoryService categoryService, TagService tagService){
        this.offerService = offerService;
        this.categoryService = categoryService;
        this.tagService = tagService;
    }

    @ApiOperation(value = "Return all existing tags")
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Tag> getAllTags() {
        LOGGER.info("Search for all tags");
        List<Tag> foundTags = tagService.findAll();
        LOGGER.info("Found tags: "  + foundTags);
        return foundTags;
    }

    @ApiOperation(value = "Return tag by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
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
    @RequestMapping(method = RequestMethod.PUT)
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
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
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
    @RequestMapping(method = RequestMethod.POST)
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
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createTags(
            @ApiParam(value = "List of tags", required = true)
            @RequestBody Set<Tag> tags) {
        LOGGER.info("Saving tags: " + tags);
        tagService.saveAll(tags);
        LOGGER.info("Tags saved");
    }
}
