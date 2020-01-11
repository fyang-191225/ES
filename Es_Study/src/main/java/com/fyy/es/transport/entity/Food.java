package com.fyy.es.transport.entity;

import lombok.Data;

/**
 * @author fyy
 * @date 2020/1/10 0010 下午 20:08
 */
@Data
public class Food {
    private int id;
    private String name;

    public Food() {
    }

    public Food(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
