package com.baidu.shop.service;

import com.baidu.shop.com.baidu.shop.base.Result;
import com.baidu.shop.entity.CategoryEntity;
import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Api(value = "商品分类接口")
public interface CategoryService {
    @ApiOperation(value = "通过父id查询商品分类信息")
    @GetMapping(value = "category/list")
    Result<List<CategoryEntity>> getCategoryByPid(Integer pid);

    @ApiOperation(value = "通过id删除分类")
    @DeleteMapping("/category/delete")
    Result<JsonObject> delCategory(Integer id);
}
