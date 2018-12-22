package com.netcracker.sharlan;

import com.netcracker.sharlan.bean.Category;
import com.netcracker.sharlan.bean.Offer;
import com.netcracker.sharlan.bean.Tag;
import com.netcracker.sharlan.dao.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OfferDaoTest {

    private OfferDao offerDao;
    private TagDao tagDao;
    private CategoryDao categoryDao;

    private Offer offer;
    private Category category;
    private Tag tag1;
    private Tag tag2;
    private Set<Tag> tags;

    @BeforeEach
    public void setUp() {
        offerDao = new OfferDaoImpl();
        tagDao = new TagDaoImpl();
        categoryDao = new CategoryDaoImpl();

        tag1 = new Tag("TEST_TAG1");
        tag2 = new Tag("TEST_TAG2");
        category = new Category("TEST_CATEGORY");
        offer = new Offer("TEST_OFFER",
                        "testOfferDescription", category, 222.22);
        tags = new HashSet<Tag>();
        tags.add(tag1);
        tags.add(tag2);
        offer.setTags(tags);
    }

    @AfterEach
    public void breakDown(){

        offerDao.delete(offer);
        for (Tag tag: tags) {
            tagDao.delete(tag);
        }
        categoryDao.delete(category);
    }


    @Test
    public void findOffer() {
        offerDao.save(offer);
        Offer foundOffer = offerDao.findById(offer.getId());

        assertNotNull(foundOffer);
        assertNotNull(foundOffer.getName());
    }

    @Test
    public void updateOffer() {
        offerDao.save(offer);
        offer.setName("updatedName");
        Offer updatedOffer = offerDao.update(offer);

        assertNotNull(updatedOffer);
        assertEquals(offer.getId(), updatedOffer.getId());
        assertEquals(offer.getName(), updatedOffer.getName());
    }

    @Test
    public void deleteOffer() {
        offerDao.save(offer);
        long id = offer.getId();
        offerDao.delete(offer);
        Offer deletedOffer = offerDao.findById(id);
        assertNull(deletedOffer);
    }

    @Test
    public void findOffersByTag(){
        offer.setTags(tags);
        offer = offerDao.save(offer);
        tags = offer.getTags();
        for (Tag t: tags) {
            assertNotNull(offerDao.findByTag(t));
        }
    }

    @Test
    public void findOffersByCategory(){
        offer = offerDao.save(offer);
        category = offer.getCategory();
        assertNotNull(offerDao.findByCategory(category));
    }

    @Test
    public void updateOfferCategory(){
        offer = offerDao.save(offer);
        Category newCategory = new Category("NEW_TEST_CATEGORY");
        offer = offerDao.updateCategory(offer, newCategory);

        assertEquals(offer.getCategory().getName(), "NEW_TEST_CATEGORY");

        newCategory = offer.getCategory();
        offerDao.updateCategory(offer, category);
        categoryDao.delete(newCategory);
    }


}
