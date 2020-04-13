package com.artatech.bilerman.Controllers;

import com.artatech.bilerman.Entities.Draft;
import com.artatech.bilerman.Services.DraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/drafts")
public class DraftController {

    @Autowired
    private DraftService draftService;

    @GetMapping
    public Iterable<Draft> getDrafts(){
        return draftService.findAll();
    }

    @GetMapping("/{id}")
    public Draft getDraftById(@PathVariable("id") Long id){
        return draftService.findById(id);
    }

    @GetMapping("/user/{id}")
    public Collection<Draft> getDraftsByUserId(@PathVariable("id") Long id, @RequestParam(required = false) Boolean published){
        return published == null ? draftService.fingAllByUser(id) : draftService.fingAllByUser(id, published);
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
