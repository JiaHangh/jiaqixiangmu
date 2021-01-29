package com.baidu.shop.service.impl;

import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SkuDTO;
import com.baidu.shop.dto.SpuDTO;
import com.baidu.shop.dto.SpuDetailDTO;
import com.baidu.shop.entity.*;
import com.baidu.shop.mapper.*;
import com.baidu.shop.service.GoodsService;
import com.baidu.shop.status.HTTPStatus;
import com.baidu.shop.utils.BaiduBeanUtil;
import com.baidu.shop.utils.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 2 * @ClassName GoodsServiceImpl
 * 3 * @Description: TODO
 * 4 * @Author jiahang
 * 5 * @Date 2021/1/29
 * 6 * @Version V1.0
 * 7
 **/
@RestController
public class GoodsServiceImpl extends BaseApiService implements GoodsService {

    @Resource
    private SpuMapper spuMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private BrandMapper brandMapper;

    @Resource
    private SkuMapper skuMapper;

    @Resource
    private StockMapper stockMapper;

    @Resource
    private SpuDetailMapper spuDetailMapper;


    @Override
    public Result<List<SpuDTO>> getSpuInfo(SpuDTO spuDTO) {
        if (ObjectUtil.isNotNull(spuDTO.getPage()) && ObjectUtil.isNotNull(spuDTO.getRows()))
            PageHelper.startPage(spuDTO.getPage(),spuDTO.getRows());

        Example example = new Example(SpuEntity.class);
        Example.Criteria criteria = example.createCriteria();
        //判断是否上架
        if (ObjectUtil.isNotNull(spuDTO.getSaleable()) && spuDTO.getSaleable()<2)
            criteria.andEqualTo("saleable",spuDTO.getSaleable());
        //按标题模糊匹配
        if (!StringUtils.isEmpty(spuDTO.getTitle()))
            criteria.andLike("title","%"+spuDTO.getTitle()+"%");
        //排序
        if (!StringUtils.isEmpty(spuDTO.getSort()))
            example.setOrderByClause(spuDTO.getOrderBy());

        List<SpuEntity> spuEntities = spuMapper.selectByExample(example);

        List<SpuDTO> SpuDTOList = spuEntities.stream().map(spuEntity -> {
            SpuDTO spuDTO1 = BaiduBeanUtil.copyProperties(spuEntity, SpuDTO.class);
            //通过分类id集合查询数据
            List<CategoryEntity> categoryEntities = categoryMapper.selectByIdList(Arrays.asList(spuEntity.getCid1(), spuEntity.getCid2(), spuEntity.getCid3()));
            // 遍历集合并且将分类名称用 / 拼接
            String categoryName = categoryEntities.stream().map(categoryEntity -> categoryEntity.getName()).collect(Collectors.joining("/"));
            //将在商品分类表中查到的商品分类名赋值给商品表中的商品分类名
            spuDTO1.setCategoryName(categoryName);
            //根据商品中的brandid到品牌表查询品牌名字
            BrandEntity brandEntity = brandMapper.selectByPrimaryKey(spuEntity.getBrandId());
            //将在品牌表中查到的品牌名赋值给商品表中的品牌名
            spuDTO1.setBrandName(brandEntity.getName());

            return spuDTO1;
        }).collect(Collectors.toList());

        PageInfo<SpuEntity> spuEntityPageInfo = new PageInfo<>(spuEntities);
        //把分页的数据放在message中传到页面
        return this.setResult(HTTPStatus.OK,spuEntityPageInfo.getTotal()+"",SpuDTOList);
    }

    //先查出来品牌信息
    @Override
    public Result<List<BrandEntity>> getBrandInfoByCategoryId(Integer cid) {
        List<BrandEntity> list = brandMapper.getBrandInfoByCategoryId(cid);
        return this.setResultSuccess(list);
    }

    @Override
    public Result<JsonObject> saveGoods(SpuDTO spuDTO) {
        Date date = new Date();
        //新增spu,新增返回主键, 给必要字段赋默认值
        SpuEntity spuEntity = BaiduBeanUtil.copyProperties(spuDTO, SpuEntity.class);
        spuEntity.setSaleable(1);
        spuEntity.setValid(1);
        spuEntity.setCreateTime(date);
        spuEntity.setLastUpdateTime(date);
        spuMapper.insertSelective(spuEntity);

        //新增spuDetail
        SpuDetailDTO spuDetail = spuDTO.getSpuDetail();
        SpuDetailEntity spuDetailEntity = BaiduBeanUtil.copyProperties(spuDetail, SpuDetailEntity.class);
        spuDetailEntity.setSpuId(spuEntity.getId());
        spuDetailMapper.insertSelective(spuDetailEntity);

        List<SkuDTO> skus = spuDTO.getSkus();
        skus.stream().forEach(skuDTO -> {
            //spuDTO中包含sku表新增需要的字段
            SkuEntity skuEntity = BaiduBeanUtil.copyProperties(skuDTO, SkuEntity.class);
            skuEntity.setSpuId(spuEntity.getId());
            skuEntity.setCreateTime(date);
            skuEntity.setLastUpdateTime(date);
            skuMapper.insertSelective(skuEntity);

            //新增stock
            StockEntity stockEntity = new StockEntity();
            stockEntity.setSkuId(skuEntity.getId());
            stockEntity.setStock(skuDTO.getStock());
            stockMapper.insertSelective(stockEntity);

        });
        return this.setResultSuccess();
    }
}
