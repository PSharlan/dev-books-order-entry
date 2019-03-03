package com.netcracker.sharlan.service;

import com.netcracker.sharlan.dao.TagDao;
import com.netcracker.sharlan.entities.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Set;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    private static final Logger LOGGER = LogManager.getLogger(TagServiceImpl.class.getName());

    private TagDao dao;

    @Autowired
    public TagServiceImpl(TagDao dao){
        this.dao = dao;
    }

    @Override
    public Tag findById(long id) {
        LOGGER.info("Searching for Tag by id: " + id);
        Tag tag = dao.findById(id);
        if(tag != null) {
            LOGGER.info("Founded Tag: " + tag);
            tag.getOffers().size();
        }else{
            LOGGER.info("Tag not found");
        }
        return tag;
    }

    @Override
    public Tag save(Tag tag) {
        LOGGER.info("Saving Tag : " + tag);
        tag = dao.save(tag);
        LOGGER.info("Saved Tag : " + tag);
        return tag;
    }

    @Override
    public void saveAll(Set<Tag> tags) {
        LOGGER.info("Saving Tags : " + tags);
        dao.saveAll(tags);
    }

    @Override
    public Tag update(Tag tag) {
        LOGGER.info("Updating Tag : " + tag);
        if(tag.getId() == null || dao.findById(tag.getId()) == null){
            LOGGER.info("Tag with id: " + tag.getId() + " not found. Tag not updated");
            return null;
        }
        Tag updatedTag = dao.update(tag);
        LOGGER.info("Updated tag : " + updatedTag);
        return updatedTag;
    }

    @Override
    public void delete(Tag tag) {
        delete(tag.getId());
    }

    @Override
    public void delete(long id) {
        LOGGER.info("Deleting Tag with id: " + id);
        dao.delete(id);
        LOGGER.info("Deleted Tag with id: " + id);
    }

    @Override
    public List<Tag> findAll() {
        LOGGER.info("Searching for all Tags");
        List<Tag> list = dao.findAll();
        LOGGER.info("Founded Tags: " + list);
        return list;
    }
}
