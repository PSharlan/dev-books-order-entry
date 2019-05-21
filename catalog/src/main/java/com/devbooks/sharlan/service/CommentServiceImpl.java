package com.devbooks.sharlan.service;

import com.devbooks.sharlan.dao.CommentDao;
import com.devbooks.sharlan.entities.Comment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    CommentDao dao;

    @Override
    @Transactional
    public Comment findById(long id) {
        return dao.findById(id);
    }

    @Override
    @Transactional
    public Set<Comment> findByOfferId(long id) {
        return dao.findByOfferId(id);
    }

    @Override
    @Transactional
    public Comment save(Comment comment) {
        return dao.save(comment);
    }

    @Override
    @Transactional
    public Comment update(Comment comment) {
        return dao.update(comment);
    }

    @Override
    @Transactional
    public void delete(Comment comment) {
        dao.delete(comment);
    }

    @Override
    @Transactional
    public void delete(long id) {
        dao.delete(id);
    }
}
