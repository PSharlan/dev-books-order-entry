package com.devbooks.sharlan.controller;

import com.devbooks.sharlan.dto.CategoryDto;
import com.devbooks.sharlan.dto.OfferDto;
import com.devbooks.sharlan.dto.TagDto;
import com.devbooks.sharlan.entities.Category;
import com.devbooks.sharlan.entities.Offer;
import com.devbooks.sharlan.entities.Tag;
import com.devbooks.sharlan.exception.EntityNotUpdatedException;
import com.devbooks.sharlan.service.OfferService;
import com.devbooks.sharlan.exception.EntityNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/catalog/offers")
@Api(value = "/api/v1/catalog/offers", description = "Manage offers")
public class OfferController {

    private static final Logger LOGGER = LogManager.getLogger(OfferController.class.getName());

    private OfferService offerService;
    private ModelMapper modelMapper;

    @Autowired
    public OfferController(ModelMapper modelMapper, OfferService offerService){
        this.offerService = offerService;
        this.modelMapper = modelMapper;
    }

//    @GetMapping(
//            value = "/image",
//            produces = MediaType.IMAGE_JPEG_VALUE
//    )
//    public byte[] getImageWithMediaType() throws IOException {
//        InputStream in = getClass().getClassLoader()
//                .getResourceAsStream("images/image.jpg");
//        return IOUtils.toByteArray(in);
//    }

    @ApiOperation(value = "Return all existing offers")
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Set<OfferDto> getAllOffers() {
        LOGGER.info("Searching for all offers");
        Set<Offer> allOffers = offerService.findAll();
        LOGGER.info("Found offers: " + allOffers);
        return allOffers.stream()
                .map(offer -> convertToDto(offer))
                .collect(Collectors.toSet());
    }

    @ApiOperation(value = "Return list of an offers by ids")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<OfferDto> getOffersByIds(@RequestParam List<Integer> ids) {
        LOGGER.info("Searching for offers by ids: " + ids);
        List<Offer> foundOffers = offerService.findById(ids);
        LOGGER.info("Found offers: " + foundOffers);
        return foundOffers.stream()
                .map(offer -> convertToDto(offer))
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Return offer by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public OfferDto getOfferById(
            @ApiParam(value = "Id of an offer to lookup for", required = true)
            @PathVariable long id) {

        LOGGER.info("Searching for an offer with id: " + id);
        Offer offer = offerService.findById(id);
        if(offer == null) {
            LOGGER.info("Offer not found");
            throw new EntityNotFoundException(Offer.class, id);
        }
        LOGGER.info("Found offer: " + offer);
        return convertToDto(offer);
    }

    @ApiOperation(
            value = "Create offer",
            notes = "Required offer instance"
    )
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public OfferDto createOffer(
            @ApiParam(value = "Offer instance", required = true)
            @Valid @RequestBody OfferDto offerDto) {
        Offer offer = convertToEntity(offerDto);
        LOGGER.info("Saving offer: " + offer);
        Offer savedOffer = offerService.save(offer);
        LOGGER.info("Saved offer id: " + offer.getId());
        return convertToDto(savedOffer);
    }

    @ApiOperation(
            value = "Update offer",
            notes = "Required offer instance"
    )
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public OfferDto updatedOffer(
            @ApiParam(value = "Offer instance", required = true)
            @Valid @RequestBody OfferDto offerDto) {
        Offer offer = convertToEntity(offerDto);
        LOGGER.info("Updating offer: " + offer);
        LOGGER.info("Updating offer tags: " + offer.getTags());
        Offer updatedOffer = offerService.update(offer);
        if(updatedOffer == null) {
            LOGGER.info("Can not update not existing offer");
            throw new EntityNotUpdatedException(Offer.class, offer.getId());
        }
        LOGGER.info("Updated offer: " + offer);
        return convertToDto(updatedOffer);
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
            @Valid @RequestBody CategoryDto categoryDto) {
        Category category = convertToEntity(categoryDto);
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
            @Valid @RequestBody TagDto tagDto) {
        Tag tag = convertToEntity(tagDto);
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
            @RequestBody TagDto tagDto) {
        Tag tag = convertToEntity(tagDto);
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
    public Set<OfferDto> getOffersByParams(
            @ApiParam(value = "Category id")
            @RequestParam long categoryId,
            @ApiParam(value = "Tag id")
            @RequestParam List<Long> tagIds,
            @ApiParam(value = "Min price")
            @RequestParam double minPrice,
            @ApiParam(value = "Max price")
            @RequestParam double maxPrice) {
        LOGGER.info("Search an offers by params. " +
                "Category id: " + categoryId +
                " | Tag ids: " + tagIds +
                " | Min price: " + minPrice +
                " | Max price: " + maxPrice);
        Set<Offer> foundOffers = offerService.findByParams(categoryId, tagIds, minPrice, maxPrice);
        LOGGER.info("Found offers: " + foundOffers);
        return foundOffers.stream()
                .map(offer -> convertToDto(offer))
                .collect(Collectors.toSet());
    }

    private OfferDto convertToDto(Offer offer) {
        OfferDto offerDto = modelMapper.map(offer, OfferDto.class);
        return offerDto;
    }

    private CategoryDto convertToDto(Category category) {
        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        return categoryDto;
    }

    private TagDto convertToDto(Tag tag) {
        TagDto tagDto = modelMapper.map(tag, TagDto.class);
        return tagDto;
    }

    private Offer convertToEntity(OfferDto offerDto) {
        Offer offer = modelMapper.map(offerDto, Offer.class);
        return offer;
    }

    private Category convertToEntity(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        return category;
    }

    private Tag convertToEntity(TagDto tagDto) {
        Tag tag = modelMapper.map(tagDto, Tag.class);
        return tag;
    }
}
