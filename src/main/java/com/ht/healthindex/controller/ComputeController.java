package com.ht.healthindex.controller;

import com.ht.healthindex.dataobject.HistoryAlarmDO;
import com.ht.healthindex.error.BusinessException;
import com.ht.healthindex.response.CommonResult;
import com.ht.healthindex.service.ComputeService;
import com.ht.healthindex.service.HistoryAlarmService;
import com.ht.healthindex.service.model.DeviceHealthIndexModel;
import com.ht.healthindex.service.model.HealthIndexByTypeModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/compute")
@Slf4j
public class ComputeController {
    @Autowired
    private ComputeService computeService;
    @Autowired
    private HistoryAlarmService historyAlarmService;

    @GetMapping("/deviceHealthIndex/{falarmdevice}")
    @ResponseBody
    public CommonResult computeDeviceAlarmHealthIndex(@PathVariable("falarmdevice") String falarmdevice, Integer fstationid){
//        入参校验
        if(null == falarmdevice){
            falarmdevice = "2DG";
        }
        if( null == fstationid || fstationid < 0){
            fstationid = 1;
        }

        List<HistoryAlarmDO> alarmDOList =  historyAlarmService.listByAlarmDeviceAndStationId(falarmdevice,fstationid);
//        List<HistoryAlarmDO> alarmDOList =  historyAlarmService.listByAlarmDevice("2DG");

        if(alarmDOList == null || alarmDOList.size() == 0){
//            System.out.println("不存在设备名为："+fdevicename+" 的报警信息");
            log.info("-------不存在车站id为{},设备名为{}的报警信息-------",fstationid,falarmdevice);
            return CommonResult.create(null);
        }

        DeviceHealthIndexModel deviceHealthIndexModel =
                computeService.getAlarmDeviceHealthIndex(
                        falarmdevice,fstationid, alarmDOList);

        return CommonResult.create(deviceHealthIndexModel);
    }

    @GetMapping("/computeSkylightHealthIndex/{stationId}/{deviceName}")
    @ResponseBody
    public CommonResult computeSkylightHealthIndex(@PathVariable("stationId") Integer stationId,
                                                   @PathVariable("deviceName") String deviceName){
                DeviceHealthIndexModel healthIndexModel = null;
//                入参校验
        if(null == stationId || !(stationId > 0)){
            log.info("------车站id不合法------");
            stationId = 1;
        }
        if(deviceName == null){
            log.info("------设备名称为空------");
            deviceName = "2DG";
        }

        healthIndexModel = computeService.getSkylightHealthIndex(stationId,deviceName);
        return CommonResult.create(healthIndexModel);
    }

    @GetMapping("/computeManualHealthIndex/{deviceId}")
    @ResponseBody
    public CommonResult computeManualHealthIndex(@PathVariable("deviceId") Integer deviceId){
        DeviceHealthIndexModel healthIndexModel = null;
//        入参校验
        if(null == deviceId || !(deviceId > 0)){
            log.info("------设备id不合法------");
            deviceId = 1;
        }

        healthIndexModel = computeService.getManualAdjustHealthIndex(deviceId);
        return CommonResult.create(healthIndexModel);
    }

    @GetMapping("/computeLifeHealthIndex/{deviceId}")
    @ResponseBody
    public CommonResult computeLifeHealthIndex(@PathVariable("deviceId") Integer deviceId){
//        入参校验
        if(null == deviceId || !(deviceId > 0)){
            log.info("------设备id不合法------");
            deviceId = 1;
        }

        DeviceHealthIndexModel healthIndexModel = null;
        try {
            healthIndexModel = computeService.getLifeHealthIndex(deviceId);
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        return CommonResult.create(healthIndexModel);
    }

    /*
    *   查询设备健康度（根据设备类型）列表接口
    * */
    @GetMapping("/computeHealthIndexByTypeList")
    @ResponseBody
    public CommonResult ComputeHealthIndexByTypeList(){
        long t1 = System.currentTimeMillis();
        log.info("================开始调用【查询设备健康度(根据设备类型)列表】接口=================");

        List<HealthIndexByTypeModel> healthIndexList = new ArrayList<>();
        try {
            healthIndexList = computeService.listHealthIndexByType();
        } catch (BusinessException e) {
            e.printStackTrace();
            return CommonResult.create("fail",e);
        }finally {
            log.info("================结束调用查询设备健康度(根据设备类型)列表=========耗时：{} ms========",
                    System.currentTimeMillis() - t1);
        }
        return CommonResult.create(healthIndexList);
    }
}
