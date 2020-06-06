package com.ht.healthindex.dao;

import com.ht.healthindex.dataobject.DeviceTypeHIDO;

import java.util.List;

public interface DeviceTypeHIDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceTypeHIDO record);

    int insertSelective(DeviceTypeHIDO record);

    DeviceTypeHIDO selectByPrimaryKey(Integer id);

    /*
    *   查找满足条件的（主要是车站id 或者车站名称）设备类型健康度（最新一日内）
    * */
    List<DeviceTypeHIDO> listDeviceTypeHILatestByCondition(DeviceTypeHIDO deviceTypeHIDO);

    int updateByPrimaryKeySelective(DeviceTypeHIDO record);

    int updateByPrimaryKey(DeviceTypeHIDO record);
}