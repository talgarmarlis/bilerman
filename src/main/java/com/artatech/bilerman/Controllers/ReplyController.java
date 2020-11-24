package com.artatech.bilerman.Controllers;

import com.artatech.bilerman.Entities.Reply;
import com.artatech.bilerman.Services.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/replies")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @GetMapping
    public Page<Reply> getReplies(@RequestParam(value = "commentId", required = true) Long commentId,
                                          @RequestParam(value = "orderBy", defaultValue = "createdAt", required = false) String orderBy,
                                          @RequestParam(value = "direction", defaultValue = "ASC", required = false) String direction,
                                          @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                          @RequestParam(value = "size", defaultValue = "10", required = false) Integer size){
        return replyService.findByPage(commentId, orderBy, direction, page, size);
    }

    @GetMapping("/{id}")
    public Reply getReplyById(@PathVariable("id") Long id){
        return replyService.findById(id);
    }

    @PostMapping
    public Reply saveReply(@RequestBody Reply reply) {
        return replyService.save(reply);
    }

    @DeleteMapping("/{id}")
    public void deleteReply(@PathVariable("id") Long id){
        replyService.delete(id);
    }
}
