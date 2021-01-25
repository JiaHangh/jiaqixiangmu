package com.baidu.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

/**
 * 2 * @ClassName CategoryBrandEntity
 * 3 * @Description: TODO
 * 4 * @Author jiahang
 * 5 * @Date 2021/1/23
 * 6 * @Version V1.0
 * 7
 **/
@Table(name = "tb_category_brand")
@Data
@NoArgsConstructor      // 生成一个无参数的构造方法z
@AllArgsConstructor     //生成一个包含所有参数的构造方法
public class CategoryBrandEntity {

    private Integer categoryId;

    private Integer brandId;
}
