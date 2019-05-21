package com.devbooks.sharlan.service;

import com.devbooks.sharlan.dao.OfferDao;
import com.devbooks.sharlan.entities.Category;
import com.devbooks.sharlan.entities.Offer;
import com.devbooks.sharlan.entities.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class OfferServiceImpl implements OfferService {

    private OfferDao offerDao;
    private TagService tagService;

    @Override
    public Set<Offer> findAll() {
        log.info("Searching for all offers");
        Set<Offer> set = offerDao.findAll();
        return set;
    }

    @Override
    public Offer findById(long id) {
        log.info("Searching for offer by id: " + id);
        final Offer offer = offerDao.findById(id);
        if(offer != null) {
            log.info("Founded offer: " + offer);
            offer.getTags().size();
        }else{
            log.info("Offer not found");
        }
        return offer;
    }

    @Override
    public List<Offer> findById(List<Integer> ids) {
        log.info("Searching for offers by ids: " + ids);

        return ids.stream().map(id -> {
            Offer offer = offerDao.findById(id);
            if(offer != null) {
                log.info("Founded offer: " + offer);
                offer.getTags().size();
            }else{
                log.info("Offer not found");
            }
            return offer;
        }).collect(Collectors.toList());

    }

    @Override
    public Offer save(Offer offer) {
        log.info("Saving offer : " + offer);
        offer = offerDao.save(offer);
        log.info("Saved offer : " + offer);
        return offer;
    }

    @Override
    public Offer update(Offer offer) {
        log.info("Updating offer : " + offer);
        if(offer.getId() == null || offerDao.findById(offer.getId()) == null){
            log.info("Offer with id: " + offer.getId() + " not found. Offer not updated");
            return null;
        }
        Offer updatedOffer = offerDao.update(offer);
        log.info("Updated offer : " + updatedOffer);
        return updatedOffer;
    }

    @Override
    public void delete(Offer offer) {
        delete(offer.getId());
    }

    @Override
    public void delete(long id) {
        log.info("Deleting offer with id: " + id);
        offerDao.delete(id);
        log.info("Deleted offer with id: " + id);
    }

    @Override
    public Set<Offer> findByTag(Tag tag) {
        log.info("Searching for offers by tag: " + tag);
        Set<Offer> set = offerDao.findByTag(tag);
        log.info("Founded offers: " + set);
        return set;
    }

    @Override
    public Set<Offer> findByCategory(Category category) {
        log.info("Searching for offers by category: " + category);
        Set<Offer> set = offerDao.findByCategory(category);
        log.info("Founded offers: " + set);
        return set;
    }

    @Override
    public Set<Offer> findByParams(long categoryId, List<Long> tagIds, double minPrice, double maxPrice) {
        List<Tag> tagsForFilter = new ArrayList<>();
        if(!tagIds.contains(new Long(0))){
            for (long id : tagIds) {
                tagsForFilter.add(tagService.findById(id));
            }
        }

        log.info("Searching for offers by parameters: " +
                "category id = " + categoryId +
                " | tag ids = " + tagIds +
                " | minimum price = " + minPrice +
                " | maximum price = " + maxPrice);
        log.info("Found tags: " + tagsForFilter);

        Set<Offer> allOffers = offerDao.findAll();
        Set<Offer> filteredOffers = new HashSet<>();
        for (Offer offer: allOffers) {
            if(categoryId == 0 || offer.getCategory().getId() == categoryId){
                if(minPrice == 0 || offer.getPrice() >= minPrice){
                    if(maxPrice == 0 || offer.getPrice() <= maxPrice){
                        if(tagsForFilter.size() == 0) {
                            filteredOffers.add(offer);
                        } else {
                            if(offer.getTags().containsAll(tagsForFilter)){
                                filteredOffers.add(offer);
                            }
                        }
                    }

                }

            }
        }
        log.info("Founded offers: " + filteredOffers);
        return filteredOffers;
    }

    @Override
    public Offer addTag(Offer offer, Tag tag) {
        log.info("Add tag: " + tag + " for Offer: " + offer);
        return offerDao.addTag(offer, tag);
    }

    @Override
    public Offer deleteTag(Offer offer, Tag tag) {
        log.info("Delete tag: " + tag + " from Offer: " + offer);
        return offerDao.deleteTag(offer, tag);
    }

    @Override
    public Offer updateTags(Offer offer, Set<Tag> tags) {
        log.info("Add tags: " + tags + " for Offer: " + offer);
        return offerDao.updateTags(offer, tags);
    }

    @Override
    public Offer updateCategory(Offer offer, Category category) {
        log.info("Set category : " + category + " for Offer: " + offer);
        return offerDao.updateCategory(offer, category);
    }
}
