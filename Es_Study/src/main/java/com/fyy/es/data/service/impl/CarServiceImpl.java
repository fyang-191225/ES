package com.fyy.es.data.service.impl;

import com.fyy.es.data.dao.CarDao;
import com.fyy.es.data.entity.Car;
import com.fyy.es.data.service.CarService;
import com.fyy.mybabystudy.common.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author fyy
 * @date 2020/1/9 0009 下午 22:12
 */

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarDao carDao;

    @Override
    public R save(Car car) {
        return R.setResult(carDao.save(car) != null, "成功");
    }

    @Override
    public R del(int id) {
        Car r = new Car();
        r.setId(id);
        carDao.delete(r);
        return R.ok();
    }

    @Override
    public R single(int id) {
        return R.ok(carDao.findById(id));
    }

    @Override
    public R all() {
        return R.ok(carDao.findAll(Sort.by(Sort.Order.desc("id"))));
    }
}
