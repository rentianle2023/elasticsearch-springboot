package com.es.demo.service;

import com.alibaba.fastjson.JSON;
import com.es.demo.document.Article;
import com.es.demo.repository.ArticleRepository;
import com.es.demo.search.SearchRequestDTO;
import com.es.demo.util.SearchUtil;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.tomcat.util.json.JSONParser;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static com.es.demo.helper.Indices.ARTICLE_INDEX;

@Service
@Slf4j
public class ArticleService {

    private final ArticleRepository repository;

    private final RestHighLevelClient client;

    @Autowired
    public ArticleService(ArticleRepository repository, @Qualifier("elasticsearchClient") RestHighLevelClient client){
        this.client = client;
        this.repository = repository;
    }

    public void save(final Article testIndex){
        repository.save(testIndex);
    }

    public Article findById(String id){
        return repository.findById(id).orElse(null);
    }

    public List<Article> search(SearchRequestDTO dto){
        SearchRequest request = SearchUtil.buildSearchRequest(ARTICLE_INDEX, dto);

        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);

            SearchHit[] searchHits = response.getHits().getHits();
            List<Article> articles = new LinkedList<>();
            for (SearchHit hit : searchHits) {
                Article article = JSON.parseObject(hit.getSourceAsString(),Article.class);
                articles.add(article);
            }
            return articles;
        } catch (IOException e) {
            log.error(e.getMessage(),e);
            return Collections.emptyList();
        }
    }






}
