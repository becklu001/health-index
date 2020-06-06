package com.ht.healthindex.service;

import com.ht.healthindex.dataobject.ManualAdjustRecordDO;
import com.ht.healthindex.error.BusinessException;

import java.util.List;

public interface ManualAdjustService {
     List<ManualAdjustRecordDO> listByDeviceIdAndDate(Integer deviceId,String beginDate,String endDate);

    /*
    *   根据设备id查找最新的人工辅正记录
    * */
     ManualAdjustRecordDO selectByDeviceId(Integer deviceId) throws BusinessException;
}
