package com.fyy.es.transport.entity;

import lombok.Data;

/**
 * @program: BabyStudy
 * @description:
 * @author: Feri
 * @create: 2020-01-09 14:18
 */
@Data
public class Student {
    private int id;
    private String name;

    public Student() {
    }

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
