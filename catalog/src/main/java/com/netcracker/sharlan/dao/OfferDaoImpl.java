package com.netcracker.sharlan.dao;

import com.netcracker.sharlan.entity.Category;
import com.netcracker.sharlan.entity.Offer;
import com.netcracker.sharlan.entity.Tag;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class OfferDaoImpl extends AbstractDao<Offer> implements OfferDao {

    public OfferDaoImpl(){
        super(Offer.class);
    }

    @Override
    public Set<Offer> findAll() {
        return new HashSet<>(getAll());
    }

    @Override
    public Offer findById(long id) {
        return findOneById(id);
    }

    @Override
    public Offer save(Offer offer) {
        return create(offer);
    }

    @Override
    public Offer update(Offer offer) {
        return merge(offer);
    }

    @Override
    public void delete(Offer offer) {
        remove(offer);
    }

    @Override
    public void delete(long id) {
       delete(findById(id));
    }


    @Override
    public Set<Offer> findByTag(Tag tag) {
        HashSet<Offer> set = new HashSet<Offer>(getEntityManager().createQuery("select o from Offer o join o.tags t where t.id = " + tag.getId()).getResultList());
        return set;
    }

    @Override
    public Set<Offer> findByCategory(Category category) {
        HashSet<Offer> set = new HashSet<Offer>(getEntityManager().createQuery("SELECT o FROM Offer o where o.category = " + category.getId()).getResultList());
        return set;
    }

    @Override
    public Offer addTag(Offer offer, Tag tag) {
        offer.getTags().add(tag);
        return merge(offer);
    }

    @Override
    public Offer deleteTag(Offer offer, Tag tag) {
        offer.getTags().remove(tag);
        return merge(offer);
    }

    @Override
    public Offer updateTags(Offer offer, Set<Tag> tags) {
        offer.setTags(tags);
        return merge(offer);
    }

    @Override
    public Offer updateCategory(Offer offer, Category category) {
        offer.setCategory(category);
        return merge(offer);
    }

}
