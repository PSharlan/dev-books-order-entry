package com.devbooks.sharlan.dao;

import com.devbooks.sharlan.entities.Comment;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class CommentDaoImpl extends AbstractDao<Comment> implements CommentDao {

    public CommentDaoImpl() {
        super(Comment.class);
    }

    @Override
    public Comment findById(long id) {
        return findOneById(id);
    }

    @Override
    public Set<Comment> findByOfferId(long id) {
        HashSet<Comment> set = new HashSet<>(getEntityManager().createQuery("SELECT c FROM Comment c where c.offer = " + id).getResultList());
        return set;
    }

    @Override
    public Comment save(Comment comment) {
        return create(comment);
    }

    @Override
    public Comment update(Comment comment) {
        return merge(comment);
    }

    @Override
    public void delete(Comment comment) {
        remove(comment);
    }

    @Override
    public void delete(long id) {
        delete(findById(id));
    }
}
