package com.ht.healthindex.service;

import com.ht.healthindex.dataobject.ParamWeightConfigDO;
import com.ht.healthindex.error.BusinessException;

import java.util.List;

public interface ParamWeightConfigService {
//    根据id获取指标权重配置规则接口
    ParamWeightConfigDO getParamWeightConfigById(Integer id) throws BusinessException;
//    获取所有指标权重配置规则接口
    List<ParamWeightConfigDO> listParamWeightConfig();

//    获取（修改时间）最新的权重配置规则
    ParamWeightConfigDO getLatestConfig();
//    修改权重配置规则,依据Id，Id为必传参数
    int updateWeightConfig(ParamWeightConfigDO paramWeightConfigDO) throws BusinessException;
}
