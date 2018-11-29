package com.netcracker.sharlan;

import com.netcracker.sharlan.bean.Category;
import com.netcracker.sharlan.bean.Offer;
import com.netcracker.sharlan.bean.Tag;
import com.netcracker.sharlan.dao.*;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestOfferDao {

    private OfferDao offerDao;

    private Offer offer;
    private Category category;
    private Tag tag;

    @BeforeEach
    public void setUp() {
        System.out.println("setUp()");
        offerDao = new OfferDaoImpl();

        tag = new Tag("testTag");
        category = new Category("testCategory");
        offer = new Offer("testOffer",
                        "testOfferDescription", category, 222.22);

        System.out.println("----- Test tag: " + tag);
        System.out.println("----- Test category: " + category);
        System.out.println("----- Test offer: " + offer);

        offerDao.beginTransaction();
    }

    @AfterEach
    public void end(){
        System.out.println("end()");
        offerDao.cancel();
    }


    @Test
    public void testSaveOffer() {
        System.out.println("->testSaveOffer()");
        Offer savedOffer = offerDao.save(offer);
        System.out.println("----- Saved offer: " + offer);

        assertNotNull(offer.getId());
    }

    @Test
    public void testFindOffer() {
        System.out.println("->testFindOffer()");
        Offer savedOffer = offerDao.save(offer);
        Offer foundOffer = offerDao.findById(savedOffer.getId());
        System.out.println("----- Found offer: " + foundOffer);

        assertNotNull(foundOffer.getId());
        assertNotNull(foundOffer.getName());
    }

    @Test
    public void testUpdateOffer() {
        System.out.println("->testUpdateOffer()");
        Offer savedOffer = offerDao.save(offer);
        System.out.println("----- Saved offer: " + savedOffer);

        savedOffer.setName("updatedName");
        Offer updatedOffer = offerDao.update(savedOffer);
        System.out.println("----- Updated offer: " + updatedOffer);

        assertNotNull(updatedOffer);
        assertEquals(savedOffer.getId(), updatedOffer.getId());
        System.out.println("saved name: " + savedOffer.getName() + " | updated name: " + updatedOffer.getName());
        assertEquals(savedOffer.getName(), updatedOffer.getName());
    }

    @Test
    public void testDeleteOffer() {
        System.out.println("->testDeleteOffer()");
        Offer savedOffer = offerDao.save(offer);
        System.out.println("----- Saved offer: " + savedOffer);

        long id = savedOffer.getId();
        offerDao.delete(savedOffer);
        Offer deletedOffer = offerDao.findById(id);

        assertNull(deletedOffer);
    }

    @Test
    public void testFindOffersByTag(){
        System.out.println("->testFindOffersByTag()");
        Set<Tag> tags = new HashSet<>();
        tags.add(tag);

        offer.setTags(tags);
        offer = offerDao.save(offer);
        System.out.println("saved offer with tags: " + offer);
        tags = offer.getTags();
        for (Tag t: tags) {
            tag = t;
        }
        System.out.println("Tag: " + tag);
        assertNotNull(offerDao.findByTag(tag));

    }

    @Test
    public void testFindOffersByCategory(){
        offer = offerDao.save(offer);
        System.out.println("saved offer with category: " + offer);
        category = offer.getCategory();
        System.out.println("Category: " + category);
        assertNotNull(offerDao.findByCategory(category));
    }

    @Test
    public void testUpdateOfferCategory(){
        offer = offerDao.save(offer);
        System.out.println("saved offer with category: " + offer);

        Category newCategory = new Category("newTestCategory");

        System.out.println("New Category: " + newCategory);
        offer = offerDao.updateCategory(offer, newCategory);

        assertNotEquals(category.getName(), offer.getCategory().getName());
    }


}
