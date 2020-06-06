package com.ht.healthindex.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.healthindex.controller.vo.HealthIndexBySystemVO;
import com.ht.healthindex.controller.vo.StationHIVO;
import com.ht.healthindex.dao.*;
import com.ht.healthindex.dataobject.*;
import com.ht.healthindex.error.BusinessException;
import com.ht.healthindex.error.EmBusinessError;
import com.ht.healthindex.response.CommonResult;
import com.ht.healthindex.service.ParamWeightConfigService;
import com.ht.healthindex.service.model.HealthStatusByTypeModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Slf4j
public class MainController {
    @Autowired
    private HealthIndexByTypeDOMapper healthIndexByTypeDOMapper;
    @Autowired
    private StationHIDOMapper stationHIDOMapper;
    @Autowired
    private ParamWeightConfigService paramWeightConfigService;
    @Autowired
    private ManualAdjustRecordDOMapper manualAdjustRecordDOMapper;
    @Autowired
    private DeviceInfoDOMapper deviceInfoDOMapper;
    @Autowired
    private StationInfoDOMapper stationInfoDOMapper;

    @GetMapping("/listHealthIndexByDevice")
    public String listHealthIndexByDevice(){
        return "healthIndexByDevice";
    }

    /*
    *   车站健康度列表页面入口
    * */
    @GetMapping("/listStationHealthIndex")
    public String listStationHealthIndex(){
        return "stationHealthIndex";
    }

    /*
    *   查询设备健康度列表分页数据接口
    * */
    @RequestMapping("/ajaxHealthIndex")
    @ResponseBody
    public CommonResult ajaxHealthIndexByDevice(HealthIndexByTypeDO healthIndexByTypeDO,Integer page,Integer number){
//        入参校验
        if(null == page || !( page > 0)){
            page = 0;
        }
        if(null == number || !(number > 0 )){
            number = 30;
        }
        if(null == healthIndexByTypeDO){
            healthIndexByTypeDO = new HealthIndexByTypeDO();
        }

        List<HealthIndexByTypeDO> healthIndexList = new ArrayList<>();

        PageHelper.startPage(page,number).setOrderBy("id desc");
//        PageInfo<HealthIndexByTypeDO> healthIndexDOPageInfo = new PageInfo<>(healthIndexByTypeDOMapper.listHealthIndex());
        PageInfo<HealthIndexByTypeDO> healthIndexDOPageInfo = new PageInfo<>(healthIndexByTypeDOMapper.
                listHealthIndexLatestByCondition(healthIndexByTypeDO));

        return CommonResult.create(healthIndexDOPageInfo);
    }

    /*
    *   查询设备健康状态列表不分页数据接口
    * */
    @RequestMapping("/ajaxHealthIndexList")
    @ResponseBody
    public CommonResult ajaxHealthIndexList(HealthIndexByTypeDO healthIndexByTypeDO){
        if(null == healthIndexByTypeDO){
            healthIndexByTypeDO = new HealthIndexByTypeDO();
        }
        if(null == healthIndexByTypeDO.getStationName() || "".equals(healthIndexByTypeDO.getStationName())){
            log.info("--------车站名不能为空-------------");
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"车站名不能为空");
        }

        List<HealthIndexByTypeDO> healthIndexList = new ArrayList<>();

        healthIndexList = healthIndexByTypeDOMapper.listHealthIndexLatestByCondition(healthIndexByTypeDO);
        List<HealthStatusByTypeModel> healthStatusTypeList = new ArrayList<>();
        Map<String,HealthStatusByTypeModel> healthStatusMap = new HashMap<String,HealthStatusByTypeModel>();

