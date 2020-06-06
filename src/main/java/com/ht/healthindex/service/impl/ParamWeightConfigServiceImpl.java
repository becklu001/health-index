package com.ht.healthindex.service.impl;

import com.ht.healthindex.dao.ParamWeightConfigDOMapper;
import com.ht.healthindex.dataobject.ParamWeightConfigDO;
import com.ht.healthindex.error.BusinessException;
import com.ht.healthindex.error.EmBusinessError;
import com.ht.healthindex.response.CommonResult;
import com.ht.healthindex.service.ParamWeightConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ParamWeightConfigServiceImpl implements ParamWeightConfigService {
    @Autowired
    private ParamWeightConfigDOMapper paramWeightConfigDOMapper;

    @Override
    public ParamWeightConfigDO getParamWeightConfigById(Integer id) throws BusinessException{
//        入参校验
        if(null == id || !(id > 0)){
            log.info("-------参数不合法----------");
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        return paramWeightConfigDOMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ParamWeightConfigDO> listParamWeightConfig() {
        return null;
    }

    @Override
    public ParamWeightConfigDO getLatestConfig() {
        ParamWeightConfigDO paramWeightConfigDO = paramWeightConfigDOMapper.getLatestConfig();
        return paramWeightConfigDO;
    }

    @Override
    public int updateWeightConfig(ParamWeightConfigDO paramWeightConfigDO) throws BusinessException{
        if(null == paramWeightConfigDO || paramWeightConfigDO.getId() == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"必传参数不能为空");
        }

        paramWeightConfigDO.setUpdateTime(new Date());

        int count = paramWeightConfigDOMapper.updateByPrimaryKeySelective(paramWeightConfigDO);
        return count;
    }

}
