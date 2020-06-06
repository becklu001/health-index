package com.ht.healthindex.response;

import lombok.Data;

/*
*   返回给前端的通用返回对象
*
* */
@Data
public class CommonResult {
//    status表示状态，只有"success"和"fail"两种状态
    private String status;
//    data是真实的返回数据
    private Object data;

    public static CommonResult create(Object data){
        return CommonResult.create("success",data);
    }

    public static CommonResult create(String status,Object data){
        CommonResult result = new CommonResult();
        result.setStatus(status);
        result.setData(data);
        return result;
    }
}
