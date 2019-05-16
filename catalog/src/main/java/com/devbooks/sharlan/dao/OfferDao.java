package com.devbooks.sharlan.dao;

import com.devbooks.sharlan.entities.Category;
import com.devbooks.sharlan.entities.Offer;
import com.devbooks.sharlan.entities.Tag;

import java.util.Set;

public interface OfferDao {

    Set<Offer> findAll();

    Offer findById(long id);

    Offer save(Offer offer);

    Offer update(Offer offer);

    void delete(Offer offer);

    void delete(long id);

    Set<Offer> findByTag(Tag tag);

    Set<Offer> findByCategory(Category category);

    Offer addTag(Offer offer, Tag tag);

    Offer deleteTag(Offer offer, Tag tag);

    Offer updateTags(Offer offer, Set<Tag> tags);

    Offer updateCategory(Offer offer, Category category);
}
