package com.baidu.shop.service.impl;

import com.baidu.shop.com.baidu.shop.base.BaseApiService;
import com.baidu.shop.com.baidu.shop.base.Result;
import com.baidu.shop.entity.CategoryEntity;
import com.baidu.shop.mapper.CategoryMapper;
import com.baidu.shop.service.CategoryService;
import com.baidu.shop.utils.ObjectUtil;
import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * 2 * @ClassName CategoryServiceImpl
 * 3 * @Description: TODO
 * 4 * @Author jiahang
 * 5 * @Date 2021/1/19
 * 6 * @Version V1.0
 * 7
 **/
@RestController
public class CategoryServiceImpl extends BaseApiService implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    //查询
    @Override
    public Result<List<CategoryEntity>> getCategoryByPid(Integer pid) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setParentId(pid);
        List<CategoryEntity> list = categoryMapper.select(categoryEntity);

        return this.setResultSuccess(list);
    }

    //删除
    @Override
    public Result<JsonObject> delCategory(Integer id) {
        if (ObjectUtil.isNull(id)) return this.setResultError("id不存在");
        CategoryEntity categoryEntity = categoryMapper.selectByPrimaryKey(id);
        if (ObjectUtil.isNull(categoryEntity)) return this.setResultError("没有此数据");
        if (categoryEntity.getIsParent()==1) return this.setResultError("当前节点为父节点");

        Example example = new Example(CategoryEntity.class);
        example.createCriteria().andEqualTo("parentId",categoryEntity.getParentId());
        List<CategoryEntity> list = categoryMapper.selectByExample(example);
        //如果查出父节点下的节点<=1时，删除了这个节点，就把该节点的父节点变为叶子节点
        if (list.size()<=1){
            CategoryEntity parentCategory = new CategoryEntity();
            parentCategory.setId(categoryEntity.getParentId());
            parentCategory.setParentId(0);
            categoryMapper.updateByPrimaryKeySelective(parentCategory);
        }

        //如果查出父节点下有多个节点，直接删除
        categoryMapper.deleteByPrimaryKey(id);

        return this.setResultSuccess();
    }

    //修改
    @Override
    public Result<JsonObject> editCategory(CategoryEntity categoryEntity) {
        try{
            categoryMapper.updateByPrimaryKeySelective(categoryEntity);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return this.setResultSuccess();
    }
}
