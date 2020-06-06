package com.ht.healthindex.service.impl;

import com.ht.healthindex.dao.ManualAdjustRecordDOMapper;
import com.ht.healthindex.dataobject.ManualAdjustRecordDO;
import com.ht.healthindex.error.BusinessException;
import com.ht.healthindex.error.EmBusinessError;
import com.ht.healthindex.service.ManualAdjustService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManualAdjustServiceImpl implements ManualAdjustService{
    @Autowired
    private ManualAdjustRecordDOMapper manualAdjustRecordDOMapper;

    @Override
    public List<ManualAdjustRecordDO> listByDeviceIdAndDate(Integer deviceId, String beginDate, String endDate) {
//        入参校验
        List<ManualAdjustRecordDO> adjustRecordDOList = manualAdjustRecordDOMapper.
                selectByDeviceIdAndDate(deviceId,beginDate,endDate);
        return adjustRecordDOList;
    }

    @Override
    public ManualAdjustRecordDO selectByDeviceId(Integer deviceId) throws BusinessException{
        if(null == deviceId){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"设备ID不能为空");
        }

        ManualAdjustRecordDO manualAdjustRecordDO = manualAdjustRecordDOMapper.selectByDeviceId(deviceId);
        return manualAdjustRecordDO;
    }


}
