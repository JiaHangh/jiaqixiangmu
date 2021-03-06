package com.baidu.shop.service.impl;

import com.baidu.shop.base.Result;
import com.baidu.shop.dto.*;
import com.baidu.shop.entity.*;
import com.baidu.shop.feign.BrandFeign;
import com.baidu.shop.feign.CategoryFeign;
import com.baidu.shop.feign.GoodsFeign;
import com.baidu.shop.feign.SpecificationFeign;
import com.baidu.shop.service.PageService;
import com.baidu.shop.utils.BaiduBeanUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 2 * @ClassName PageServiceImpl
 * 3 * @Description: TODO
 * 4 * @Author jiahang
 * 5 * @Date 2021/3/8
 * 6 * @Version V1.0
 * 7
 **/
//@Service
public class PageServiceImpl implements PageService {
    //@Autowired
    private GoodsFeign goodsFeign;

    //@Autowired
    private SpecificationFeign specificationFeign;

    //@Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    //@Autowired
    private BrandFeign brandFeign;

    //@Autowired
    private CategoryFeign categoryFeign;

    @Override
    public Map<String, Object> getGoodsInfo(Integer spuId) {
        HashMap<String, Object> goodsInfoMap = new HashMap<>();
        //spu
        SpuDTO spuDTO = new SpuDTO();
        spuDTO.setId(spuId);
        Result<List<SpuDTO>> spuResult = goodsFeign.getSpuInfo(spuDTO);
        SpuDTO spuResultData=null;
        if (spuResult.isSuccess()){
            spuResultData=spuResult.getData().get(0);
            goodsInfoMap.put("spuInfo",spuResultData);
        }
        //spuDetail
        Result<SpuDetailEntity> spuDetailResult = goodsFeign.getSpuDetailBydSpu(spuId);
        if (spuDetailResult.isSuccess()){

            goodsInfoMap.put("spuDetail",spuDetailResult.getData());
        }
        //分类信息
        Result<List<CategoryEntity>> categoryResult = categoryFeign.getCateByIds(
                String.join(
                        ",", Arrays.asList(spuResultData.getCid1()+"",spuResultData.getCid2()+"",spuResultData.getCid3()+"")
                )
        );
        if (categoryResult.isSuccess()){
            goodsInfoMap.put("categoryInfo",categoryResult.getData());
        }
        //品牌信息
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(spuResultData.getBrandId());
        Result<PageInfo<BrandEntity>> brandResult = brandFeign.getBrandInfo(brandDTO);
        if (brandResult.isSuccess()){
            goodsInfoMap.put("brandInfo",brandResult.getData().getList().get(0));
        }
        //sku
        Result<List<SkuDTO>> skusResult = goodsFeign.getSkusBySpuId(spuId);
        if (skusResult.isSuccess()){
            goodsInfoMap.put("skusInfo",skusResult.getData());
        }
        //规格组,规格参数(通用)
        SpecGroupDTO specGroupDTO = new SpecGroupDTO();
        specGroupDTO.setCid(spuResultData.getCid3());
        Result<List<SpecGroupEntity>> specGroupResult = specificationFeign.getSepcGroupInfo(specGroupDTO);
        if (specGroupResult.isSuccess()){
            List<SpecGroupEntity> specGroupList = specGroupResult.getData();
            List<SpecGroupDTO> specGroupAndParam = specGroupList.stream().map(specGroup -> {
                SpecGroupDTO specGroupDTO1 = BaiduBeanUtil.copyProperties(specGroup, specGroupDTO.getClass());

                SpecParamDTO specParamDTO = new SpecParamDTO();
                specParamDTO.setGroupId(specGroupDTO1.getId());
                specParamDTO.setGeneric(true);
                Result<List<SpecParamEntity>> specParamResult = specificationFeign.getSepcParamInfo(specParamDTO);
                if (specParamResult.isSuccess()){
                    specGroupDTO1.setSpecList(specParamResult.getData());
                }
                return specGroupDTO1;
            }).collect(Collectors.toList());
            goodsInfoMap.put("specGroupAndParam",specGroupAndParam);
        }
        //特殊规格
        SpecParamDTO specParamDTO = new SpecParamDTO();
        specParamDTO.setCid(spuResultData.getCid3());
        specParamDTO.setGeneric(false);
        Result<List<SpecParamEntity>> specParamResult = specificationFeign.getSepcParamInfo(specParamDTO);
        if (specParamResult.isSuccess()){
            List<SpecParamEntity> specParamResultData = specParamResult.getData();
            Map<Integer, String> specParamMap = new HashMap<>();
            specParamResultData.stream().forEach(specParam -> specParamMap.put(specParam.getId(),specParam.getName()));
            goodsInfoMap.put("specParamMap",specParamMap);
        }
        return goodsInfoMap;
    }
}
