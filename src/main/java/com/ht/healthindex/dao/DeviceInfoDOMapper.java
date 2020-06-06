package com.ht.healthindex.dao;

import com.ht.healthindex.dataobject.DeviceInfoDO;
import com.ht.healthindex.service.model.DeviceInfoModel;

import java.util.List;

public interface DeviceInfoDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceInfoDO record);

    int insertSelective(DeviceInfoDO record);

    DeviceInfoDO selectByPrimaryKey(Integer id);

//    获取所有设备列表
    List<DeviceInfoDO> listDeviceInfo();
//    根据车站id查找所属设备
    List<DeviceInfoDO> listDeviceInfoByStationId(Integer stationId);
//    获取所有设备信息列表，关联station表获取 stationName
    List<DeviceInfoModel> listDeviceInfoModel();

    int updateByPrimaryKeySelective(DeviceInfoDO record);

    int updateByPrimaryKey(DeviceInfoDO record);
}