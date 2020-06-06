package com.ht.healthindex.dao;

import com.ht.healthindex.dataobject.StationHIDO;

import java.util.List;

public interface StationHIDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StationHIDO record);

    int insertSelective(StationHIDO record);

    StationHIDO selectByPrimaryKey(Integer id);

    List<StationHIDO> listStationHILatestByCondition(StationHIDO stationHIDO);

    /*
    *   查询最近30天符合条件的车站健康度记录(时间不去重)
    * */
    List<StationHIDO> listStationHILatest30days(StationHIDO stationHIDO);

    int updateByPrimaryKeySelective(StationHIDO record);

    int updateByPrimaryKey(StationHIDO record);
}