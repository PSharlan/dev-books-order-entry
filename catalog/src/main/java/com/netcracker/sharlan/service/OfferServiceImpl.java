package com.netcracker.sharlan.service;

import com.netcracker.sharlan.dao.OfferDao;
import com.netcracker.sharlan.entities.Category;
import com.netcracker.sharlan.entities.Offer;
import com.netcracker.sharlan.entities.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Set;

@Service
@Transactional
public class OfferServiceImpl implements OfferService {

    private OfferDao dao;

    @Autowired
    public OfferServiceImpl(OfferDao dao){
        this.dao = dao;
    }

    @Override
    public Offer findById(long id) {
        return dao.findById(id);
    }

    @Override
    public Offer save(Offer offer) {
        return dao.save(offer);
    }

    @Override
    public Offer update(Offer offer) {
        return dao.update(offer);
    }

    @Override
    public void delete(Offer offer) {
        dao.delete(offer);
    }

    @Override
    public void delete(long id) {
        dao.delete(id);
    }

    @Override
    public Set<Offer> findByTag(Tag tag) {
        return dao.findByTag(tag);
    }

    @Override
    public Set<Offer> findByCategory(Category category) {
        return dao.findByCategory(category);
    }

    @Override
    public Set<Offer> findByPrice(double price) {
        return dao.findByPrice(price);
    }

    @Override
    public Offer addTag(Offer offer, Tag tag) {
        return dao.addTag(offer, tag);
    }

    @Override
    public Offer deleteTag(Offer offer, Tag tag) {
        return dao.deleteTag(offer, tag);
    }

    @Override
    public Offer updateTags(Offer offer, Set<Tag> tags) {
        return dao.updateTags(offer, tags);
    }

    @Override
    public Offer updateCategory(Offer offer, Category category) {
        return dao.updateCategory(offer, category);
    }
}
