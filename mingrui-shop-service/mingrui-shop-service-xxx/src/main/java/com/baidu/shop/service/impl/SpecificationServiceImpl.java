package com.baidu.shop.service.impl;

import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SpecGroupDTO;
import com.baidu.shop.dto.SpecParamDTO;
import com.baidu.shop.entity.SpecGroupEntity;
import com.baidu.shop.entity.SpecParamEntity;
import com.baidu.shop.mapper.SpecGroupMapper;
import com.baidu.shop.mapper.SpecParamMapper;
import com.baidu.shop.service.SpecificationService;
import com.baidu.shop.status.HTTPStatus;
import com.baidu.shop.utils.BaiduBeanUtil;
import com.baidu.shop.utils.ObjectUtil;
import com.google.gson.JsonObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * 2 * @ClassName SpecificationServiceImpl
 * 3 * @Description: TODO
 * 4 * @Author jiahang
 * 5 * @Date 2021/1/24
 * 6 * @Version V1.0
 * 7
 **/
@RestController
public class SpecificationServiceImpl extends BaseApiService implements SpecificationService {

    @Resource
    private SpecGroupMapper specGroupMapper;

    @Resource
    private SpecParamMapper specParamMapper;

    @Override
    public Result<List<SpecGroupEntity>> getSepcGroupInfo(SpecGroupDTO specGroupDTO) {
        Example example = new Example(SpecGroupEntity.class);
        if (ObjectUtil.isNotNull(specGroupDTO.getCid())){
            example.createCriteria().andEqualTo("cid", BaiduBeanUtil.copyProperties(specGroupDTO,SpecGroupEntity.class).getCid());
        }
        List<SpecGroupEntity> specGroupEntities = specGroupMapper.selectByExample(example);
        return this.setResultSuccess(specGroupEntities);
    }

    @Transactional
    @Override
    public Result<JsonObject> saveSepcGroup(SpecGroupDTO specGroupDTO) {
        specGroupMapper.insertSelective(BaiduBeanUtil.copyProperties(specGroupDTO,SpecGroupEntity.class));
        return this.setResultSuccess();
    }

    @Transactional
    @Override
    public Result<JsonObject> editSpecGroup(SpecGroupDTO specGroupDTO) {
        specGroupMapper.updateByPrimaryKeySelective(BaiduBeanUtil.copyProperties(specGroupDTO,SpecGroupEntity.class));
        return this.setResultSuccess();
    }

    @Override
    public Result<JsonObject> deleteSpecGroup(Integer id) {
        //规格组删除之前要判断一下该规格组下有没有规格组参数，有则不让删除
        Example example = new Example(SpecParamEntity.class);
        example.createCriteria().andEqualTo("groupId", id);
        List<SpecParamEntity> specParamEntities = specParamMapper.selectByExample(example);
        if (specParamEntities.size()!=0) return this.setResultError(HTTPStatus.OPERATION_ERROR,"该规格组内有规格组参数,无法删除");

        specGroupMapper.deleteByPrimaryKey(id);
        return this.setResultSuccess();
    }

    //规格组参数查询
    @Override
    public Result<List<SpecParamEntity>> getSepcParamInfo(SpecParamDTO specParamDTO) {
        SpecParamEntity specParamEntity = BaiduBeanUtil.copyProperties(specParamDTO, SpecParamEntity.class);

        Example example = new Example(SpecParamEntity.class);
        example.createCriteria().andEqualTo("groupId",specParamEntity.getGroupId());
        List<SpecParamEntity> specParamEntities = specParamMapper.selectByExample(example);
        return this.setResultSuccess(specParamEntities);
    }

    //规格组参数新增
    @Transactional
    @Override
    public Result<JsonObject> saveSpecParam(SpecParamDTO specParamDTO) {
        SpecParamEntity specParamEntity = BaiduBeanUtil.copyProperties(specParamDTO, SpecParamEntity.class);
        specParamMapper.insertSelective(specParamEntity);
        return this.setResultSuccess();
    }

    //规格组参数修改
    @Transactional
    @Override
    public Result<JsonObject> editSpecParam(SpecParamDTO specParamDTO) {
        SpecParamEntity specParamEntity = BaiduBeanUtil.copyProperties(specParamDTO, SpecParamEntity.class);
        specParamMapper.updateByPrimaryKeySelective(specParamEntity);
        return this.setResultSuccess();
    }

    //规格组参数删除
    @Transactional
    @Override
    public Result<JsonObject> deleteSpecParam(Integer id) {
        specParamMapper.deleteByPrimaryKey(id);
        return this.setResultSuccess();
    }
}
