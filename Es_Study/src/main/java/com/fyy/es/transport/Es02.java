package com.fyy.es.transport;

import com.alibaba.fastjson.JSON;
import com.fyy.es.transport.entity.Work;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetSocketAddress;

/**
 * 普通的增删改查
 *
 * @author fyy
 * @date 2020/1/10 0010 下午 20:13
 */
public class Es02 {
    public static void main(String[] args) {

        //1、创建设置对象
        Settings settings = Settings.builder().put("cluster.name", "qfjava").build();
        Settings settings1 = Settings.builder().put("cluster.name", "qfjava").build();
        //2、创建连接对象
        TransportClient client = new PreBuiltTransportClient(settings).addTransportAddress(
                new TransportAddress(
                        new InetSocketAddress("39.105.189.141", 9300)));

        // 3 CRUD
        add(client);

        // 4关闭
        client.close();
    }

    // 新增
    public static void add(TransportClient client) {
        Work work = new Work(109, "包子", "玩", 23456);
        IndexResponse indexResponse = client.prepareIndex("j1908", "work", "2")
                .setSource(XContentType.JSON, JSON.toJSONString(work)).get();
        System.out.println(indexResponse.getId());
        System.out.println(indexResponse.status().getStatus() + "-----" + indexResponse.status().name());
    }

    // 修改
    public static void update(TransportClient client) {
        Work work = new Work(109, "包子", "玩", 23456);
        UpdateResponse updateResponse = client.prepareUpdate("j1908", "work", work.getId() + "")
                .setDoc(XContentType.JSON, JSON.toJSONString(work)).get();
        System.out.println(updateResponse.status().toString());
    }

    // 查询
    public static void select(TransportClient client) {
        GetResponse response = client.prepareGet("j1908", "work", "1").get();
        String json = response.getSourceAsString();
        Work work1 = JSON.parseObject(json, Work.class);
        System.out.println(work1);
    }
    // 删除
    public static void del(TransportClient client) {
        DeleteResponse response = client.prepareDelete("j1908", "work", "1").get();
        System.out.println(response.status().getStatus()+"-------"+response.getIndex());
    }
}
