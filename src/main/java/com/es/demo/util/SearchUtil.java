package com.es.demo.util;

import com.es.demo.search.SearchRequestDTO;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class SearchUtil {

    private SearchUtil(){};

    public static SearchRequest buildSearchRequest(String index, SearchRequestDTO dto){

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(getQueryBuilder(dto));


        /*BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("created").gte("2020-08-10"));
        searchSourceBuilder.query(boolQueryBuilder);
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(dto.getFields().get(0),dto.getSearchTerm());

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(termQueryBuilder);

        searchSourceBuilder.highlighter(new HighlightBuilder().field("title").requireFieldMatch(false).postTags("</123>").preTags("<123>"));*/

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
