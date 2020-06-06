package com.ht.healthindex.service.impl;

import com.ht.healthindex.dao.SkylightRecordDOMapper;
import com.ht.healthindex.dataobject.SkylightRecordDO;
import com.ht.healthindex.error.BusinessException;
import com.ht.healthindex.error.EmBusinessError;
import com.ht.healthindex.service.SkylightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SkylightServiceImpl implements SkylightService {
    @Autowired
    private SkylightRecordDOMapper skylightRecordDOMapper;

    @Override
    public List<SkylightRecordDO> listByStationId(Integer stationId) throws BusinessException{
//        入参校验
        if(null == stationId || !(stationId > 0)){
           log.info("-------输入车站id不合法--------");
           throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        List<SkylightRecordDO> skylightRecordDOList =
                skylightRecordDOMapper.listByStationId(stationId);

        return skylightRecordDOList;
    }
}
