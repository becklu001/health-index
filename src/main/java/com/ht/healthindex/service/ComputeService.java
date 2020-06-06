package com.ht.healthindex.service;

import com.ht.healthindex.dataobject.HistoryAlarmDO;
import com.ht.healthindex.error.BusinessException;
import com.ht.healthindex.service.model.DeviceHealthIndexModel;
import com.ht.healthindex.service.model.DeviceTypeHIModel;
import com.ht.healthindex.service.model.HealthIndexByTypeModel;
import com.ht.healthindex.service.model.StationHIModel;

import java.util.List;

public interface ComputeService {
    /*
    *   获取报警记录部分健康度指数
    *   parameter:String fdevicename
    *       Integer fstationid
    *       List<HistoryAlarmDO> alarmDOList
    * */
    DeviceHealthIndexModel getAlarmDeviceHealthIndex(
            String fdevicename,Integer fstationid,List<HistoryAlarmDO> alarmDOList);


    /*
    *   获取天窗修部分健康度指数
    *   parameter：Integer stationId,
    *       String deviceName
    * */
    DeviceHealthIndexModel getSkylightHealthIndex(Integer stationId, String deviceName);

    /*
    *   获取人工辅正部分健康度指数
    *   parameter:Integer deviceId
    * */
    DeviceHealthIndexModel getManualAdjustHealthIndex(Integer deviceId);

    /*
    *   获取设备寿命健康度指数
    *  parameter:Integer deviceId
    * */
    DeviceHealthIndexModel getLifeHealthIndex(Integer deviceId) throws BusinessException;

    /*
    *   根据设备类型计算健康指数 获取单个设备的健康度
    *   @param : deviceId 设备id
    *   @return: 设备按类型计算出的健康度model
    * */
    HealthIndexByTypeModel getHealthIndexByType(Integer deviceId);

    /*
    *   根据设备类型计算所有设备的健康指数
    *   @return 所有设备的健康度指数列表
    * */
    List<HealthIndexByTypeModel> listHealthIndexByType() throws BusinessException;

    /*
    *   计算设备类型的健康度指数
    *   @return 各车站所有设备类型的健康度指数列表
    * */
    List<DeviceTypeHIModel> listDeviceTypeHI() throws BusinessException;

    /*
    *   计算各车站的健康度指数
    *   @return 各车站的健康度指数列表
    * */
    List<StationHIModel> listStationHI()  throws BusinessException;

}
