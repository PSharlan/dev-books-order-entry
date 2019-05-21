package com.devbooks.sharlan.dao;

import com.devbooks.sharlan.entities.Comment;

import java.util.Set;

public interface CommentDao {

    Comment findById(long id);

    Set<Comment> findByOfferId(long id);

    Comment save(Comment comment);

    Comment update(Comment comment);

    void delete(Comment comment);

    void delete(long id);

}
