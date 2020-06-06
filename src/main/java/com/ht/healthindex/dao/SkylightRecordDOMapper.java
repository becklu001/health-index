package com.ht.healthindex.dao;

import com.ht.healthindex.dataobject.SkylightRecordDO;

import java.util.List;

public interface SkylightRecordDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SkylightRecordDO record);

    int insertSelective(SkylightRecordDO record);

    SkylightRecordDO selectByPrimaryKey(Integer id);

//    根据车站id查找天窗修记录列表接口
    List<SkylightRecordDO> listByStationId(Integer stationId);

    int updateByPrimaryKeySelective(SkylightRecordDO record);

    int updateByPrimaryKeyWithBLOBs(SkylightRecordDO record);

    int updateByPrimaryKey(SkylightRecordDO record);
}