package com.fyy.es.transport;

import com.alibaba.fastjson.JSON;
import com.fyy.es.transport.entity.Food;
import com.fyy.es.util.ESTransportUtil;

import java.util.ArrayList;

/**
 * ES 的 批处理
 *
 * @author fyy
 * @date 2020/1/10 0010 下午 21:13
 */
public class ES03 {
    public static void main(String[] args) {
        // 批量增加
        ArrayList<ESTransportUtil.EsDocmentBean> list = new ArrayList<>();
        for (int i = 1000; i < 1010; i++) {
            list.add(new ESTransportUtil.EsDocmentBean(i + "", "j1908", "food", JSON.toJSONString(new Food(i, "鸡腿" + i))));
        }
        System.out.println(ESTransportUtil.batchSave(list));
    }
}
