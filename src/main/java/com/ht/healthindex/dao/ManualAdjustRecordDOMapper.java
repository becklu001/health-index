package com.ht.healthindex.dao;

import com.ht.healthindex.dataobject.ManualAdjustRecordDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ManualAdjustRecordDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ManualAdjustRecordDO record);

    int insertSelective(ManualAdjustRecordDO record);

    ManualAdjustRecordDO selectByPrimaryKey(Integer id);

//    根据设备Id和起始时间段查找 人工辅正记录
    List<ManualAdjustRecordDO> selectByDeviceIdAndDate(@Param("deviceId") Integer deviceId,
                                                       @Param("beginDate") String beginDate,
                                                       @Param("endDate") String endDate);

//    根据设备Id查找最新的人工辅正记录
    ManualAdjustRecordDO selectByDeviceId(Integer deviceId);

    int updateByPrimaryKeySelective(ManualAdjustRecordDO record);

    int updateByPrimaryKey(ManualAdjustRecordDO record);
}