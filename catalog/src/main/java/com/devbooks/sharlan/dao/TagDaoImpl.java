package com.devbooks.sharlan.dao;

import com.devbooks.sharlan.entities.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
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

    @Override
    public void delete(long id) {
        remove(findById(id));
    }

    @Override
    public List<Tag> findAll() {
        return getAll();
    }

}
