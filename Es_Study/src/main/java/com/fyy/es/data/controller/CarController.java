package com.fyy.es.data.controller;

import com.fyy.es.data.entity.Car;
import com.fyy.es.data.service.CarService;
import com.fyy.mybabystudy.common.vo.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author fyy
 * @date 2020/1/9 0009 下午 22:09
 */


@RestController
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping("/save")
    public R save(@RequestBody Car car) {
        return carService.save(car);
    }
    @DeleteMapping("/del")
    public R del( int id) {
        return carService.del(id);
    }
    @GetMapping("/single")
    public R single(int id) {
        return carService.single(id);
    }
    @GetMapping("/all")
    public R all() {
        return carService.all();
    }
}
