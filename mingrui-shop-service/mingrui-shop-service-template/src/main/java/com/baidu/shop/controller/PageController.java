package com.baidu.shop.controller;

import com.baidu.shop.service.PageService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * 2 * @ClassName PageController
 * 3 * @Description: TODO
 * 4 * @Author jiahang
 * 5 * @Date 2021/3/8
 * 6 * @Version V1.0
 * 7
 **/
@Controller
@RequestMapping(value = "item")
public class PageController {
    @Autowired
    private PageService pageService;

    @GetMapping(value = "{spuId}.html")
    public String test(@PathVariable(value = "spuId") Integer spuId,ModelMap modelMap){
        Map<String, Object> map = pageService.getGoodsInfo(spuId);
        modelMap.putAll(map);
        return "item";
    }
}
