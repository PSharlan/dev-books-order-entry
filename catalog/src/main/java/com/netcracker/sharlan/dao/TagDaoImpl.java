package com.netcracker.sharlan.dao;

import com.netcracker.sharlan.bean.Tag;

import java.util.Set;

public class TagDaoImpl extends AbstractDao<Tag> implements TagDao {

    public TagDaoImpl(){
        super(Tag.class);
    }
    @Override
    public Tag findById(long id) {
        return findOneById(id);
    }

    @Override
    public Tag save(Tag tag) {
        return create(tag);
    }

    @Override
    public void saveAll(Set<Tag> tags) {
        for (Tag tag: tags) {
            create(tag);
        }
    }

    @Override
    public Tag update(Tag tag) {
        return merge(tag);
    }

    @Override
    public void delete(Tag tag) {
        remove(tag);
    }

}
