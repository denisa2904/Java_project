package com.example.zoo.service;

import com.example.zoo.model.Comment;
import com.example.zoo.repository.CommentRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;

    public int addComment(Comment comment) {
        commentRepo.save(comment);
        return 1;
    }

    public List<Comment> getCommentsByAnimalId(UUID animalId) {
        return commentRepo.findAllByAnimalId(animalId);
    }

    public List<Comment> getCommentsByUserId(UUID animalId, UUID userId) {
        return commentRepo.findAllByAnimalIdAndUserId(animalId, userId);
    }

    public int deleteComment(UUID commentId) {
        commentRepo.deleteById(commentId);
        return 1;
    }

    public int updateComment(UUID commentId, String content) {
        if(commentRepo.findById(commentId).isEmpty())
            return 0;
        Comment c = commentRepo.findById(commentId).get();
        c.setContent(content);
        commentRepo.save(c);
        return 1;
    }
}
