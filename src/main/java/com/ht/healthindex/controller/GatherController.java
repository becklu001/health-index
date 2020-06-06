package com.ht.healthindex.controller;

import com.ht.healthindex.error.BusinessException;
import com.ht.healthindex.response.CommonResult;
import com.ht.healthindex.service.ComputeService;
import com.ht.healthindex.service.GatherService;
import com.ht.healthindex.service.model.HealthIndexByTypeModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
public class GatherController {
    @Autowired
    private GatherService gatherService;
    @Autowired
    private ComputeService computeService;

    @RequestMapping("/gatherHealthIndexByType")
    @ResponseBody

    public CommonResult gatherHealthIndexByType(){
        long t1 = System.currentTimeMillis();
        log.info("================开始调用【采集设备健康度(根据设备类型)列表】接口=================");

        int count = 0;
        try {
            List<HealthIndexByTypeModel> healthIndexList = computeService.listHealthIndexByType();
            count = gatherService.gatherHealthIndexByType(healthIndexList);
        } catch (BusinessException e) {
            e.printStackTrace();
            return CommonResult.create("fail",e);
        } finally {
            log.info("================结束调用采集设备健康度(根据设备类型)列表=========耗时：{} ms========",
                    System.currentTimeMillis() - t1);
        }
        return CommonResult.create(count);

    }

    @GetMapping("/gatherDeviceTypeHI")
    @ResponseBody
    public CommonResult gatherDeviceTypeHI(){
        long t1 = System.currentTimeMillis();
        log.info("================开始调用【采集设备类型健康度列表】接口=================");

        int count = 0;
        try {
            count = gatherService.gatherDeviceTypeHI();
        } catch (BusinessException e) {
            e.printStackTrace();
            return CommonResult.create("fail",e);
        } finally {
            log.info("================结束调用采集设备类型健康度列表=========耗时：{} ms========",
                    System.currentTimeMillis() - t1);
        }
        return CommonResult.create(count);
    }

    @GetMapping("/gatherStationHI")
    @ResponseBody
    public CommonResult gatherStationHI(){
        long t1 = System.currentTimeMillis();
        log.info("================开始调用【采集车站健康度列表】接口=================");

        int count = 0;
        try {
            count = gatherService.gatherStationHI();
        } catch (BusinessException e) {
            e.printStackTrace();
            return CommonResult.create("fail",e);
        }finally {
            log.info("================结束调用采集车站健康度列表=========耗时：{} ms========",
                    System.currentTimeMillis() - t1);
        }
        return CommonResult.create(count);
    }

}