        for( HealthIndexByTypeDO healthIndex:healthIndexList){
            //如果healthStatusTypeList还没有该类型设备的对象，增加该类型设备对象
            if( !(healthStatusMap.containsKey(healthIndex.getDeviceType()))){
                log.info("新增设备类型:{}",healthIndex.getDeviceType());
                HealthStatusByTypeModel healthStatusModel = new HealthStatusByTypeModel();
                healthStatusModel.setDeviceType(healthIndex.getDeviceType());
                healthStatusModel.setStationName(healthIndex.getStationName());
                healthStatusModel.setStationId(healthIndex.getStationId());
                healthStatusModel.setAbnormalCount(0);
                healthStatusModel.setErrorCount(0);
                healthStatusModel.setHealthyCount(0);
                healthStatusModel.setSubhealthyCount(0);
                healthStatusModel.setMorbidCount(0);

                if(healthIndex.getHealthIndex().compareTo(new BigDecimal("85"))==1
                        || healthIndex.getHealthIndex().compareTo(new BigDecimal("85"))==0){
                    healthStatusModel.setHealthyCount(healthStatusModel.getHealthyCount()+1);
                }else if(healthIndex.getHealthIndex().compareTo(new BigDecimal("70"))==1
                        || healthIndex.getHealthIndex().compareTo(new BigDecimal("70"))==0){
                    healthStatusModel.setSubhealthyCount(healthStatusModel.getSubhealthyCount()+1);
                }else if(healthIndex.getHealthIndex().compareTo(new BigDecimal("60"))==1
                        || healthIndex.getHealthIndex().compareTo(new BigDecimal("60"))==0){
                    healthStatusModel.setAbnormalCount(healthStatusModel.getAbnormalCount()+1);
                }else if(healthIndex.getHealthIndex().compareTo(new BigDecimal("40"))==1
                        || healthIndex.getHealthIndex().compareTo(new BigDecimal("40"))==0){
                    healthStatusModel.setMorbidCount(healthStatusModel.getMorbidCount()+1);
                }else{
                    healthStatusModel.setErrorCount(healthStatusModel.getErrorCount()+1);
                }
                healthStatusMap.put(healthStatusModel.getDeviceType(),healthStatusModel);
            }else{
                //如果已经存在该类型设备的对象
                HealthStatusByTypeModel healthStatusModel = healthStatusMap.get(healthIndex.getDeviceType());
                log.info("已存在设备类型:{}",healthIndex.getDeviceType());
                if(healthIndex.getHealthIndex().compareTo(new BigDecimal("85"))==1
                        || healthIndex.getHealthIndex().compareTo(new BigDecimal("85"))==0){
                    healthStatusModel.setHealthyCount(healthStatusModel.getHealthyCount()+1);
                }else if(healthIndex.getHealthIndex().compareTo(new BigDecimal("70"))==1
                        || healthIndex.getHealthIndex().compareTo(new BigDecimal("70"))==0){
                    healthStatusModel.setSubhealthyCount(healthStatusModel.getSubhealthyCount()+1);
                }else if(healthIndex.getHealthIndex().compareTo(new BigDecimal("60"))==1
                        || healthIndex.getHealthIndex().compareTo(new BigDecimal("60"))==0){
                    healthStatusModel.setAbnormalCount(healthStatusModel.getAbnormalCount()+1);
                }else if(healthIndex.getHealthIndex().compareTo(new BigDecimal("40"))==1
                        || healthIndex.getHealthIndex().compareTo(new BigDecimal("40"))==0){
                    healthStatusModel.setMorbidCount(healthStatusModel.getMorbidCount()+1);
                }else{
                    healthStatusModel.setErrorCount(healthStatusModel.getErrorCount()+1);
                }
            }

        }

        //将map中的value存放入ArrayList 送回前端
        for(Iterator itr = healthStatusMap.keySet().iterator();itr.hasNext();){
            healthStatusTypeList.add(healthStatusMap.get(itr.next().toString()));
        }

