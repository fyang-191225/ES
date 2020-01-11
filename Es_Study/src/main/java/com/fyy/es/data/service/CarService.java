package com.fyy.es.data.service;

import com.fyy.es.data.entity.Car;
import com.fyy.mybabystudy.common.vo.R;

/**
 * @author fyy
 * @date 2020/1/9 0009 下午 22:10
 */

public interface CarService {
    // 新增
    R save(Car car);
    // 删除
    R del(int id);
    // 查询
    R single(int id);
    // 查询全部
    R all();
}
