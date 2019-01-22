package com.netcracker.sharlan.controller;

import com.netcracker.sharlan.entities.Category;
import com.netcracker.sharlan.entities.Offer;
import com.netcracker.sharlan.entities.Tag;
import com.netcracker.sharlan.service.CategoryService;
import com.netcracker.sharlan.service.OfferService;
import com.netcracker.sharlan.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api/v1/catalog")
public class CatalogController {

    @Autowired
    OfferService offerService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    TagService tagService;

    @RequestMapping(value = "/offers", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Set<Offer> getAllOffers() {
        return offerService.findAll();
    }

    @RequestMapping(value = "/offers/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Offer getOfferById(@PathVariable long id) {
        return offerService.findById(id);
    }

    @RequestMapping(value = "/offers", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Offer createOffer(@RequestBody Offer offer) {
        return offerService.save(offer);
    }

    @RequestMapping(value = "/offers", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Offer updatedOffer(@RequestBody Offer offer) {
        return offerService.update(offer);
    }

    @RequestMapping(value = "/offers/{id}/categories", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updatedOfferCategory(@PathVariable long id, @RequestBody Category category) {
        offerService.updateCategory(offerService.findById(id), category);
    }

    @RequestMapping(value = "/offers/{id}/tags", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void addOfferTag(@PathVariable long id, @RequestBody Tag tag) {
        Offer offer = offerService.findById(id);
        Set<Tag> set = offer.getTags();
        set.add(tag);
        offerService.updateTags(offer, set);
    }

    @RequestMapping(value = "/offers/{id}/tags", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteOfferTag(@PathVariable long id, @RequestBody Tag tag) {
        Offer offer = offerService.findById(id);
        Set<Tag> set = offer.getTags();
        set.remove(tag);
        offerService.updateTags(offer, set);
    }

    @RequestMapping(value = "/offers/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteOffer(@PathVariable long id) {
        offerService.delete(id);
    }

    @RequestMapping(value = "/offers/filter", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Set<Offer> getOffersByParams(@RequestParam long categoryId,
                                  @RequestParam long tagId,
                                  @RequestParam double startPrice,
                                  @RequestParam double endPrice) {

        Tag tagForFilter = null;
        if(tagId != 0){
            tagForFilter = tagService.findById(tagId);
        }

        Set<Offer> allOffers = offerService.findAll();
        Set<Offer> filteredOffers = new HashSet<>();
        for (Offer offer: allOffers) {
            if(categoryId == 0 || offer.getCategory().getId() == categoryId){
                if(tagForFilter == null || offer.getTags().contains(tagForFilter)){
                    if(startPrice == 0 || offer.getPrice() > startPrice){
                        if(endPrice == 0 || offer.getPrice() < endPrice){
                            filteredOffers.add(offer);
                        }
                    }
                }
            }
        }
        return filteredOffers;
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Category> getAllCategories() {
        return categoryService.findAll();
    }

    @RequestMapping(value = "/categories/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Category getCategoryById(@PathVariable long id) {
        return categoryService.findById(id);
    }

    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Category createCategory(@RequestBody Category category) {
        return categoryService.save(category);
    }

    @RequestMapping(value = "/categories", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Category updatedCategory(@RequestBody Category category) {
        return categoryService.update(category);
    }

    @RequestMapping(value = "/categories/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategory(@PathVariable long id) {
        categoryService.delete(id);
    }

    @RequestMapping(value = "/categories/list", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createCategory(@RequestBody Set<Category> categories) {
        categoryService.saveAll(categories);
    }

    @RequestMapping(value = "/tags", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Tag> getAllTags() {
        return tagService.findAll();
    }

    @RequestMapping(value = "/tags/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Tag getTagById(@PathVariable long id) {
        return tagService.findById(id);
    }

    @RequestMapping(value = "/tags", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Tag updateTag(@RequestBody Tag tag) {
        return tagService.update(tag);
    }

    @RequestMapping(value = "/tags/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteTag(@PathVariable long id) {
        tagService.delete(id);
    }

    @RequestMapping(value = "/tags", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Tag createTag(@RequestBody Tag tag) {
        return tagService.save(tag);
    }

    @RequestMapping(value = "/tags/list", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createTag(@RequestBody Set<Tag> tags) {
        tagService.saveAll(tags);
    }
}