        return CommonResult.create(healthStatusTypeList);
    }

    /*
    *   查询车站健康度列表分页数据接口
    * */
    @GetMapping("/ajaxStationHealthIndex")
    @ResponseBody
    public CommonResult ajaxStationHealthIndex(StationHIDO stationHIDO, Integer page, Integer number){
        //        入参校验
        if(null == page || !( page > 0)){
            page = 0;
        }
        if(null == number || !(number > 0 )){
            number = 30;
        }
        if( null == stationHIDO){
            stationHIDO = new StationHIDO();
        }

        List<StationHIDO> stationHIDOList = new ArrayList<StationHIDO>();

        PageHelper.startPage(page,number).setOrderBy("id desc");
        PageInfo<StationHIDO> stationHIDOPageInfo = new PageInfo<>(
                stationHIDOMapper.listStationHILatestByCondition(stationHIDO));

        return CommonResult.create(stationHIDOPageInfo);
    }

    /*
    *   查询车站健康度列表数据接口(不分页)
    * */
    @GetMapping("/ajaxStationHealthIndexList")
    @ResponseBody
    public CommonResult ajaxStationHealthIndexLatestList(StationHIDO stationHIDO){
        if(null == stationHIDO){
            stationHIDO = new StationHIDO();
        }
        if(null == stationHIDO.getLineName() || "".equals(stationHIDO.getLineName())){
            log.info("--------线路名不能为空-------------");
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"车站名不能为空");
        }

        List<StationHIDO> stationHIDOList = new ArrayList<StationHIDO>();

        stationHIDOList = stationHIDOMapper.listStationHILatestByCondition(stationHIDO);
        return CommonResult.create(stationHIDOList);
    }

    /*
    *   展示车站最近30天健康度趋势图页面入口
    * */
    @GetMapping("/trend30DaysStation")
    public String showTrendStation(@RequestParam("stationId") Integer stationId,
                            @RequestParam("stationName") String stationName){
        return "trend30DaysStation";
    }

    /*
    *   展示车站最近30天健康度趋势数据接口
    * */
    @GetMapping("/latest30daysStation")
    @ResponseBody
    public CommonResult listHealthIndexLatest30Days(@RequestParam("stationId") Integer stationId,
                                                    @RequestParam("stationName") String stationName){

        StationHIDO stationHIDO = new StationHIDO();
        stationHIDO.setStationId(stationId);
        stationHIDO.setCreateDate(new Date());

        //指定最近30天内的数据都取出来了，但是没有办法实现去重，因为根据日期去重还是很麻烦的
        //其实并不适合在数据库底层做过于复杂的操作，这样并不方便进行功能迭代
        List<StationHIDO> healthIndexList = stationHIDOMapper.listStationHILatest30days(stationHIDO);
        //
        Map<String,StationHIDO> healthIndexMap = new LinkedHashMap<String,StationHIDO>();

        for(StationHIDO healthIndexDO:healthIndexList){
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

        List<StationHIDO> newList = new ArrayList<>(healthIndexMap.values());
        return CommonResult.create(newList);
    }

    /*
*   权重配置页面入口
* */
    @GetMapping("/weightConfig")
    public String weightConfig(){
        return "weightConfig";
    }

    /*
    *   权重配置数据获取接口,获取最新的权重配置规则
    *
    * */
    @GetMapping("/ajaxWeightConfig")
    @ResponseBody
    public CommonResult ajaxWeightConfig(){
        ParamWeightConfigDO paramWeightConfigDO = paramWeightConfigService.getLatestConfig();
        return CommonResult.create(paramWeightConfigDO);
    }

    /*
    *   配置修改数据保存接口
    * */
    @PostMapping("/updateWeightConfig")
    @ResponseBody
    public CommonResult updateWeightConfig(ParamWeightConfigDO paramWeightConfigDO){
//        service层已经做了参数检验，本层在确认的情况下，可以不做
/*        if(null == paramWeightConfigDO || paramWeightConfigDO.getId() == null) {
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"必填参数不能为空");

        }*/
        int count = 0;
        try {
          count  = paramWeightConfigService.updateWeightConfig(paramWeightConfigDO);
        } catch (BusinessException e) {
            e.printStackTrace();
            log.info(e.getErrMsg());
            return CommonResult.create("fail",e);
        }

        return CommonResult.create(count);
    }


    /*
    *   查询设备健康状态列表(按来源系统)不分页数据接口(mock)
    * */
    @RequestMapping("/ajaxHealthIndexBySystemList")
    @ResponseBody
    public CommonResult ajaxHealthIndexBySystemList(HealthIndexByTypeDO healthIndexByTypeDO){
        if(null == healthIndexByTypeDO){
            healthIndexByTypeDO = new HealthIndexByTypeDO();
        }
        if(null == healthIndexByTypeDO.getStationName() || "".equals(healthIndexByTypeDO.getStationName())){
            log.info("--------车站名不能为空-------------");
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"车站名不能为空");
        }

        List<HealthIndexBySystemVO> healthIndexList = new ArrayList<>();

//        数据mock
        HealthIndexBySystemVO vo1 = new HealthIndexBySystemVO();
        vo1.setId(1);
        vo1.setStationId(1);
        vo1.setStationName("长北一场");
        vo1.setInterfaceName("自采集");
        vo1.setHealthIndex(new BigDecimal("86.32"));
        vo1.setHealthyCount(123);
        vo1.setSubhealthyCount(17);
        vo1.setAbnormalCount(21);
        vo1.setMorbidCount(6);
        vo1.setErrorCount(3);
        vo1.setCreateDate(new Date());

        healthIndexList.add(vo1);

        HealthIndexBySystemVO vo2 = new HealthIndexBySystemVO();
        vo2.setId(2);
        vo2.setStationId(1);
        vo2.setStationName("长北一场");
        vo2.setInterfaceName("联锁");
        vo2.setHealthIndex(new BigDecimal("76.32"));
        vo2.setHealthyCount(103);
        vo2.setSubhealthyCount(37);
        vo2.setAbnormalCount(24);
        vo2.setMorbidCount(5);
        vo2.setErrorCount(1);
        vo2.setCreateDate(new Date());

        healthIndexList.add(vo2);

        HealthIndexBySystemVO vo3 = new HealthIndexBySystemVO();
        vo3.setId(3);
        vo3.setStationId(1);
        vo3.setStationName("长北一场");
        vo3.setInterfaceName("CTC/TDCS");
        vo3.setHealthIndex(new BigDecimal("66.32"));
        vo3.setHealthyCount(83);
        vo3.setSubhealthyCount(57);
        vo3.setAbnormalCount(14);
        vo3.setMorbidCount(15);
        vo3.setErrorCount(1);
        vo3.setCreateDate(new Date());

        healthIndexList.add(vo3);

        HealthIndexBySystemVO vo4 = new HealthIndexBySystemVO();
        vo4.setId(4);
        vo4.setStationId(1);
        vo4.setStationName("长北一场");
        vo4.setInterfaceName("列控");
        vo4.setHealthIndex(new BigDecimal("72.32"));
        vo4.setHealthyCount(93);
        vo4.setSubhealthyCount(47);
        vo4.setAbnormalCount(14);
        vo4.setMorbidCount(11);
        vo4.setErrorCount(5);
        vo4.setCreateDate(new Date());

        healthIndexList.add(vo4);

        HealthIndexBySystemVO vo5 = new HealthIndexBySystemVO();
        vo5.setId(5);
        vo5.setStationId(1);
        vo5.setStationName("长北一场");
        vo5.setInterfaceName("电源屏");
        vo5.setHealthIndex(new BigDecimal("52.32"));
        vo5.setHealthyCount(43);
        vo5.setSubhealthyCount(47);
        vo5.setAbnormalCount(34);
        vo5.setMorbidCount(31);
        vo5.setErrorCount(15);
        vo5.setCreateDate(new Date());

        healthIndexList.add(vo5);

        HealthIndexBySystemVO vo6 = new HealthIndexBySystemVO();
        vo6.setId(6);
        vo6.setStationId(1);
        vo6.setStationName("长北一场");
        vo6.setInterfaceName("zpw2000");
        vo6.setHealthIndex(new BigDecimal("62.32"));
        vo6.setHealthyCount(83);
        vo6.setSubhealthyCount(57);
        vo6.setAbnormalCount(24);
        vo6.setMorbidCount(1);
        vo6.setErrorCount(5);
        vo6.setCreateDate(new Date());

        healthIndexList.add(vo6);

        HealthIndexBySystemVO vo7 = new HealthIndexBySystemVO();
        vo7.setId(7);
        vo7.setStationId(1);
        vo7.setStationName("长北一场");
        vo7.setInterfaceName("道岔缺口");
        vo7.setHealthIndex(new BigDecimal("72.32"));
        vo7.setHealthyCount(93);
        vo7.setSubhealthyCount(47);
        vo7.setAbnormalCount(14);
        vo7.setMorbidCount(11);
        vo7.setErrorCount(5);
        vo7.setCreateDate(new Date());

        healthIndexList.add(vo7);

        HealthIndexBySystemVO vo8 = new HealthIndexBySystemVO();
        vo8.setId(8);
        vo8.setStationId(1);
        vo8.setStationName("长北一场");
        vo8.setInterfaceName("中心CTC");
        vo8.setHealthIndex(new BigDecimal("92.32"));
        vo8.setHealthyCount(133);
        vo8.setSubhealthyCount(43);
        vo8.setAbnormalCount(4);
        vo8.setMorbidCount(1);
        vo8.setErrorCount(0);
        vo8.setCreateDate(new Date());

        healthIndexList.add(vo8);

        return CommonResult.create(healthIndexList);
    }

    /*
    *   人工辅正页面入口
    * */
    @GetMapping("/manualAdjust")
    public String showManualAdjust(){
        return "manualAdjust";
    }

    /*
    *   人工辅正页面数据查询接口
    * */
    @GetMapping("/ajaxManualAdjust")
    @ResponseBody
    public CommonResult ajaxManualAdjust(@RequestParam("deviceId") Integer deviceId){
        if(null == deviceId){
            log.info("设备Id不能为空");
            return CommonResult.create("fail",null);
        }

        ManualAdjustRecordDO manualAdjustRecordDO = manualAdjustRecordDOMapper.selectByDeviceId(deviceId);
        if(null == manualAdjustRecordDO){
            manualAdjustRecordDO = new ManualAdjustRecordDO();
        }

        manualAdjustRecordDO.setDeviceId(deviceId);

        JSONObject jo = (JSONObject) JSONObject.toJSON(manualAdjustRecordDO);
//        String dateStr = manualAdjustRecordDO.getCreateDate().toString();
/*        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String dateStr = sdf.format(manualAdjustRecordDO.getCreateDate());

        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            log.info("日期转换错误");
        }
        manualAdjustRecordDO.setCreateDate(date);*/

        DeviceInfoDO deviceInfo = deviceInfoDOMapper.selectByPrimaryKey(deviceId);
        jo.getInnerMap().put("deviceName",deviceInfo.getDeviceName());
        StationInfoDO stationInfo = stationInfoDOMapper.selectByPrimaryKey(deviceInfo.getStationId());
        jo.getInnerMap().put("stationName",stationInfo.getStationName());

        return CommonResult.create(jo);
    }

    @PostMapping("/addManualAdjust")
    @ResponseBody
    public CommonResult addManualAdjust(ManualAdjustRecordDO manualAdjustRecordDO){
        if(manualAdjustRecordDO == null){
            log.info("参数不能为空");
        }
        manualAdjustRecordDO.setCreateDate(new Date());
        int count = manualAdjustRecordDOMapper.insertSelective(manualAdjustRecordDO);
        if(count != 1){
            return CommonResult.create("fail",null);
        }
        return CommonResult.create(count);
    }

}
