package com.netcracker.sharlan.service;

import com.netcracker.sharlan.dao.TagDao;
import com.netcracker.sharlan.entities.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Set;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    private TagDao dao;

    @Autowired
    public TagServiceImpl(TagDao dao){
        this.dao = dao;
    }

    @Override
    public Tag findById(long id) {
        Tag tag = dao.findById(id);
        tag.getOffers().size();
        return tag;
    }

    @Override
    public Tag save(Tag tag) {
        return dao.save(tag);
    }

    @Override
    public void saveAll(Set<Tag> tags) {
        dao.saveAll(tags);
    }

    @Override
    public Tag update(Tag tag) {
        return dao.update(tag);
    }

    @Override
    public void delete(Tag tag) {
        dao.delete(tag);
    }

    @Override
    public void delete(long id) {
        dao.delete(id);
    }

    @Override
    public List<Tag> findAll() {
        return dao.findAll();
    }
}
