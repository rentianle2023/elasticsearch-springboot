package com.es.demo.repository;

import com.es.demo.document.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleRepository extends ElasticsearchRepository<Article,String> {
}
