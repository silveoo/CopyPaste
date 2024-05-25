package com.silveo.copypaste.controllers;

import com.silveo.copypaste.entity.Comment;
import com.silveo.copypaste.entity.Paste;
import com.silveo.copypaste.services.CommentService;
import com.silveo.copypaste.services.PasteService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/comments")
@AllArgsConstructor
public class CommentController {
    private final PasteService pasteService;

    //POST method adds comment
    @PostMapping("/add/{pasteId}")
    @PreAuthorize("hasAnyAuthority('ROLE_AUTHOR')")
    public Comment addComment(@RequestBody Comment comment, @PathVariable Long pasteId){
        pasteService.addCommentToPaste(pasteId, comment);
        return comment;
    }
}
