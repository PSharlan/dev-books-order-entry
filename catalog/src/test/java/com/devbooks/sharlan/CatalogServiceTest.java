package com.devbooks.sharlan;

import com.devbooks.sharlan.config.TestConfig;
import com.devbooks.sharlan.service.CategoryService;
import com.devbooks.sharlan.service.OfferService;
import com.devbooks.sharlan.service.TagService;
import com.devbooks.sharlan.entities.Category;
import com.devbooks.sharlan.entities.Offer;
import com.devbooks.sharlan.entities.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfig.class }, loader = AnnotationConfigContextLoader.class)
@SqlConfig(dataSource = "pgTestDataSource")
@Sql(value = {"/create.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/drop.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CatalogServiceTest {

    @Autowired
    private OfferService offerService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CategoryService categoryService;

    private Offer offer;
    private Category category;
    private Tag tag1;
    private Tag tag2;
    private Set<Tag> tags;


    @BeforeEach
    public void setUp() {
        category = new Category("TEST_CATEGORY");
        categoryService.save(category);
        tag1 = new Tag("TEST_TAG1");
        tag2 = new Tag("TEST_TAG2");

        tags = new HashSet<Tag>();
        tags.add(tag1);
        tags.add(tag2);
        tagService.saveAll(tags);

        offer = new Offer("TEST_OFFER", "testOfferDescription", 222.22, category, tags);
    }

    @Test
    public void findAllOffers(){
        offerService.save(offer);
        Set<Offer> list = offerService.findAll();
        assertNotNull(list);
    }


    @Test
    public void findOffer() {
        offerService.save(offer);
        Offer foundOffer = offerService.findById(offer.getId());

        assertNotNull(foundOffer);
        assertNotNull(foundOffer.getName());
    }

    @Test
    public void updateOffer() {
        offerService.save(offer);
        offer.setName("updatedName");
        Offer updatedOffer = offerService.update(offer);

        assertNotNull(updatedOffer);
        assertEquals(offer.getId(), updatedOffer.getId());
        assertEquals(offer.getName(), updatedOffer.getName());
    }

    @Test
    public void deleteOffer() {
        offerService.save(offer);
        long id = offer.getId();
        offerService.delete(offer);
        offer = null;
        Offer deletedOffer = offerService.findById(id);
        assertNull(deletedOffer);
    }

    @Test
    public void findOffersByTag(){
        offer = offerService.save(offer);
        tags = offer.getTags();
        for (Tag t: tags) {
            assertNotNull(offerService.findByTag(t));
        }
    }

    @Test
    public void findOffersByCategory(){
        offer = offerService.save(offer);
        category = offer.getCategory();
        assertNotNull(offerService.findByCategory(category));
    }

    @Test
    public void updateOfferCategory(){
        offer = offerService.save(offer);

        Category newCategory = new Category("NEW_TEST_CATEGORY");
        categoryService.save(newCategory);

        offer = offerService.updateCategory(offer, newCategory);

        assertEquals(offer.getCategory().getName(), "NEW_TEST_CATEGORY");

        newCategory = offer.getCategory();
        offerService.updateCategory(offer, category);
        categoryService.delete(newCategory);
    }
}
