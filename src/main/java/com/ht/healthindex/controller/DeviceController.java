package com.ht.healthindex.controller;

import com.github.pagehelper.PageInfo;
import com.ht.healthindex.dataobject.DeviceInfoDO;
import com.ht.healthindex.response.CommonResult;
import com.ht.healthindex.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/device")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @GetMapping("")
    public String main(){
        return "listDeviceInfo";
    }

    @GetMapping("/listDeviceInfo")
    @ResponseBody
    public CommonResult listDeviceInfo(Integer page,Integer number){
//        List<DeviceInfoDO> list = deviceService.listDeviceInfo();

//        接入层需要做参数校验
        if(null == page || !( page > 0)){
            page = 0;
        }
        if(null == number || !(number > 0 )){
            number = 30;
        }

        PageInfo<DeviceInfoDO> pageInfo = deviceService.listDeviceInfoByPage(page,number);

        return CommonResult.create(pageInfo);
    }
}
