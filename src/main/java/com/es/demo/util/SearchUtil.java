package com.es.demo.util;

import com.es.demo.search.SearchRequestDTO;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class SearchUtil {

    private SearchUtil(){};

    public static SearchRequest buildSearchRequest(String index, SearchRequestDTO dto){

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(getQueryBuilder(dto));

        if(dto.getSortBy() != null){
            searchSourceBuilder.sort(
                    dto.getSortBy(),
                    dto.getOrder() == null ? SortOrder.ASC : dto.getOrder());
        }

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(searchSourceBuilder);

        return searchRequest;
    }

    public static QueryBuilder getQueryBuilder(SearchRequestDTO dto){
        if(dto == null) return null;

        List<String> fields = dto.getFields();
        if (CollectionUtils.isEmpty(fields)) {
            return null;
        }

        if(fields.size() > 1){
            MultiMatchQueryBuilder queryBuilder = new MultiMatchQueryBuilder(dto.getSearchTerm())
                    .type(MultiMatchQueryBuilder.Type.CROSS_FIELDS)
                    .operator(Operator.AND);
            fields.forEach(queryBuilder::field);
            return queryBuilder;
        }

        return dto.getFields().stream()
                .findFirst()
                .map(field -> QueryBuilders.matchQuery(field,dto.getSearchTerm()))
                .orElse(null);
    }
}
