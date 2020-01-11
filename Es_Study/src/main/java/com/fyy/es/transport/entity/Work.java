package com.fyy.es.transport.entity;

import lombok.Data;

/**
 * @author fyy
 * @date 2020/1/10 0010 下午 20:15
 */
@Data
public class Work {
    private Integer id;
    private String name;
    private String company;
    private int money;//年薪

    public Work() {
    }

    public Work(Integer id, String name, String company, int money) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.money = money;
    }
}
