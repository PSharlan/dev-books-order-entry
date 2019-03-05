package com.netcracker.sharlan.controller;

import com.netcracker.sharlan.entity.Category;
import com.netcracker.sharlan.entity.Offer;
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
@RequestMapping("/api/v1/catalog/offers")
@Api(value = "/api/v1/catalog/offers", description = "Manage offers")
public class OfferController {

    private static final Logger LOGGER = LogManager.getLogger(OfferController.class.getName());

    OfferService offerService;
    CategoryService categoryService;
    TagService tagService;

    @Autowired
    public OfferController(OfferService offerService, CategoryService categoryService, TagService tagService){
        this.offerService = offerService;
        this.categoryService = categoryService;
        this.tagService = tagService;
    }

    @ApiOperation(value = "Return all existing offers")
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Set<Offer> getAllOffers() {
        LOGGER.info("Searching for all offers");
        Set<Offer> allOffers = offerService.findAll();
        LOGGER.info("Found offers: " + allOffers);
        return allOffers;
    }

    @ApiOperation(value = "Return list of an offers by ids")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Offer> getOffersByIds(@RequestParam List<Integer> ids) {
        LOGGER.info("Searching for offers by ids: " + ids);
        List<Offer> foundOffers = offerService.findById(ids);
        LOGGER.info("Found offers: " + foundOffers);
        return foundOffers;
    }

    @ApiOperation(value = "Return offer by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
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
    @RequestMapping(method = RequestMethod.POST)
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
    @RequestMapping(method = RequestMethod.PUT)
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
    @RequestMapping(value = "/{id}/categories", method = RequestMethod.PUT)
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
    @RequestMapping(value = "/{id}/tags", method = RequestMethod.PUT)
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
    @RequestMapping(value = "/{id}/tags", method = RequestMethod.DELETE)
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
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteOffer(
            @ApiParam(value = "Id of an offer to delete", required = true)
            @PathVariable long id) {
        LOGGER.info("Deleting offer with id: " + id);
        offerService.delete(id);
        LOGGER.info("Offer deleted");
    }

    @ApiOperation(value = "Return offers filtered by parameters")
    @RequestMapping(value = "/filter", method = RequestMethod.GET)
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
}
