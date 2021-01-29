package com.baidu.shop.service;

import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SpuDTO;
import com.baidu.shop.entity.BrandEntity;
import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import springfox.documentation.spring.web.json.Json;

import java.util.List;

/**
 * 2 * @ClassName GoodsService
 * 3 * @Description: TODO
 * 4 * @Author jiahang
 * 5 * @Date 2021/1/29
 * 6 * @Version V1.0
 * 7
 **/
@Api(value = "商品接口")
public interface GoodsService {

    @ApiOperation(value = "获取spu信息")
    @GetMapping(value = "/goods/getSpuInfo")
    Result<List<SpuDTO>> getSpuInfo(SpuDTO spuDTO);

    @ApiOperation(value = "根据分类查询品牌")
    @GetMapping(value = "/brand/getBrandInfoByCategoryId")
    Result<List<BrandEntity>> getBrandInfoByCategoryId(Integer cid);

    @ApiOperation(value = "新增商品")
    @PostMapping(value = "/goodss/save")
    Result<JsonObject> saveGoods(@RequestBody SpuDTO spuDTO);
}
