package com.baidu.shop.mapper;

import com.baidu.shop.entity.CategoryBrandEntity;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;
//接口可以多继承,InsertListMapper用于批量新增

public interface CategoryBrandMapper extends InsertListMapper<CategoryBrandEntity>, Mapper<CategoryBrandEntity> {
}
