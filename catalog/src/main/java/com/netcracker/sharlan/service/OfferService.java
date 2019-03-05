package com.netcracker.sharlan.service;

import com.netcracker.sharlan.entity.Category;
import com.netcracker.sharlan.entity.Offer;
import com.netcracker.sharlan.entity.Tag;

import java.util.List;
import java.util.Set;

public interface OfferService {

    Set<Offer> findAll();

    Offer findById(long id);

    public List<Offer> findById(List<Integer> ids);

    Offer save(Offer offer);

    Offer update(Offer offer);

    void delete(Offer offer);

    void delete(long id);

    Set<Offer> findByTag(Tag tag);

    Set<Offer> findByCategory(Category category);

    Set<Offer> findByParams(long categoryId, long tagId, double minPrice, double maxPrice);

    Offer addTag(Offer offer, Tag tag);

    Offer deleteTag(Offer offer, Tag tag);

    Offer updateTags(Offer offer, Set<Tag> tags);

    Offer updateCategory(Offer offer, Category category);
}
