package com.netcracker.sharlan.service;

import com.netcracker.sharlan.dao.TagDao;
import com.netcracker.sharlan.entities.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    @Autowired
    TagDao dao;

    @Override
    public Tag findById(long id) {
        return dao.findById(id);
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
}
