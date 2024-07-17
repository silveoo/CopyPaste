package com.silveo.copypaste.services;

import com.silveo.copypaste.entity.Comment;
import com.silveo.copypaste.repositories.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment addComment(Comment comment){
        return commentRepository.save(comment);
    }
}
