package com.es.demo.controller;

import com.es.demo.document.Article;
import com.es.demo.search.SearchRequestDTO;
import com.es.demo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    private final ArticleService service;

    @Autowired
    public ArticleController(ArticleService service){
        this.service = service;
    }

    @PostMapping
    public void save(@RequestBody Article testIndex){
        service.save(testIndex);
    }

    @GetMapping("/{id}")
    public Article save(@PathVariable String id){
        return service.findById(id);
    }

    @PostMapping("/search")
    public List<Article> search(@RequestBody SearchRequestDTO dto){
        return service.search(dto);
    }
}
