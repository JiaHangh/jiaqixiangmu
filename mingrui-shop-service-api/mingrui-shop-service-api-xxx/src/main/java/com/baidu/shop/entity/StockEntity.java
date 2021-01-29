package com.baidu.shop.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 2 * @ClassName StockEntity
 * 3 * @Description: TODO
 * 4 * @Author jiahang
 * 5 * @Date 2021/1/29
 * 6 * @Version V1.0
 * 7
 **/
@Table(name = "tb_stock")
@Data
public class StockEntity {
    @Id
    private Long skuId;

    private Integer seckillStock;

    private Integer seckillTotal;

    private Integer stock;
}
