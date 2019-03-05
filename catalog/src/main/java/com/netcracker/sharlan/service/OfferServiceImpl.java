package com.netcracker.sharlan.service;

import com.netcracker.sharlan.dao.OfferDao;
import com.netcracker.sharlan.entity.Category;
import com.netcracker.sharlan.entity.Offer;
import com.netcracker.sharlan.entity.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class OfferServiceImpl implements OfferService {

    private static final Logger LOGGER = LogManager.getLogger(OfferServiceImpl.class.getName());

    private OfferDao offerDao;
    private TagService tagService;

    @Autowired
    public OfferServiceImpl(OfferDao offerDao, TagService tagService){
        this.offerDao = offerDao;
        this.tagService = tagService;
    }

    @Override
    public Set<Offer> findAll() {
        LOGGER.info("Searching for all offers");
        Set<Offer> set = offerDao.findAll();
        return set;
    }

    @Override
    public Offer findById(long id) {
        LOGGER.info("Searching for offer by id: " + id);
        Offer offer = offerDao.findById(id);
        if(offer != null) {
            LOGGER.info("Founded offer: " + offer);
            offer.getTags().size();
        }else{
            LOGGER.info("Offer not found");
        }
        return offer;
    }

    @Override
    public List<Offer> findById(List<Integer> ids) {
        LOGGER.info("Searching for offers by ids: " + ids);
        Offer offer;
        List<Offer> foundOfers = new ArrayList<>();
        for (Integer id : ids) {
            LOGGER.info("Searching for offer by id: " + id);
            offer = offerDao.findById(id);
            if(offer != null) {
                LOGGER.info("Founded offer: " + offer);
                offer.getTags().size();
                foundOfers.add(offer);
            }else{
                LOGGER.info("Offer not found");
            }
        }
        return foundOfers;
    }

    @Override
    public Offer save(Offer offer) {
        LOGGER.info("Saving offer : " + offer);
        offer = offerDao.save(offer);
        LOGGER.info("Saved offer : " + offer);
        return offer;
    }

    @Override
    public Offer update(Offer offer) {
        LOGGER.info("Updating offer : " + offer);
        if(offer.getId() == null || offerDao.findById(offer.getId()) == null){
            LOGGER.info("Offer with id: " + offer.getId() + " not found. Offer not updated");
            return null;
        }
        Offer updatedOffer = offerDao.update(offer);
        LOGGER.info("Updated offer : " + updatedOffer);
        return updatedOffer;
    }

    @Override
    public void delete(Offer offer) {
        delete(offer.getId());
    }

    @Override
    public void delete(long id) {
        LOGGER.info("Deleting offer with id: " + id);
        offerDao.delete(id);
        LOGGER.info("Deleted offer with id: " + id);
    }

    @Override
    public Set<Offer> findByTag(Tag tag) {
        LOGGER.info("Searching for offers by tag: " + tag);
        Set<Offer> set = offerDao.findByTag(tag);
        LOGGER.info("Founded offers: " + set);
        return set;
    }

    @Override
    public Set<Offer> findByCategory(Category category) {
        LOGGER.info("Searching for offers by category: " + category);
        Set<Offer> set = offerDao.findByCategory(category);
        LOGGER.info("Founded offers: " + set);
        return set;
    }

    @Override
    public Set<Offer> findByParams(long categoryId, long tagId, double minPrice, double maxPrice) {
        Tag tagForFilter = null;
        if(tagId != 0){
            tagForFilter = tagService.findById(tagId);
        }

        LOGGER.info("Searching for offers by parameters: " +
                "category id = " + categoryId +
                " | tag id = " + tagId +
                " | minimum price = " + minPrice +
                " | maximum price = " + maxPrice);

        Set<Offer> allOffers = offerDao.findAll();
        Set<Offer> filteredOffers = new HashSet<>();
        for (Offer offer: allOffers) {
            if(categoryId == 0 || offer.getCategory().getId() == categoryId){
                if(minPrice == 0 || offer.getPrice() > minPrice){
                    if(maxPrice == 0 || offer.getPrice() < maxPrice){
                        if(tagForFilter == null || offer.getTags().contains(tagForFilter)) {
                            filteredOffers.add(offer);
                        }
                    }
                }

            }
        }
        LOGGER.info("Founded offers: " + filteredOffers);
        return filteredOffers;
    }

    @Override
    public Offer addTag(Offer offer, Tag tag) {
        LOGGER.info("Add tag: " + tag + " for Offer: " + offer);
        return offerDao.addTag(offer, tag);
    }

    @Override
    public Offer deleteTag(Offer offer, Tag tag) {
        LOGGER.info("Delete tag: " + tag + " from Offer: " + offer);
        return offerDao.deleteTag(offer, tag);
    }

    @Override
    public Offer updateTags(Offer offer, Set<Tag> tags) {
        LOGGER.info("Add tags: " + tags + " for Offer: " + offer);
        return offerDao.updateTags(offer, tags);
    }

    @Override
    public Offer updateCategory(Offer offer, Category category) {
        LOGGER.info("Set category : " + category + " for Offer: " + offer);
        return offerDao.updateCategory(offer, category);
    }
}
