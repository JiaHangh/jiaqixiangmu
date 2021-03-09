package com.baidu.shop.service.impl;

import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.BrandDTO;
import com.baidu.shop.entity.BrandEntity;
import com.baidu.shop.entity.CategoryBrandEntity;
import com.baidu.shop.mapper.BrandMapper;
import com.baidu.shop.mapper.CategoryBrandMapper;
import com.baidu.shop.service.BrandService;
import com.baidu.shop.utils.BaiduBeanUtil;
import com.baidu.shop.utils.ObjectUtil;
import com.baidu.shop.utils.PinyinUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 2 * @ClassName BrandServiceImpl
 * 3 * @Description: TODO
 * 4 * @Author jiahang
 * 5 * @Date 2021/1/22
 * 6 * @Version V1.0
 * 7
 **/
@RestController
public class BrandServiceImpl extends BaseApiService implements BrandService {

    @Resource
    private BrandMapper brandMapper;

    @Resource
    private CategoryBrandMapper categoryBrandMapper;

    @Override
    public Result<List<BrandEntity>> getBrandByIds(String ids) {

        List<Integer> brandIdsArr = Arrays.asList(ids.split(","))
                .stream().map(idStr -> Integer.parseInt(idStr)).collect(Collectors.toList());
        List<BrandEntity> list = brandMapper.selectByIdList(brandIdsArr);
        return this.setResultSuccess(list);
    }

    @Override
    public Result<PageInfo<BrandEntity>> getBrandInfo(BrandDTO brandDTO) {
        if (ObjectUtil.isNotNull(brandDTO.getPage()) && ObjectUtil.isNotNull(brandDTO.getRows()))
            PageHelper.startPage(brandDTO.getPage(),brandDTO.getRows());//分页

        //排序
        if (!StringUtils.isEmpty(brandDTO.getSort())) PageHelper.orderBy(brandDTO.getOrderBy());

        BrandEntity brandEntity = BaiduBeanUtil.copyProperties(brandDTO, BrandEntity.class);

        Example example = new Example(BrandEntity.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(brandEntity.getName()))
            criteria.andLike("name","%" + brandEntity.getName() + "%");
        if(ObjectUtil.isNotNull(brandDTO.getId()))
            criteria.andEqualTo("id",brandDTO.getId());

        List<BrandEntity> list = brandMapper.selectByExample(example);
        PageInfo<BrandEntity> brandEntityPageInfo = new PageInfo<>(list);

        return this.setResultSuccess(brandEntityPageInfo);
    }

    @Override
    public Result<JsonObject> save(BrandDTO brandDTO) {
        BrandEntity brandEntity = BaiduBeanUtil.copyProperties(brandDTO, BrandEntity.class);
        brandEntity.setLetter(PinyinUtil.getUpperCase(String.valueOf(brandEntity.getName().toCharArray()[0]),false).toCharArray()[0]);
        brandMapper.insertSelective(brandEntity);

//        if (StringUtils.isEmpty(brandDTO.getCategories())) throw new RuntimeException();
//        if (brandDTO.getCategories().contains(",")){
//            String[] split = brandDTO.getCategories().split(",");
//            List<CategoryBrandEntity> collect = Arrays.asList(split).stream().map(categoryIds -> new CategoryBrandEntity(Integer.valueOf(categoryIds), brandEntity.getId())).collect(Collectors.toList());
//            categoryBrandMapper.insertList(collect);
//        }else{
//            //实体类需要些有参无参构造的注解
//            CategoryBrandEntity categoryBrandEntity = new CategoryBrandEntity(Integer.valueOf(brandDTO.getCategories()),brandEntity.getId());
//            categoryBrandMapper.insertSelective(categoryBrandEntity);
//        }
        this.insertBrandId(brandDTO.getCategories(),brandEntity.getId());

        return this.setResultSuccess();
    }

    @Override
    public Result<JsonObject> editBrand(BrandDTO brandDTO) {
        BrandEntity brandEntity = BaiduBeanUtil.copyProperties(brandDTO, BrandEntity.class);
        brandEntity.setLetter(PinyinUtil.getUpperCase(String.valueOf(brandEntity.getName().toCharArray()[0]),false).toCharArray()[0]);
        brandMapper.updateByPrimaryKeySelective(brandEntity);

        this.deleteCategoryBrandList(brandEntity.getId());

        this.insertBrandId(brandDTO.getCategories(),brandEntity.getId());
        return this.setResultSuccess();
    }

    @Override
    public Result<JsonObject> deleteBrand(Integer id) {
        brandMapper.deleteByPrimaryKey(id);
        this.deleteCategoryBrandList(id);
        return this.setResultSuccess();
    }

    //通过brandId删除中间表的数据
    private void deleteCategoryBrandList(Integer id){
        Example example = new Example(CategoryBrandEntity.class);
        example.createCriteria().andEqualTo("brandId",id);
        categoryBrandMapper.deleteByExample(example);
    }

    private void insertBrandId(String categories,Integer brandId){

        if (StringUtils.isEmpty(categories)) throw new RuntimeException();
        if (categories.contains(",")){
            categoryBrandMapper.insertList(
                    Arrays.asList(categories.split(","))
                            .stream()
                            .map(categoryIds->new CategoryBrandEntity(Integer.valueOf(categoryIds),brandId))
                            .collect(Collectors.toList())
            );
        }else{
            CategoryBrandEntity categoryBrandEntity = new CategoryBrandEntity();
            categoryBrandEntity.setCategoryId(Integer.valueOf(categories));
            categoryBrandEntity.setBrandId(brandId);
            categoryBrandMapper.insertSelective(categoryBrandEntity);
        }
    }
}
