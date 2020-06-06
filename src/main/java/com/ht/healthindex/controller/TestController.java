package com.ht.healthindex.controller;

import com.ht.healthindex.dao.DeviceTypeHIDOMapper;
import com.ht.healthindex.dao.HealthIndexByTypeDOMapper;
import com.ht.healthindex.dataobject.*;
import com.ht.healthindex.error.BusinessException;
import com.ht.healthindex.response.CommonResult;
import com.ht.healthindex.service.*;
import com.ht.healthindex.service.model.DeviceInfoModel;
import com.ht.healthindex.service.model.DeviceTypeHIModel;
import com.ht.healthindex.service.model.StationHIModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Slf4j
public class TestController {
    @Autowired
    private ParamWeightConfigService paramWeightConfigService;
    @Autowired
    private SkylightService skylightService;
    @Autowired
    private ManualAdjustService manualAdjustService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private HealthIndexByTypeDOMapper healthIndexByTypeDOMapper;
    @Autowired
    private ComputeService computeService;
    @Autowired
    private DeviceTypeHIDOMapper deviceTypeHIDOMapper;

    @GetMapping("/index")
    public String index(){
//        log.info("test log info------------------");
        return "index";
    }

    @GetMapping("/getWeightConfig/{id}")
    @ResponseBody
    public CommonResult listWeightConfig(@PathVariable("id") Integer id){
        ParamWeightConfigDO paramWeightConfigDO = null;
        try {
            paramWeightConfigDO =
                    paramWeightConfigService.getParamWeightConfigById(id);
        } catch (BusinessException e) {
            e.printStackTrace();
        }

        return CommonResult.create(paramWeightConfigDO);
    }

    @GetMapping("/listSkylightRecord/{stationId}")
    @ResponseBody
    public CommonResult listSkylightRecord(@PathVariable("stationId") Integer stationId){
        List<SkylightRecordDO> skylightRecordDOList = null;

        try {
            skylightRecordDOList = skylightService.listByStationId(stationId);
        } catch (BusinessException e) {
            e.printStackTrace();
        }

        return CommonResult.create(skylightRecordDOList);
    }

    @GetMapping("/listManualAdjustRecord/{deviceId}")
    @ResponseBody
    public CommonResult listManualAdjustRecord(@PathVariable("deviceId") Integer deviceId){
        List<ManualAdjustRecordDO> adjustRecordDOList = null;

//        入参校验
        if(deviceId == null || !(deviceId > 0)){
            log.info("-----设备id{}不合法-----",deviceId);
            deviceId = 6;
        }

        //获取当前月份的第一天和最后一天
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//1:本月第一天
        String beginDate = format.format(c.getTime());
        System.out.println("本月第一天:"+beginDate);

        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        String endDate = format.format(c.getTime());
        System.out.println("本月最后一天:"+endDate);

        adjustRecordDOList = manualAdjustService.listByDeviceIdAndDate(deviceId,beginDate,endDate);
        return CommonResult.create(adjustRecordDOList);
    }

    @GetMapping("/listDeviceModel")
    @ResponseBody
    public CommonResult listDeviceModel(){
        List<DeviceInfoModel> deviceInfoModelList = deviceService.listDeviceInfoModel();
        return CommonResult.create(deviceInfoModelList);
    }

