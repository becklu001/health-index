package com.ht.healthindex.dao;

import com.ht.healthindex.dataobject.ParamWeightConfigDO;

import java.util.List;

public interface ParamWeightConfigDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ParamWeightConfigDO record);

    int insertSelective(ParamWeightConfigDO record);

    ParamWeightConfigDO selectByPrimaryKey(Integer id);

    //    如果存在多条可供选择的平行规则，可以使用该接口查找所有的规则记录
    List<ParamWeightConfigDO> listConfig();

    //    获取最新的配置规则
    ParamWeightConfigDO getLatestConfig();

    int updateByPrimaryKeySelective(ParamWeightConfigDO record);

    int updateByPrimaryKey(ParamWeightConfigDO record);
}