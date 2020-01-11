package com.fyy.es.data.dao;

import com.fyy.es.data.entity.Car;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

/**
 * @author fyy
 * @date 2020/1/9 0009 下午 22:09
 */
public interface CarDao extends ElasticsearchCrudRepository<Car,Integer> {
}
