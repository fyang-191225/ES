package com.fyy.es.transport;

import com.fyy.es.util.ESTransportUtil;
import org.apache.lucene.util.QueryBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

/**
 * ES  条件查询
 *
 * @author fyy
 * @date 2020/1/11 0011 上午 9:40
 */
public class ES04 {
    public static void main(String[] args) {
        TransportClient client = ESTransportUtil.getInstance();
        // 范围查询
        RangeQueryBuilder queryBuilder = QueryBuilders.rangeQuery("id");
        queryBuilder.gt(200).lt(300);
        // 精确查询
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("money", 200);
        // 模糊查询
//        WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("name", "*22*");/
        //布尔条件 用于拼接多个查询条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(queryBuilder).must(termQueryBuilder);
        // 最终的查询条件汇总对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        // 执行查询
        SearchResponse searchResponse = client.prepareSearch("j1908").setTypes("work")
                .addSort("id", SortOrder.DESC).setQuery(boolQueryBuilder).get();

        // 获取查询结果

        SearchHits hits = searchResponse.getHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit sh : hits1) {
            System.out.println(sh.getSourceAsString());
        }

       // System.out.println(ESTransportUtil.queryAll("j1908","work"));
    }
}

