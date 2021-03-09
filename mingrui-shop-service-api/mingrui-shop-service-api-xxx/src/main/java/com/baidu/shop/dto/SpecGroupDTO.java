package com.baidu.shop.dto;

import com.baidu.shop.entity.SpecParamEntity;
import com.baidu.shop.validate.group.MingruiOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 2 * @ClassName SpecGroupDTO
 * 3 * @Description: TODO
 * 4 * @Author jiahang
 * 5 * @Date 2021/1/24
 * 6 * @Version V1.0
 * 7
 **/
@ApiModel(value = "规格组数据传输DTO")
@Data
public class SpecGroupDTO {
    @ApiModelProperty(value = "主键",example = "1")
    @NotNull(message = "主键id不能为空",groups = {MingruiOperation.Update.class})
    private Integer id;

    @ApiModelProperty(value = "分类id",example = "1")
    @NotNull(message = "类型Id不能为空",groups = {MingruiOperation.Add.class})
    private Integer cid;

    @ApiModelProperty(value = "规格组名称")
    @NotEmpty(message = "规格组名称不能为空",groups = {MingruiOperation.Add.class})
    private String name;

    private List<SpecParamEntity> specList;
}
