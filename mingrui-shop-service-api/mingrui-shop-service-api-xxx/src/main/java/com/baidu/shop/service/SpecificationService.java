package com.baidu.shop.service;

import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SpecGroupDTO;
import com.baidu.shop.dto.SpecParamDTO;
import com.baidu.shop.entity.SpecGroupEntity;
import com.baidu.shop.entity.SpecParamEntity;
import com.baidu.shop.validate.group.MingruiOperation;
import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.List;

/**
 * 2 * @ClassName SpecificationService
 * 3 * @Description: TODO
 * 4 * @Author jiahang
 * 5 * @Date 2021/1/24
 * 6 * @Version V1.0
 * 7
 **/
@Api(tags = "规格接口")
public interface SpecificationService {

    @ApiOperation(value = "通过条件查询规格组")
    @GetMapping(value = "/specgroup/groups")
    Result<List<SpecGroupEntity>> getSepcGroupInfo(@SpringQueryMap SpecGroupDTO specGroupDTO);

    @ApiOperation(value = "新增规格组")
    @PostMapping(value = "/specgroup/save")
    Result<JsonObject> saveSepcGroup(@Validated ({MingruiOperation.Add.class}) @RequestBody SpecGroupDTO specGroupDTO);

    @ApiOperation(value = "修改规格组")
    @PutMapping(value = "/specgroup/save")
    Result<JsonObject> editSpecGroup(@Validated ({MingruiOperation.Update.class}) @RequestBody SpecGroupDTO specGroupDTO);

    @ApiOperation(value = "删除规格组")
    @DeleteMapping(value = "specgroup/delete/{id}")
    Result<JsonObject> deleteSpecGroup(@PathVariable Integer id);

    @ApiOperation(value = "查询规格参数")
    @GetMapping(value = "/specparam/groups")
    Result<List<SpecParamEntity>> getSepcParamInfo(@SpringQueryMap SpecParamDTO specParamDTO);

    @ApiOperation(value = "新增规格组参数")
    @PostMapping(value = "/specparam/save")
    Result<JsonObject> saveSpecParam(@RequestBody SpecParamDTO specParamDTO);

    @ApiOperation(value = "修改规格组参数")
    @PutMapping(value = "/specparam/save")
    Result<JsonObject> editSpecParam(@RequestBody SpecParamDTO specParamDTO);

    @ApiOperation(value = "删除规格组参数")
    @DeleteMapping(value = "specparam/delete")
    Result<JsonObject> deleteSpecParam(Integer id);
}