    @GetMapping("/listHealthIndexCondition")
    @ResponseBody
    public CommonResult listHealthIndexByCondition(){
        HealthIndexByTypeDO healthIndexByTypeDO = new HealthIndexByTypeDO();
        healthIndexByTypeDO.setStationId(1);
        healthIndexByTypeDO.setDeviceType("道岔");

        Date date = null;
        try {
            String dateStr = "2020-05-28";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        healthIndexByTypeDO.setCreateDate(date);
        List<HealthIndexByTypeDO> healthIndexList = healthIndexByTypeDOMapper.
                listHealthIndexByCondition(healthIndexByTypeDO);

        return CommonResult.create(healthIndexList);
    }

    @GetMapping("/listHealthIndexLatest")
    @ResponseBody
    public CommonResult listHealthIndexLatest(){
        List<HealthIndexByTypeDO> healthIndexList = healthIndexByTypeDOMapper.listHealthIndexLatest();
        return CommonResult.create(healthIndexList);
    }

    @GetMapping("/listLatestByCondition")
    @ResponseBody
    public CommonResult listHealthIndexLatestByCondition(){
        HealthIndexByTypeDO healthIndexByTypeDO = new HealthIndexByTypeDO();
/*        healthIndexByTypeDO.setStationId(1);
        healthIndexByTypeDO.setDeviceType("电源屏");*/
        healthIndexByTypeDO.setDeviceId(25);

        Date date = null;
        try {
            String dateStr = "2020-05-28";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        healthIndexByTypeDO.setCreateDate(date);

        List<HealthIndexByTypeDO> healthIndexList = healthIndexByTypeDOMapper.
                listHealthIndexLatestByCondition(healthIndexByTypeDO);

        return CommonResult.create(healthIndexList);
    }

    @GetMapping("/listHealthIndexByDate")
    @ResponseBody
    public CommonResult listHealthIndexByDate(){
        Date date = null;
        try {
            String dateStr = "2020-05-28";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(dateStr);
            log.info("-----传入的时间为: {}------",date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<HealthIndexByTypeDO> healthList = healthIndexByTypeDOMapper.listHealthIndexByDate(date);
         return CommonResult.create(healthList);

    }

    @GetMapping("/latest30days")
    @ResponseBody
    public CommonResult listHealthIndexLatest30Days(@RequestParam("deviceId") Integer deviceId,
                                                    @RequestParam("deviceName") String deviceName){
        HealthIndexByTypeDO healthIndexByTypeDO = new HealthIndexByTypeDO();
        healthIndexByTypeDO.setDeviceId(deviceId);
        healthIndexByTypeDO.setCreateDate(new Date());

        //指定最近30天内的数据都取出来了，但是没有办法实现去重，因为根据日期去重还是很麻烦的
        //其实并不适合在数据库底层做过于复杂的操作，这样并不方便进行功能迭代
        List<HealthIndexByTypeDO> healthIndexList = healthIndexByTypeDOMapper.
                listHealthIndexLatest30Days(healthIndexByTypeDO);
        //
        Map<String,HealthIndexByTypeDO> healthIndexMap = new LinkedHashMap<String,HealthIndexByTypeDO>();

        for(HealthIndexByTypeDO healthIndexDO:healthIndexList){
            System.out.println(healthIndexDO.getCreateDate());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = sdf.format(healthIndexDO.getCreateDate());
            Date date = null;
            try {
                date = sdf.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            healthIndexDO.setCreateDate(date);

            System.out.println(dateStr);
            healthIndexMap.put(dateStr,healthIndexDO);
        }

        List<HealthIndexByTypeDO> newList = new ArrayList<>(healthIndexMap.values());
        return CommonResult.create(newList);
    }

    @GetMapping("/trend30Days")
    public String showTrend(@RequestParam("deviceId") Integer deviceId,
                            @RequestParam("deviceName") String deviceName){
        return "trend30Days";
    }

    @GetMapping("/listDeviceTypeHI")
    @ResponseBody
    public CommonResult listDeviceTypeHI(){
        List<DeviceTypeHIModel> deviceTypeHIModelList = null;
        try {
            deviceTypeHIModelList = computeService.listDeviceTypeHI();
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        return CommonResult.create(deviceTypeHIModelList);
    }

    @GetMapping("/listDeviceTypeHILatestByCondition")
    @ResponseBody
    public CommonResult listDeviceTypeHILatestByCondition(){
        DeviceTypeHIDO deviceTypeHIDO = new DeviceTypeHIDO();
        deviceTypeHIDO.setStationName("松原站");
        List<DeviceTypeHIDO> healthIndexList = deviceTypeHIDOMapper.listDeviceTypeHILatestByCondition(deviceTypeHIDO);

        return CommonResult.create(healthIndexList);
    }

    @GetMapping("/listStationHI")
    @ResponseBody
    public CommonResult listStationHI(){
        List<StationHIModel> stationHIModelList = null;
        try {
            stationHIModelList = computeService.listStationHI();
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        return CommonResult.create(stationHIModelList);
    }

}
