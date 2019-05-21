package com.devbooks.sharlan.service;

import com.devbooks.sharlan.entities.Comment;

import java.util.Set;

public interface CommentService {

    Comment findById(long id);

    Set<Comment> findByOfferId(long id);

    Comment save(Comment comment);

    Comment update(Comment comment);

    void delete(Comment comment);

    void delete(long id);
}
