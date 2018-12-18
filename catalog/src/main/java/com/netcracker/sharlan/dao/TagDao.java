package com.netcracker.sharlan.dao;

import com.netcracker.sharlan.bean.Category;
import com.netcracker.sharlan.bean.Tag;

import java.util.Set;

public interface TagDao {

    Tag findById(long id);

    Tag save(Tag tag);

    void saveAll(Set<Tag> tags);

    Tag update(Tag tag);

    void delete(Tag tag);
}
