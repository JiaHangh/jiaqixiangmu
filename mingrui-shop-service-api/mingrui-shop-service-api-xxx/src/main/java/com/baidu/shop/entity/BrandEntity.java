package com.baidu.shop.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 2 * @ClassName BrandEntity
 * 3 * @Description: TODO
 * 4 * @Author jiahang
 * 5 * @Date 2021/1/22
 * 6 * @Version V1.0
 * 7
 **/
@Table(name = "tb_brand")
@Data
public class BrandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键自增
    private Integer id;

    private String name;

    private String image;

    private Character letter;
}
