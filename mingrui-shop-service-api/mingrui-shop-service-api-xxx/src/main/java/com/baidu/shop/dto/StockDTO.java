package com.baidu.shop.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 2 * @ClassName StockDTO
 * 3 * @Description: TODO
 * 4 * @Author jiahang
 * 5 * @Date 2021/1/29
 * 6 * @Version V1.0
 * 7
 **/
@ApiModel(value = "库存数据传输类")
@Data
public class StockDTO {
    @ApiModelProperty(value = "主键",example = "1")
    private Long skuId;

    @ApiModelProperty(value = "可秒杀库存", example = "1")
    private Integer seckillStock;

    @ApiModelProperty(value = "秒杀总数量", example = "1")
    private Integer seckillTotal;

    @ApiModelProperty(value = "库存数量", example = "1")
    private Integer stock;
}
