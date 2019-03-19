package com.netcracker.sharlan.service;

import com.netcracker.sharlan.entities.Category;
import com.netcracker.sharlan.entities.Offer;
import com.netcracker.sharlan.entities.Tag;

import java.util.List;
import java.util.Set;

/**
 * Provides the service for CRUD operations with offers.
 *
 * @see Offer
 *
 * @author Pavel Sharlan
 * @version  1.0
 */
public interface OfferService {

    /**
     * Returns all offers.
     *
     * @return - set of all offers.
     */
    Set<Offer> findAll();

    /**
     * Returns a single offer by id.
     *
     * @param id - offer id
     * @return - offer
     */
    Offer findById(long id);

    /**
     * Returns a list of an offers found by list of an id's.
     *
     * @param ids - offer ids
     * @return - list of found offers
     */
    List<Offer> findById(List<Integer> ids);

    /**
     * Saves single offer.
     *
     * @param offer - offer to save
     * @return saved offer
     */
    Offer save(Offer offer);

    /**
     * Updates single offer.
     * Note: Method will return null if offer would be not found in Database
     *
     * @param offer - offer to update
     * @return updated offer
     */
    Offer update(Offer offer);

    /**
     * Deletes single offer.
     *
     * @param offer - offer to delete
     */
    void delete(Offer offer);

    /**
     * Deletes single offer.
     *
     * @param id - id of the offer to delete
     */
    void delete(long id);

    /**
     * Returns a single offer by tag.
     *
     * @see Tag
     *
     * @param tag - tag
     * @return - offer
     */
    Set<Offer> findByTag(Tag tag);

    /**
     * Returns a single offer by category.
     *
     * @see Category
     *
     * @param category - category
     * @return - offer
     */
    Set<Offer> findByCategory(Category category);

    /**
     * Returns a list of an offers found by params.
     * Note: to avoid any parameter you have to specify it by '0'.
     * If all parameters would be specified as '0', method returns all existing offers.
     *
     * @param categoryId - filter by category id
     * @param tagIds - filter by list of tag ids
     * @param minPrice - filter by min price
     * @param maxPrice - filter by max price
     * @return - filtered offers by params
     */
    Set<Offer> findByParams(long categoryId, List<Long> tagIds, double minPrice, double maxPrice);

    /**
     * Adds a tag to an offer.
     * Note: you can add only already existing at Database tags.
     *
     * @param offer - offer to add new tag
     * @param tag - added tag
     * @return - updated offer
     */
    Offer addTag(Offer offer, Tag tag);

    /**
     * Deletes a tag from an offer.
     *
     * @param offer - offer to update
     * @param tag - tag to remove
     * @return - updated offer
     */
    Offer deleteTag(Offer offer, Tag tag);

    /**
     * Updates a tags of an offer.
     * Note: you can add only already existing at Database tags.
     *
     * @param offer - offer to update
     * @param tags - new tags
     * @return - updated offer
     */
    Offer updateTags(Offer offer, Set<Tag> tags);

    /**
     * Updates a tags of an offer.
     * Note: you can add only already existing at Database tags.
     *
     * @param offer - offer to updated
     * @param category - new category
     * @return - updated offer
     */
    Offer updateCategory(Offer offer, Category category);
}
