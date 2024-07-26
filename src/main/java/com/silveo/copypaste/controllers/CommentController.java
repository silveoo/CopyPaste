package com.silveo.copypaste.controllers;

import com.silveo.copypaste.entity.Comment;
import com.silveo.copypaste.services.PasteService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/comments")
@AllArgsConstructor
public class CommentController {
    private final PasteService pasteService;
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    //POST method adds comment
    @PostMapping("/add/{pasteId}")
    @PreAuthorize("hasAnyAuthority('ROLE_AUTHOR')")
    public Comment addComment(@RequestBody Comment comment, @PathVariable Long pasteId){
        logger.info("New comment was added to paste {}", pasteId);
        pasteService.addCommentToPaste(pasteId, comment);
        return comment;
    }
}
