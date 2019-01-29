package com.netcracker.sharlan.service;

import com.netcracker.sharlan.dao.OfferDao;
import com.netcracker.sharlan.entities.Category;
import com.netcracker.sharlan.entities.Offer;
import com.netcracker.sharlan.entities.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class OfferServiceImpl implements OfferService {

    private OfferDao offerDao;
    private TagService tagService;

    @Autowired
    public OfferServiceImpl(OfferDao offerDao, TagService tagService){
        this.offerDao = offerDao;
        this.tagService = tagService;
    }

    @Override
    public Set<Offer> findAll() {
        return offerDao.findAll();
    }

    @Override
    public Offer findById(long id) {
        Offer offer = offerDao.findById(id);
        if(offer != null) {
            offer.getTags().size();
        }
        return offer;
    }

    @Override
    public Offer save(Offer offer) {
        return offerDao.save(offer);
    }

    @Override
    public Offer update(Offer offer) {
        return offerDao.update(offer);
    }

    @Override
    public void delete(Offer offer) {
        offerDao.delete(offer);
    }

    @Override
    public void delete(long id) {
        offerDao.delete(id);
    }

    @Override
    public Set<Offer> findByTag(Tag tag) {
        return offerDao.findByTag(tag);
    }

    @Override
    public Set<Offer> findByCategory(Category category) {
        return offerDao.findByCategory(category);
    }

    @Override
    public Set<Offer> findByPrice(double price) {
        return offerDao.findByPrice(price);
    }

    @Override
    public Set<Offer> findByParams(long categoryId, long tagId, double minPrice, double maxPrice) {
        Tag tagForFilter = null;
        if(tagId != 0){
            tagForFilter = tagService.findById(tagId);
        }

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
        return filteredOffers;
    }

    @Override
    public Offer addTag(Offer offer, Tag tag) {
        return offerDao.addTag(offer, tag);
    }

    @Override
    public Offer deleteTag(Offer offer, Tag tag) {
        return offerDao.deleteTag(offer, tag);
    }

    @Override
    public Offer updateTags(Offer offer, Set<Tag> tags) {
        return offerDao.updateTags(offer, tags);
    }

    @Override
    public Offer updateCategory(Offer offer, Category category) {
        return offerDao.updateCategory(offer, category);
    }
}
