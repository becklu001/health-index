package com.ht.healthindex.service;

import com.ht.healthindex.dataobject.SkylightRecordDO;
import com.ht.healthindex.error.BusinessException;

import java.util.List;

public interface SkylightService {
//    根据车站id获取 该站的天窗修记录集合
    List<SkylightRecordDO> listByStationId(Integer stationId) throws BusinessException;
}
