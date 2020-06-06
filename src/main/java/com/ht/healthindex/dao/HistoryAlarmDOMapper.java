package com.ht.healthindex.dao;

import com.ht.healthindex.dataobject.HistoryAlarmDO;
import com.ht.healthindex.dataobject.HistoryAlarmDOKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HistoryAlarmDOMapper {
    int deleteByPrimaryKey(HistoryAlarmDOKey key);

    int insert(HistoryAlarmDO record);

    int insertSelective(HistoryAlarmDO record);

    HistoryAlarmDO selectByPrimaryKey(HistoryAlarmDOKey key);

//    根据设备名和车站id查找对应的报警信息
    List<HistoryAlarmDO> listByAlarmDeviceAndStationId(@Param("falarmdevice") String falarmdevice,
                                                       @Param("fstationid") Integer fstationid);

    int updateByPrimaryKeySelective(HistoryAlarmDO record);

    int updateByPrimaryKey(HistoryAlarmDO record);
}