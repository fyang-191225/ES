package com.fyy.es.util;


import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Es 所有查询语句的工具类
 *
 * @author fyy
 * @date 2020/1/10 0010 下午 21:16
 */
public class ESTransportUtil {
    private static String clusterName = "qfjava"; // 集群名称  可以改变
    private static String esHost = "39.105.189.141"; // 当前ES服务器的地址  可以改变
    private static int esPort = 9300; //端口号
    private static TransportClient client;

    private ESTransportUtil() {
    }

    static {
        //1、创建设置对象 并且设置ES的集群名称
        Settings settings = Settings.builder().put("cluster.name", clusterName).build();
        // 2、创建客户端对象
        client = new PreBuiltTransportClient(settings).
                addTransportAddress(new TransportAddress(
                        new InetSocketAddress(esHost, esPort)));
    }

    public static TransportClient getInstance() {
        return client;
    }

    // 1~4 基本CRUD

    /**
     * 1
     * 新增或者修改
     *
     * @param bean
     * @return 201 新增 200修改
     */
    public static int saveOrUpdate(EsDocmentBean bean) {
        IndexResponse indexResponse = client.prepareIndex(bean.getIndexName(), bean.getType(), bean.getId())
                .setSource(bean.getJson(), XContentType.JSON).get();
        return indexResponse.status().getStatus();
    }

    /**
     * 2
     * 查询
     *
     * @param bean 4个参数 1索引名，库名 2类型，表名， 3id
     * @param clz  实例类
     * @param <T>  指定泛型的实例对象
     * @return 指定泛型的实例对象
     */
    public static <T> T getSingle(EsDocmentBean bean, Class<T> clz) {
        GetResponse getResponse = client.prepareGet(bean.getIndexName(), bean.getType(), bean.getId()).get();
        return JSON.parseObject(getResponse.getSourceAsString(), clz);
    }

    /**
     * 3
     * 查询
     *
     * @return 返回存储的字符串
     */
    public static String getSingle(EsDocmentBean bean) {
        GetResponse response = client.prepareGet(bean.getIndexName(), bean.getType(), bean.getId()).get();
        return response.getSourceAsString();
    }

    /**
     * 4
     * 删除
     *
     * @return 200删除成功  404没有对应数据
     */
    public static int del(EsDocmentBean bean) {
        DeleteResponse response = client.prepareDelete(bean.getIndexName(), bean.getType(), bean.getId()).get();
        return response.status().getStatus();
    }

    /**
     * 5
     * 批量新增或修改
     *
     * @return 返回200 表示成功
     */
    public static int batchSave(List<EsDocmentBean> beanList) {
        //创建批处理对象
        BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();
        //循环往批处理 添加内容
        for (EsDocmentBean bean : beanList) {
            bulkRequestBuilder.add(client.prepareIndex(bean.getIndexName(), bean.getType(), bean.getId()).setSource(bean.getJson(), XContentType.JSON));
        }
        //执行批处理
        BulkResponse reponses = bulkRequestBuilder.get();
        //获取批处理的结果
        return reponses.status().getStatus();
    }

    /**
     * 6
     * 批量删除
     *
     * @return 返回200 表示成功
     */
    public static int batchDel(List<EsDocmentBean> beanList) {
        //创建批处理对象
        BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();
        //循环往批处理 添加内容
        for (EsDocmentBean bean : beanList) {
            bulkRequestBuilder.add(client.prepareDelete(bean.getIndexName(), bean.getType(), bean.getId()));
        }
        //执行批处理
        BulkResponse reponses = bulkRequestBuilder.get();
        //获取批处理的结果
        return reponses.status().getStatus();
    }

    /**
     * 7
     * 查询全部
     *
     * @return 查詢的結果 指定泛型的集合
     */
    public static <T> List<T> queryAll(String indexName, String type, Class<T> clz) {
        SearchResponse searchResponse = client.prepareSearch(indexName).setTypes(type).get();
        if (searchResponse.status().getStatus() == 200) {
            ArrayList<T> list = new ArrayList<>();
            SearchHit[] hits = searchResponse.getHits().getHits();
            for (SearchHit s : hits) {
                list.add(JSON.parseObject(s.getSourceAsString(), clz));
            }
            return list;
        } else {
            return null;
        }
    }

    /**
     * 8
     * 查询全部
     * 返回字符串
     */
    public static String queryAll(String indexName, String type) {
        SearchResponse response = client.prepareSearch(indexName).setTypes(type).get();
        if (response.status().getStatus() == 200) {
            List<String> list = new ArrayList<>();
            SearchHit[] arr = response.getHits().getHits();
            for (SearchHit s : arr) {
                list.add(s.getSourceAsString());
            }
            return JSON.toJSONString(list);
        } else {
            return null;
        }
    }

    /**
     * 9
     * 查询全部
     * <p>
     * 带排序
     */
    public static String queryAll(String indexName, String type, SortBuilder builder) {
        SearchResponse response = client.prepareSearch(indexName).setTypes(type).addSort(builder).get();
        if (response.status().getStatus() == 200) {
            List<String> list = new ArrayList<>();
            SearchHit[] arr = response.getHits().getHits();
            for (SearchHit s : arr) {
                list.add(s.getSourceAsString());
            }
            return JSON.toJSONString(list);
        } else {
            return null;
        }
    }

    /**
     * 范围查询
     */
    public static String queryAll(EsDocumentRangeBean bean) {
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(bean.getField());
        rangeQueryBuilder.gt(bean.minNum).lt(bean.maxNum);
        SearchResponse response = client.prepareSearch(bean.getIndexName()).setTypes(bean.getType()).setQuery(rangeQueryBuilder).get();
        if (response.status().getStatus() == 200) {
            List<String> list = new ArrayList<>();
            SearchHit[] arr = response.getHits().getHits();
            for (SearchHit s : arr) {
                list.add(s.getSourceAsString());
            }
            return JSON.toJSONString(list);
        } else {
            return null;
        }
    }

    /**
     * 模糊查询
     */
    public static <T> List<T> queryAll(EsDocumentLikeBean bean, Class<T> clz) {
        WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery(bean.getField(), "*" + bean.getValue() + "*");
        SearchResponse response = client.prepareSearch(bean.getIndexName()).setTypes(bean.getType()).setQuery(wildcardQueryBuilder).get();
        if (response.status().getStatus() == 200) {
            List<T> list = new ArrayList<>();
            SearchHit[] arr = response.getHits().getHits();
            for (SearchHit s : arr) {
                list.add(JSON.parseObject(s.getSourceAsString(), clz));
            }
            return list;
        } else {
            return null;
        }
    }

    //静态内部类
    //实现es文档的操作 新增 修改
    @Data
    public static class EsDocmentBean {
        private String id;
        private String indexName;
        private String type;
        private String json;

        public EsDocmentBean(String id, String indexName, String type, String json) {
            this.id = id;
            this.indexName = indexName;
            this.type = type;
            this.json = json;
        }

        public EsDocmentBean() {
        }
    }

    //实现范围查询
    @Data
    public static class EsDocumentRangeBean {
        private String indexName;
        private String type;
        private String field;
        private int minNum;
        private int maxNum;
    }

    //实现模糊查询的封装
    @Data
    public static class EsDocumentLikeBean {
        private String indexName;
        private String type;
        private String field;
        private String value;
    }
}
