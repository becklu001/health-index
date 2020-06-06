package com.ht.healthindex.dao;

import com.ht.healthindex.dataobject.StationInfoDO;

import java.util.List;

public interface StationInfoDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StationInfoDO record);

    int insertSelective(StationInfoDO record);

    StationInfoDO selectByPrimaryKey(Integer id);

    List<StationInfoDO> listStationInfo();

    int updateByPrimaryKeySelective(StationInfoDO record);

    int updateByPrimaryKey(StationInfoDO record);
}