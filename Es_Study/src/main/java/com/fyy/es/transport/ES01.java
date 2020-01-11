package com.fyy.es.transport;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetSocketAddress;

/**
 * ES  transport 基本实现
 *
 * @author fyy
 * @date 2020/1/10 0010 下午 19:35
 */
/*
<dependency>
    <groupId>org.elasticsearch</groupId>
    <artifactId>elasticsearch-x-content</artifactId>
    <version>6.8.5</version>
</dependency> <!-- https://mvnrepository.com/artifact/org.elasticsearch.client/transport -->
<dependency>
    <groupId>org.elasticsearch.client</groupId>
    <artifactId>transport</artifactId>
    <version>6.8.5</version>
</dependency>
*/

public class ES01 {
    public static void main(String[] args) {
        // 1创建设置对象
        Settings settings = Settings.builder().put("cluster.name", "qfjava").build();

        // 2 创建 链接对象
        Client client = new PreBuiltTransportClient(settings).addTransportAddress(
                new TransportAddress(
                        new InetSocketAddress("39.105.189.141", 9300)));

        // 3 操作Es 服务器 Crud
        // 新增  新增或修改  有id就是新增 无id就是修改 * 参数说明： * 1、索引名称 * 2、类型 * 3、id 唯一值*/
//        IndexResponse indexResponse1 = client.prepareIndex("java1908", "food", "1").setSource("{'id':10,'name':'鸡腿'}", XContentType.JSON).get();
        IndexResponse indexResponse = client.prepareIndex("es1902", "food", "1")
                .setSource("{\"id\":1,\"name\":\"鸡腿\"}", XContentType.JSON).get();
        System.out.println("新增：" + indexResponse.status().toString());
        // 4 销毁

//        // 查询
//        GetResponse response=client.prepareGet("es1902","food","1").get();
//        String json=response.getSourceAsString();
//        Food food = JSON.parseObject(json, Food.class);
//        System.out.println(food);
        client.close();
    }
}
