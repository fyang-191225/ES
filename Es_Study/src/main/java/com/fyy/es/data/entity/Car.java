package com.fyy.es.data.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * @author fyy
 * @date 2020/1/9 0009 下午 22:10
 */
@Document(indexName = "j1908",type = "car") //标记数据所在的索引和类型
@Data
public class Car {
    @Id //标记主键  唯一
    private int id;
    @Field(name = "_name") //字段设置
    private String name;
    private int money;
}
