package com.ht.healthindex.service.impl;

import com.ht.healthindex.dao.*;
import com.ht.healthindex.dataobject.*;
import com.ht.healthindex.error.BusinessException;
import com.ht.healthindex.error.EmBusinessError;
import com.ht.healthindex.service.ComputeService;
import com.ht.healthindex.service.HistoryAlarmService;
import com.ht.healthindex.service.ParamWeightConfigService;
import com.ht.healthindex.service.SkylightService;
import com.ht.healthindex.service.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.BuilderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class ComputeServiceImpl implements ComputeService{
    @Autowired
    private ParamWeightConfigService paramWeightConfigService;
    @Autowired
    private SkylightService skylightService;
    @Autowired
    private ManualAdjustRecordDOMapper manualAdjustRecordDOMapper;
    @Autowired
    private DeviceInfoDOMapper deviceInfoDOMapper;
    @Autowired
    private HistoryAlarmService historyAlarmService;
    @Autowired
    private StationInfoDOMapper stationInfoDOMapper;
    @Autowired
    private HealthIndexByTypeDOMapper healthIndexByTypeDOMapper;
    @Autowired
    private DeviceTypeHIDOMapper deviceTypeHIDOMapper;

    @Override
    public DeviceHealthIndexModel getAlarmDeviceHealthIndex(
            String fDeviceName,Integer fsationid,List<HistoryAlarmDO> alarmDOList) {

        ParamWeightConfigDO paramWeightConfigDO = null;

        try {
            paramWeightConfigDO = paramWeightConfigService.getParamWeightConfigById(1);
        } catch (BusinessException e) {
            e.printStackTrace();
        }

        BigDecimal alarmDeviceHealthIndex = new BigDecimal("0");
        int levelOneCount = 0;
        int levelTwoCount = 0;
        int levelThreeCount = 0;
        int forcastAlarmCount = 0;

        for(HistoryAlarmDO alarmDO:alarmDOList){
            if(alarmDO.getFlevel().equals(1)){
                System.out.println("一级报警");
                levelOneCount++;
                alarmDeviceHealthIndex = alarmDeviceHealthIndex.add(
                        paramWeightConfigDO.getLevel1AlarmWeight());
            }else if(alarmDO.getFlevel().equals(2)){
                System.out.println("二级报警");
                levelTwoCount++;
                alarmDeviceHealthIndex = alarmDeviceHealthIndex.add(
                        paramWeightConfigDO.getLevel2AlarmWeight());
            }else if(alarmDO.getFlevel().equals(3)){
                System.out.println("三级报警");
                levelThreeCount++;
                alarmDeviceHealthIndex = alarmDeviceHealthIndex.add(
                        paramWeightConfigDO.getLevel3AlarmWeight());
            }else if(alarmDO.getFlevel().equals(0)){
                forcastAlarmCount++;
                System.out.println("预警");
                alarmDeviceHealthIndex = alarmDeviceHealthIndex.add(
                        paramWeightConfigDO.getForecastWeight());
            }
        }

        System.out.println("一级报警数量："+levelOneCount);
        System.out.println("二级报警数量："+levelTwoCount);
        System.out.println("三级报警数量："+levelThreeCount);
        System.out.println("预警数量："+ forcastAlarmCount);
        System.out.println("设备名称："+fDeviceName+"------健康指数："+alarmDeviceHealthIndex);

        DeviceHealthIndexModel deviceHealthIndexModel = new DeviceHealthIndexModel();
        deviceHealthIndexModel.setFDeviceName(fDeviceName);
        deviceHealthIndexModel.setAlarmDeviceHealthIndex(alarmDeviceHealthIndex);

        return deviceHealthIndexModel;
    }

    @Override
    public DeviceHealthIndexModel getSkylightHealthIndex(Integer stationId, String deviceName) {
        //需要先获取车站对应的天窗修记录
        List<SkylightRecordDO> skylightRecordDOList = null;
        DeviceHealthIndexModel healthIndexModel = new DeviceHealthIndexModel();
        healthIndexModel.setFDeviceName(deviceName);
        healthIndexModel.setAlarmDeviceHealthIndex(new BigDecimal("0"));
        try {
             skylightRecordDOList = skylightService.listByStationId(stationId);
        } catch (BusinessException e) {
            e.printStackTrace();
        }

        if(skylightRecordDOList.size() == 0 ){
            log.info("-------车站{}没有对应天窗修记录-------");
        }

//        获取指标权重配置
        ParamWeightConfigDO paramWeightConfigDO = null;
        try {
            paramWeightConfigDO = paramWeightConfigService.getParamWeightConfigById(1);
        } catch (BusinessException e) {
            e.printStackTrace();
        }

        if(null == paramWeightConfigDO){
            log.info("------没有可用的指标权重配置规则，将使用默认的配置规则------");
            paramWeightConfigDO = new ParamWeightConfigDO();
            paramWeightConfigDO.setSkylightWeight(new BigDecimal("0.30"));
        }

        for(SkylightRecordDO skylightRecordDO:skylightRecordDOList){
            System.out.println(skylightRecordDO.getDeviceCollection());
            List deviceList = this.parseDeviceCollection(skylightRecordDO.getDeviceCollection());
            if(deviceList.contains(deviceName)){
                log.info("车站{} 设备{} 存在天窗修记录，时间为{}",stationId,deviceName,skylightRecordDO.getStartTime());
                healthIndexModel.setAlarmDeviceHealthIndex(
                        healthIndexModel.getAlarmDeviceHealthIndex().add(
                                new BigDecimal(
                                        paramWeightConfigDO.getSkylightWeight().toString()))
                        );
                log.info("设备天窗修部分hi增加{}",paramWeightConfigDO.getSkylightWeight());
            }
        }

        return healthIndexModel;
    }

    @Override
    public DeviceHealthIndexModel getManualAdjustHealthIndex(Integer deviceId) {
//        入参校验
        DeviceHealthIndexModel healthIndexModel = new DeviceHealthIndexModel();
        healthIndexModel.setDeviceId(deviceId);
        healthIndexModel.setAlarmDeviceHealthIndex(new BigDecimal("0"));

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

        List<ManualAdjustRecordDO> adjustRecordDOList = null;

        adjustRecordDOList = manualAdjustRecordDOMapper.selectByDeviceIdAndDate(
                deviceId,beginDate,endDate);

        if(adjustRecordDOList.size() > 1){
//            throw new BusinessException(EmBusinessError.UNKOWN_ERROR,"本月内人工辅正记录多于一条");
            log.info("本月内人工辅正记录多于一条");
        }else if(adjustRecordDOList.size() == 1){
            log.info("设备id:{}本月具有人工辅正记录,辅正值为{}",deviceId,adjustRecordDOList.get(0).getValue());
            healthIndexModel.setAlarmDeviceHealthIndex(
                    healthIndexModel.getAlarmDeviceHealthIndex().add(
                            adjustRecordDOList.get(0).getValue()));
        }
        return healthIndexModel;
    }

    @Override
    public DeviceHealthIndexModel getLifeHealthIndex(Integer deviceId) throws BusinessException{
//        入参校验
        // 获取设备信息
        DeviceInfoDO deviceInfoDO = null;
        DeviceHealthIndexModel healthIndexModel = new DeviceHealthIndexModel();
        healthIndexModel.setDeviceId(deviceId);
        healthIndexModel.setAlarmDeviceHealthIndex(new BigDecimal("0"));

        deviceInfoDO = deviceInfoDOMapper.selectByPrimaryKey(deviceId);
        if(null == deviceInfoDO){
            log.info("id为{}的设备不存在",deviceId);
            throw new BusinessException(EmBusinessError.DEVICE_NOT_EXIST);
        }

//        根据上道时间和使用寿命计算健康度
        System.out.println(deviceInfoDO.getProduceDate());
        System.out.println(deviceInfoDO.getInuseDate());

//       计算设备已经使用的天数
        long inuseTimestamp = deviceInfoDO.getInuseDate().getTime();
        int usedDays = (int)(( new Date().getTime() - inuseTimestamp )/(24*60*60*1000));
        log.info("id为{}的设备已经使用了{}天",deviceId,usedDays);
        healthIndexModel.setFDeviceName(deviceInfoDO.getDeviceName());

        log.info("id为{}的设备的使用寿命为{}天",deviceId,deviceInfoDO.getPerspectLife());
        double paramLife = 1.00 - ((double)usedDays/deviceInfoDO.getPerspectLife());
        String paramLifeStr = ""+paramLife;

        //        获取指标权重配置
        ParamWeightConfigDO paramWeightConfigDO = null;
        try {
            paramWeightConfigDO = paramWeightConfigService.getParamWeightConfigById(1);
        } catch (BusinessException e) {
            e.printStackTrace();
        }

        if(null == paramWeightConfigDO){
            log.info("------没有可用的指标权重配置规则，将使用默认的配置规则------");
            paramWeightConfigDO = new ParamWeightConfigDO();
            paramWeightConfigDO.setLifeWeight(new BigDecimal("20"));
        }

        healthIndexModel.setAlarmDeviceHealthIndex(
                healthIndexModel.getAlarmDeviceHealthIndex().add(
                        paramWeightConfigDO.getLifeWeight().multiply(new BigDecimal(paramLifeStr))));
        return healthIndexModel;
    }

    @Override
    public HealthIndexByTypeModel getHealthIndexByType(Integer deviceId) {
        return null;
    }

    /*
    *   根据设备类型计算所有设备的健康指数
    *   @return 所有设备的健康度指数列表
    * */
    @Override
    public List<HealthIndexByTypeModel> listHealthIndexByType() throws BusinessException{
        List<DeviceInfoModel> deviceList = deviceInfoDOMapper.listDeviceInfoModel();

        List<HealthIndexByTypeModel> healthIndexList = new ArrayList<>();

//        获取指标权重配置规则
        ParamWeightConfigDO paramWeightConfigDO = null;

        try {
            paramWeightConfigDO = paramWeightConfigService.getParamWeightConfigById(1);
        } catch (BusinessException e) {
            e.printStackTrace();
        }

        if(paramWeightConfigDO == null){
            log.info("-----不存在符合要求的指标权重配置规则，使用默认的规则-----");
            paramWeightConfigDO = new ParamWeightConfigDO();
            paramWeightConfigDO.setLevel1AlarmWeight(new BigDecimal("0.15"));
            paramWeightConfigDO.setLevel2AlarmWeight(new BigDecimal("0.10"));
            paramWeightConfigDO.setLevel3AlarmWeight(new BigDecimal("0.05"));
            paramWeightConfigDO.setForecastWeight(new BigDecimal("0.01"));
            paramWeightConfigDO.setSkylightWeight(new BigDecimal("0.30"));
            paramWeightConfigDO.setLifeWeight(new BigDecimal("20"));
        }

        for(DeviceInfoModel deviceInfoModel:deviceList){
            BigDecimal healthIndex = new BigDecimal("100");
            HealthIndexByTypeModel healthIndexByTypeModel = new HealthIndexByTypeModel();

            log.info("==============================================================");

            //计算设备报警部分健康度
            BigDecimal alarmHealthIndex =
                    this.getAlarmHealthIndex(deviceInfoModel.getStationId(),deviceInfoModel.getDeviceName(),
                    paramWeightConfigDO);
            log.info("车站id:{} 设备id:{} 设备名称:{},其报警记录健康度为{}",deviceInfoModel.getStationId(),deviceInfoModel.getId(),
                    deviceInfoModel.getDeviceName(),alarmHealthIndex);
            //计算天窗修部分健康度
            BigDecimal skylightHealthIndex =
            this.getSkylightHealghIndex(deviceInfoModel.getStationId(),deviceInfoModel.getDeviceName(),
                    paramWeightConfigDO);
            log.info("车站id:{} 设备id:{} 设备名称:{},其天窗修健康度为{}",deviceInfoModel.getStationId(),deviceInfoModel.getId(),
                    deviceInfoModel.getDeviceName(),skylightHealthIndex);
            //计算人工辅正部分健康度
            BigDecimal manualHealthIndex = this.getManualHealthIndex(deviceInfoModel.getId());
            log.info("车站id:{} 设备id:{} 设备名称:{},其人工辅正记录健康度为{}",deviceInfoModel.getStationId(),deviceInfoModel.getId(),
                    deviceInfoModel.getDeviceName(),manualHealthIndex);
            //计算使用寿命部分健康度
            BigDecimal lifeHealthIndex = this.getLifeHealthIndex(deviceInfoModel.getId(),paramWeightConfigDO);
            log.info("车站id:{} 设备id:{} 设备名称:{},其使用寿命健康度为{}",deviceInfoModel.getStationId(),deviceInfoModel.getId(),
                    deviceInfoModel.getDeviceName(),lifeHealthIndex);

            healthIndex = healthIndex.subtract(alarmHealthIndex).add(skylightHealthIndex).add(manualHealthIndex).subtract(lifeHealthIndex);

            //       计算设备预期剩余寿命
            Integer lifeLeft = 0;
            long inuseTimestamp = deviceInfoModel.getInuseDate().getTime();
            int usedDays = (int)(( new Date().getTime() - inuseTimestamp )/(24*60*60*1000));
            log.info("id为{}的设备已经使用了{}天",deviceInfoModel,usedDays);

            log.info("id为{}的设备的使用寿命为{}天",deviceInfoModel.getId(),deviceInfoModel.getPerspectLife());
            lifeLeft = deviceInfoModel.getPerspectLife() - usedDays;

            healthIndexByTypeModel.setDeviceId(deviceInfoModel.getId());
            healthIndexByTypeModel.setDeviceName(deviceInfoModel.getDeviceName());
            healthIndexByTypeModel.setDeviceType(deviceInfoModel.getDeviceType());
            healthIndexByTypeModel.setStationId(deviceInfoModel.getStationId());
            healthIndexByTypeModel.setStationName(deviceInfoModel.getStationName());
            healthIndexByTypeModel.setHealthIndex(healthIndex);
            healthIndexByTypeModel.setLifeLeft(lifeLeft);
            log.info("车站id:{} 车站名称: {} 设备id:{} 设备名称:{},其总健康度为{}",deviceInfoModel.getStationId()
                    ,deviceInfoModel.getStationName(),deviceInfoModel.getId(),
                    deviceInfoModel.getDeviceName(),healthIndex);
            log.info("==============================================================");
            healthIndexList.add(healthIndexByTypeModel);
        }

        return healthIndexList;
    }

    @Override
    public List<DeviceTypeHIModel> listDeviceTypeHI() throws BusinessException {
        //获取最新的所有单个设备的健康度列表
        List<StationInfoDO> stationList = stationInfoDOMapper.listStationInfo();

        if(stationList.size() == 0){
            return new ArrayList<>();
        }

        List<DeviceTypeHIModel> allDeviceTypeHIList = new ArrayList<DeviceTypeHIModel>();

        for(StationInfoDO station:stationList){
//            根据车站id查找最新的该车站所有设备的健康度列表
            HealthIndexByTypeDO healthIndexByTypeDO = new HealthIndexByTypeDO();
            healthIndexByTypeDO.setStationId(station.getId());

            List<HealthIndexByTypeDO> healthIndexList = new ArrayList<>();

            healthIndexList = healthIndexByTypeDOMapper.listHealthIndexLatestByCondition(healthIndexByTypeDO);
            List<DeviceTypeHIModel> healthStatusTypeList = new ArrayList<>();
            Map<String,DeviceTypeHIModel> healthStatusMap = new HashMap<String,DeviceTypeHIModel>();

            for( HealthIndexByTypeDO healthIndex:healthIndexList){
                //如果healthStatusTypeList还没有该类型设备的对象，增加该类型设备对象
                if( !(healthStatusMap.containsKey(healthIndex.getDeviceType()))){
                    log.info("新增设备类型:{}",healthIndex.getDeviceType());
                    DeviceTypeHIModel healthStatusModel = new DeviceTypeHIModel();
                    healthStatusModel.setDeviceType(healthIndex.getDeviceType());
                    healthStatusModel.setStationName(healthIndex.getStationName());
                    healthStatusModel.setStationId(healthIndex.getStationId());
                    healthStatusModel.setAbnormalCount(0);
                    healthStatusModel.setErrorCount(0);
                    healthStatusModel.setHealthyCount(0);
                    healthStatusModel.setSubhealthyCount(0);
                    healthStatusModel.setMorbidCount(0);
                    healthStatusModel.setHealthIndex(new BigDecimal("0"));

                    //计算设备类型的健康度(最后还要处理)
                    healthStatusModel.setHealthIndex(healthStatusModel.getHealthIndex().
                            add(healthIndex.getHealthIndex()));

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
                    //如果已经存在该类型设备的对象，直接统计相关参数
                    DeviceTypeHIModel healthStatusModel = healthStatusMap.get(healthIndex.getDeviceType());
                    log.info("已存在设备类型:{}",healthIndex.getDeviceType());

                    healthStatusModel.setHealthIndex(healthStatusModel.getHealthIndex().
                            add(healthIndex.getHealthIndex()));
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

            for(Iterator itr = healthStatusMap.keySet().iterator();itr.hasNext();){
                DeviceTypeHIModel deviceTypeHIModel =  healthStatusMap.
                        get(itr.next().toString());
                float healthIndex = deviceTypeHIModel.getHealthIndex().floatValue();
                int count = deviceTypeHIModel.getHealthyCount()+deviceTypeHIModel.getSubhealthyCount()+
                        deviceTypeHIModel.getAbnormalCount()+deviceTypeHIModel.getMorbidCount()+
                        deviceTypeHIModel.getErrorCount();
                float newHealthIndex = healthIndex/count;
                String healthIndexStr =  String.format("%.2f",newHealthIndex);
                deviceTypeHIModel.setHealthIndex(new BigDecimal(healthIndexStr));

/*                deviceTypeHIModel.setHealthIndex(deviceTypeHIModel.getHealthIndex().divide(
                        new BigDecimal(deviceTypeHIModel.getHealthyCount()+deviceTypeHIModel.getSubhealthyCount()
                        +deviceTypeHIModel.getAbnormalCount()+deviceTypeHIModel.getMorbidCount()+
                        deviceTypeHIModel.getErrorCount())
                ));*/

//                healthStatusTypeList.add(healthStatusMap.get(itr.next().toString()));
                healthStatusTypeList.add(deviceTypeHIModel);
                allDeviceTypeHIList.add(deviceTypeHIModel);
            }

        }

        return allDeviceTypeHIList;
    }

    /*
    *   根据车站各设备类型健康度指数计算车站健康度指数
    * */
    @Override
    public List<StationHIModel> listStationHI() throws BusinessException {
        List<StationInfoDO>  stationList = stationInfoDOMapper.listStationInfo();
        List<StationHIModel> stationHIModelList = new ArrayList<StationHIModel>();
        if(null == stationList||stationList.size() == 0){
            return stationHIModelList;
        }

        for(StationInfoDO stationInfo:stationList){
            //查询设备类型 健康度记录表，获取车站对应的最新一日内的健康度
            DeviceTypeHIDO deviceTypeHIDO = new DeviceTypeHIDO();
            deviceTypeHIDO.setStationId(stationInfo.getId());
            List<DeviceTypeHIDO> deviceTypeHIDOList = deviceTypeHIDOMapper.
                    listDeviceTypeHILatestByCondition(deviceTypeHIDO);

            if(deviceTypeHIDOList.size() == 0){
                continue;
            }
            // 根据车站各设备类型健康度计算车站的健康度
            float healthIndex = 0.0f;
            for(DeviceTypeHIDO deviceTypeHI:deviceTypeHIDOList){
                healthIndex+= deviceTypeHI.getHealthIndex().floatValue();
            }
            healthIndex /= deviceTypeHIDOList.size();
            String healthIndexStr = String.format("%.2f",healthIndex);

            StationHIModel stationHIModel = new StationHIModel();
            stationHIModel.setStationId(stationInfo.getId());
            stationHIModel.setStationName(stationInfo.getStationName());
            stationHIModel.setHealthIndex(new BigDecimal(healthIndexStr));
            stationHIModel.setLineName(stationInfo.getLineName());
            stationHIModel.setWorkshopName(stationInfo.getWorkshopName());
            stationHIModel.setSectionName(stationInfo.getSectionName());
            stationHIModel.setCompanyName(stationInfo.getCompanyName());

            stationHIModelList.add(stationHIModel);
        }

        return stationHIModelList;
    }

    /*
    *   将设备集合字段解析为一个个设备的List
    *   解析过程中并没有验证 数据格式的合法性(由数据采集方保证数据格式的正确性)
    * */
    public List<String> parseDeviceCollection(String deviceCollection){
        if(StringUtils.isEmpty(deviceCollection)){
            return new ArrayList<String>();
        }

        List deviceList = new ArrayList<String>();

        String newStr = deviceCollection.substring(1,deviceCollection.length()-1);
        String[] arrDeviceCollection = newStr.split("]");
        for(int i=0;i<arrDeviceCollection.length;i++){
            String deviceStr = arrDeviceCollection[i].substring(arrDeviceCollection[i].indexOf("[")+1);
//            System.out.println(deviceStr);
            String[] deviceArr = deviceStr.split(",");
            for(int j=0;j<deviceArr.length;j++){
//                去除双引号，否则会存入 "2DG"这样的一个字符串
                deviceList.add(deviceArr[j].substring(1,deviceArr[j].length()-1));
            }
        }

//        System.out.println("------设备列表长度：-------"+deviceList.size());
        return deviceList;
    }



/*    public static void main(String[] args) {
        String deviceCollection = "{"1":["4G","5G","IG","IIG","6/10G","8/12G","1DG","15DG","16DG","23420G","23422G"],
        "2":["1-J1","1-J2","1-J3","1-X1","1-X2","3-J1"],
        "9":["空调单项","信号机械室","电源屏室","计算机室"]}";
        System.out.println(this.disassembleDeviceCollection(deviceCollection));
    }*/


    /*
    *   根据设备类型计算设备报警记录部分健康度
    * */
    public BigDecimal getAlarmHealthIndex(Integer stationId,String deviceName,
                                          ParamWeightConfigDO paramWeightConfigDO){
        List<HistoryAlarmDO> alarmDOList = historyAlarmService.
                listByAlarmDeviceAndStationId(deviceName,stationId);

        BigDecimal alarmDeviceHealthIndex = new BigDecimal("0");

        for(HistoryAlarmDO alarmDO:alarmDOList){
            if(alarmDO.getFlevel().equals(1)){
//                System.out.println("一级报警");

                alarmDeviceHealthIndex = alarmDeviceHealthIndex.add(
                        paramWeightConfigDO.getLevel1AlarmWeight());
            }else if(alarmDO.getFlevel().equals(2)){
//                System.out.println("二级报警");
                alarmDeviceHealthIndex = alarmDeviceHealthIndex.add(
                        paramWeightConfigDO.getLevel2AlarmWeight());
            }else if(alarmDO.getFlevel().equals(3)){
//                System.out.println("三级报警");
                alarmDeviceHealthIndex = alarmDeviceHealthIndex.add(
                        paramWeightConfigDO.getLevel3AlarmWeight());
            }else if(alarmDO.getFlevel().equals(0)){
//                System.out.println("预警");
                alarmDeviceHealthIndex = alarmDeviceHealthIndex.add(
                        paramWeightConfigDO.getForecastWeight());
            }
        }
//        log.info("车站id:{} 设备名称:{},其报警记录健康度为{}",stationId,deviceName,alarmDeviceHealthIndex);
        return alarmDeviceHealthIndex;
    }

    /*
    *  根据设备类型计算设备天窗修部分健康度
    * */
    public BigDecimal getSkylightHealghIndex(Integer stationId,String deviceName,
                                             ParamWeightConfigDO paramWeightConfigDO){
        BigDecimal healthIndex = new BigDecimal("0");
        //需要先获取车站对应的天窗修记录
        List<SkylightRecordDO> skylightRecordDOList = null;
        try {
            skylightRecordDOList = skylightService.listByStationId(stationId);
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        if(skylightRecordDOList.size() == 0 ){
            log.info("-------车站{}没有对应天窗修记录-------");
        }
        for(SkylightRecordDO skylightRecordDO:skylightRecordDOList){
//            System.out.println(skylightRecordDO.getDeviceCollection());
            List deviceList = this.parseDeviceCollection(skylightRecordDO.getDeviceCollection());
            if(deviceList.contains(deviceName)){
                log.info("车站{} 设备{} 存在天窗修记录，时间为{}",stationId,deviceName,skylightRecordDO.getStartTime());
                healthIndex.add(paramWeightConfigDO.getSkylightWeight());
                log.info("设备天窗修部分hi增加{}",paramWeightConfigDO.getSkylightWeight());
            }
        }
//        log.info("车站id:{}设备名称:{}，其天窗修部分健康度为{}",stationId,deviceName,healthIndex);
        return healthIndex;
    }

    /*
    *   根据设备类型计算设备人工辅正部分健康度
    * */
    public BigDecimal getManualHealthIndex(Integer deviceId){
        BigDecimal healthIndex = new BigDecimal("0");

        //获取当前月份的第一天和最后一天
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//1:本月第一天
        String beginDate = format.format(c.getTime());
//        System.out.println("本月第一天:"+beginDate);

        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        String endDate = format.format(c.getTime());
//        System.out.println("本月最后一天:"+endDate);

        List<ManualAdjustRecordDO> adjustRecordDOList = null;

        adjustRecordDOList = manualAdjustRecordDOMapper.selectByDeviceIdAndDate(
                deviceId,beginDate,endDate);

        if(adjustRecordDOList.size() > 1){
//            throw new BusinessException(EmBusinessError.UNKOWN_ERROR,"本月内人工辅正记录多于一条");
            log.info("本月内人工辅正记录多于一条");
        }else if(adjustRecordDOList.size() == 1){
            log.info("设备id:{}本月具有人工辅正记录,辅正值为{}",deviceId,adjustRecordDOList.get(0).getValue());
            healthIndex.add(adjustRecordDOList.get(0).getValue());
        }
//        log.info("设备id:{}本月人工辅正值为{}",deviceId,healthIndex);
        return healthIndex;
    }

    public BigDecimal getLifeHealthIndex(Integer deviceId,
                                         ParamWeightConfigDO paramWeightConfigDO)
            throws BusinessException{
        BigDecimal healthIndex = new BigDecimal("0");
        // 入参校验
        // 获取设备信息
        DeviceInfoDO deviceInfoDO = null;
        deviceInfoDO = deviceInfoDOMapper.selectByPrimaryKey(deviceId);
        if(null == deviceInfoDO){
            log.info("id为{}的设备不存在",deviceId);
            throw new BusinessException(EmBusinessError.DEVICE_NOT_EXIST);
        }

        //        根据上道时间和使用寿命计算健康度
//        System.out.println(deviceInfoDO.getProduceDate());
//        System.out.println(deviceInfoDO.getInuseDate());

//       计算设备已经使用的天数
        long inuseTimestamp = deviceInfoDO.getInuseDate().getTime();
        int usedDays = (int)(( new Date().getTime() - inuseTimestamp )/(24*60*60*1000));
        log.info("id为{}的设备已经使用了{}天",deviceId,usedDays);

        log.info("id为{}的设备的使用寿命为{}天",deviceId,deviceInfoDO.getPerspectLife());
        double paramLife = 1.00 - ((double)usedDays/deviceInfoDO.getPerspectLife());
        String paramLifeStr = String.format("%.2f",paramLife);

/*        healthIndexModel.setAlarmDeviceHealthIndex(
                healthIndexModel.getAlarmDeviceHealthIndex().add(
                        paramWeightConfigDO.getLifeWeight().multiply(new BigDecimal(paramLifeStr))));*/
        healthIndex = healthIndex.add(paramWeightConfigDO.getLifeWeight().multiply(new BigDecimal(paramLifeStr)));
//        log.info("设备id:{}使用寿命健康度为{}",deviceId,healthIndex);
        return healthIndex;
    }

}
