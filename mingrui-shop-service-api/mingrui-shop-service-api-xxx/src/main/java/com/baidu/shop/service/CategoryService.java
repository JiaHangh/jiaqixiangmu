package com.baidu.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.Result;
import com.baidu.shop.entity.CategoryEntity;
import com.baidu.shop.validate.group.MingruiOperation;
import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "商品分类接口")
public interface CategoryService {
    @ApiOperation(value = "通过父id查询商品分类信息")
    @GetMapping(value = "category/list")
    Result<List<CategoryEntity>> getCategoryByPid(Integer pid);

    @ApiOperation(value = "通过id删除分类")
    @DeleteMapping(value = "/category/delete")
    Result<JsonObject> delCategory(Integer id);

    @ApiOperation(value = "根据id修改分类信息")
    @PutMapping(value = "/category/update")
    Result<JsonObject> editCategory(@Validated({MingruiOperation.Update.class}) @RequestBody CategoryEntity categoryEntity);

    @ApiOperation(value = "新增分类")
    @PostMapping(value = "/category/save")
    Result<JSONObject> addCategory(@Validated({MingruiOperation.Add.class}) @RequestBody CategoryEntity categoryEntity);

    @ApiOperation(value = "通过品牌id查询商品分类")
    @GetMapping(value = "/category/brand")
    Result<List<CategoryEntity>> getByBrandId(Integer brandId);
}
