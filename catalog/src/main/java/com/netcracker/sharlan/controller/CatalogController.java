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

    @ApiOperation(value = "Return all offers")
    @RequestMapping(value = "/offers", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Set<Offer> getAllOffers() {
        return offerService.findAll();
    }

    @ApiOperation(value = "Return offer by id")
    @RequestMapping(value = "/offers/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Offer getOfferById(
            @ApiParam(value = "Id of offer to lookup for", required = true)
            @PathVariable long id) {
        return offerService.findById(id);
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/offers", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Offer createOffer(@RequestBody Offer offer) {
        return offerService.save(offer);
    }

    //@ApiOperation(value = "")
    @RequestMapping(value = "/offers", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Offer updatedOffer(@RequestBody Offer offer) {
        return offerService.update(offer);
    }

    //@ApiOperation(value = "")
    @RequestMapping(value = "/offers/{id}/categories", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updatedOfferCategory(@PathVariable long id, @RequestBody Category category) {
        offerService.updateCategory(offerService.findById(id), category);
    }

    //@ApiOperation(value = "")
    @RequestMapping(value = "/offers/{id}/tags", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void addOfferTag(@PathVariable long id, @RequestBody Tag tag) {
//        Offer offer = offerService.findById(id);
//        Set<Tag> set = offer.getTags();
//        set.add(tag);
//        offerService.updateTags(offer, set);
        offerService.addTag(offerService.findById(id), tag);
    }

    //@ApiOperation(value = "")
    @RequestMapping(value = "/offers/{id}/tags", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteOfferTag(@PathVariable long id, @RequestBody Tag tag) {
//        Offer offer = offerService.findById(id);
//        Set<Tag> set = offer.getTags();
//        set.remove(tag);
//        offerService.updateTags(offer, set);
        offerService.deleteTag(offerService.findById(id), tag);
    }

    //@ApiOperation(value = "")
    @RequestMapping(value = "/offers/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteOffer(@PathVariable long id) {
        offerService.delete(id);
    }

    //@ApiOperation(value = "")
    @RequestMapping(value = "/offers/filter", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Set<Offer> getOffersByParams(@RequestParam long categoryId,
                                  @RequestParam long tagId,
                                  @RequestParam double minPrice,
                                  @RequestParam double maxPrice) {
        return offerService.findByParams(categoryId, tagId, minPrice, maxPrice);
    }

    //@ApiOperation(value = "")
    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Category> getAllCategories() {
        return categoryService.findAll();
    }

    //@ApiOperation(value = "")
    @RequestMapping(value = "/categories/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Category getCategoryById(@PathVariable long id) {
        return categoryService.findById(id);
    }

    //@ApiOperation(value = "")
    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Category createCategory(@RequestBody Category category) {
        return categoryService.save(category);
    }

    //@ApiOperation(value = "")
    @RequestMapping(value = "/categories", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Category updatedCategory(@RequestBody Category category) {
        return categoryService.update(category);
    }

    //@ApiOperation(value = "")
    @RequestMapping(value = "/categories/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategory(@PathVariable long id) {
        categoryService.delete(id);
    }

    //@ApiOperation(value = "")
    @RequestMapping(value = "/categories/list", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createCategory(@RequestBody Set<Category> categories) {
        categoryService.saveAll(categories);
    }

    //@ApiOperation(value = "")
    @RequestMapping(value = "/tags", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Tag> getAllTags() {
        return tagService.findAll();
    }

    //@ApiOperation(value = "")
    @RequestMapping(value = "/tags/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Tag getTagById(@PathVariable long id) {
        return tagService.findById(id);
    }

    //@ApiOperation(value = "")
    @RequestMapping(value = "/tags", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Tag updateTag(@RequestBody Tag tag) {
        return tagService.update(tag);
    }

    //@ApiOperation(value = "")
    @RequestMapping(value = "/tags/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteTag(@PathVariable long id) {
        tagService.delete(id);
    }

    //@ApiOperation(value = "")
    @RequestMapping(value = "/tags", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Tag createTag(@RequestBody Tag tag) {
        return tagService.save(tag);
    }

    //@ApiOperation(value = "")
    @RequestMapping(value = "/tags/list", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createTag(@RequestBody Set<Tag> tags) {
        tagService.saveAll(tags);
    }
}
