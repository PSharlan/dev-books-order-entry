package com.devbooks.sharlan.controller;

import com.devbooks.sharlan.dto.TagDto;
import com.devbooks.sharlan.exception.EntityNotUpdatedException;
import com.devbooks.sharlan.service.TagService;
import com.devbooks.sharlan.entities.Category;
import com.devbooks.sharlan.entities.Tag;
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
@RequestMapping("/api/v1/catalog/tags")
@Api(value = "/api/v1/catalog/tags", description = "Manage tags")
public class TagController {

    private static final Logger LOGGER = LogManager.getLogger(TagController.class.getName());

    private TagService tagService;
    private ModelMapper modelMapper;

    @Autowired
    public TagController(ModelMapper modelMapper, TagService tagService){
        this.tagService = tagService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "Return all existing tags")
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<TagDto> getAllTags() {
        LOGGER.info("Search for all tags");
        List<Tag> foundTags = tagService.findAll();
        LOGGER.info("Found tags: "  + foundTags);
        return foundTags.stream()
                .map(tag -> convertToDto(tag))
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Return tag by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public TagDto getTagById(
            @ApiParam(value = "Id of tag to lookup for", required = true)
            @PathVariable long id) {
        LOGGER.info("Search for a tag with id: " + id);
        Tag tag = tagService.findById(id);
        if(tag == null) {
            LOGGER.info("Tag not found");
            throw new EntityNotFoundException(Tag.class, id);
        }
        LOGGER.info("Found tag: " + tag);
        return convertToDto(tag);
    }

    @ApiOperation(
            value = "Update tag",
            notes = "Required tag instance"
    )
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public TagDto updateTag(
            @ApiParam(value = "Tag instance")
            @Valid @RequestBody TagDto tagDto) {
        Tag tag = convertToEntity(tagDto);
        LOGGER.info("Updating tag: " + tag);
        LOGGER.info("Tag offers: " + tag.getOffers());
        Tag updatedTag = tagService.update(tag);
        if(updatedTag == null){
            LOGGER.info("Can not update not existing tag");
            throw new EntityNotUpdatedException(Category.class, tag.getId());
        }
        LOGGER.info("Updated tag: " + updatedTag);
        return convertToDto(updatedTag);
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
    public TagDto createTag(
            @ApiParam(value = "Tag instance")
            @Valid @RequestBody TagDto tagDto) {
        Tag tag = convertToEntity(tagDto);
        LOGGER.info("Saving tag: " + tag);
        Tag savedTag = tagService.save(tag);
        LOGGER.info("Saved tag: " + tag + " with id: " + tag.getId());
        return convertToDto(savedTag);
    }

    @ApiOperation(
            value = "Create list of tags",
            notes = "List of tags is required"
    )
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createTags(
            @ApiParam(value = "List of tags", required = true)
            @Valid @RequestBody Set<TagDto> tagsDto) {
        Set<Tag> tags = tagsDto.stream()
                .map(tag -> convertToEntity(tag))
                .collect(Collectors.toSet());
        LOGGER.info("Saving tags: " + tags);
        tagService.saveAll(tags);
        LOGGER.info("Tags saved");
    }

    private TagDto convertToDto(Tag tag) {
        TagDto tagDto = modelMapper.map(tag, TagDto.class);
        return tagDto;
    }

    private Tag convertToEntity(TagDto tagDto) {
        Tag tag = modelMapper.map(tagDto, Tag.class);
        return tag;
    }
}
