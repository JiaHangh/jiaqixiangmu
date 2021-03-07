package com.baidu.response;

import com.baidu.shop.base.Result;
import com.baidu.shop.document.GoodsDoc;
import com.baidu.shop.entity.BrandEntity;
import com.baidu.shop.entity.CategoryEntity;
import com.baidu.shop.status.HTTPStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 2 * @ClassName GoodsResponse
 * 3 * @Description: TODO
 * 4 * @Author jiahang
 * 5 * @Date 2021/3/6
 * 6 * @Version V1.0
 * 7
 **/
@Data
@NoArgsConstructor   //提供无参构造函数
public class GoodsResponse extends Result<List<GoodsDoc>> {

    private Long total;

    private Long totalPage;

    private List<BrandEntity> brandList;

    private List<CategoryEntity> categoryList;

    private Map<String,List<String>> specMap;


    public GoodsResponse(Long total,Long totalPage,List<BrandEntity> brandList,List<CategoryEntity> categoryList,List<GoodsDoc> goodsDocs,Map<String, List<String>> specMap){

        super(HTTPStatus.OK,"",goodsDocs);
        this.total=total;
        this.totalPage=totalPage;
        this.brandList=brandList;
        this.categoryList=categoryList;
        this.specMap=specMap;
    }
}
