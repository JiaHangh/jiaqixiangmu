package com.baidu.shop.service;

import com.baidu.shop.base.Result;
import com.baidu.shop.dto.BrandDTO;
import com.baidu.shop.entity.BrandEntity;
import com.baidu.shop.validate.group.MingruiOperation;
import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "品牌接口")
public interface BrandService {

    @ApiOperation(value = "查询品牌信息")
    @GetMapping(value = "/brand/list")
    Result<List<BrandEntity>> getBrandInfo(BrandDTO brandDTO);

    @ApiOperation(value = "品牌新增")
    @PostMapping(value = "/brand/save")
    Result<JsonObject> save(@Validated({MingruiOperation.Add.class}) @RequestBody BrandDTO brandDTO);

    @ApiOperation(value = "品牌修改")
    @PutMapping(value = "/brand/save")
    Result<JsonObject> editBrand(@Validated({MingruiOperation.Update.class}) @RequestBody BrandDTO brandDTO);

    @ApiOperation(value = "通过id删除品牌信息")
    @DeleteMapping(value = "/brand/deleteBrand")
    Result<JsonObject> deleteBrand(Integer id);

    @ApiOperation(value = "通过品牌id集合获取品牌")
    @GetMapping(value = "/brand/getBrandByIds")
    Result<List<BrandEntity>> getBrandByIds(@RequestParam String ids);
}
