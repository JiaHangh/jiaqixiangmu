package com.baidu.shop.base;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 2 * @ClassName Result
 * 3 * @Description: TODO
 * 4 * @Author jiahang
 * 5 * @Date 2020/12/22
 * 6 * @Version V1.0
 * 7
 **/
@Data
@NoArgsConstructor
public class Result<T> {
    private Integer code;//返回码

    private String message;//返回消息

    private T data;//返回数据

    public Result(Integer code, String message, Object data){
        this.code = code;
        this.message = message;
        this.data = (T) data;
    }
}
