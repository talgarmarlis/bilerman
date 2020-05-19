package com.artatech.bilerman.Controllers;

import com.artatech.bilerman.Entities.Draft;
import com.artatech.bilerman.Services.DraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/drafts")
public class DraftController {

    @Autowired
    private DraftService draftService;

    @GetMapping
    public Page<Draft> getDrafts(@RequestParam(value = "userId", required = false) Long userId,
                                 @RequestParam(required = false) Boolean published,
                                 @RequestParam(value = "orderBy", defaultValue = "createdAt", required = false) String orderBy,
                                 @RequestParam(value = "direction", defaultValue = "DESC", required = false) String direction,
                                 @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                 @RequestParam(value = "size", defaultValue = "10", required = false) Integer size){
        return draftService.findByPage(userId, published, orderBy, direction, page, size);
    }

    @GetMapping("/{id}")
    public Draft getDraftById(@PathVariable("id") Long id){
        return draftService.findById(id);
    }

    @GetMapping("/user/{id}")
    public Page<Draft> getDraftsByUserId(@PathVariable("id") Long id,
                                         @RequestParam(required = false) Boolean published,
                                         @RequestParam(value = "orderBy", defaultValue = "createdAt", required = false) String orderBy,
                                         @RequestParam(value = "direction", defaultValue = "DESC", required = false) String direction,
                                         @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                         @RequestParam(value = "size", defaultValue = "10", required = false) Integer size){
        return draftService.findByPage(id, published, orderBy, direction, page, size);
    }

    @PostMapping
    public Draft saveDraft(@RequestBody Draft draft){
        return draftService.save(draft);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        draftService.delete(id);
    }
}
