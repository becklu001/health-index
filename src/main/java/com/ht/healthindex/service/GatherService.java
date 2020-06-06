package com.ht.healthindex.service;

import com.ht.healthindex.error.BusinessException;
import com.ht.healthindex.service.model.HealthIndexByTypeModel;

import java.util.List;

public interface GatherService {
    /*
    *   采集设备健康度指数存放入数据库
    * */
    int gatherHealthIndexByType(List<HealthIndexByTypeModel> healthIndexList) throws BusinessException;

    int gatherHealthIndexByType()throws BusinessException;

    /*
    *  采集设备类型健康度存放入数据库
    * */
    int gatherDeviceTypeHI() throws BusinessException;

    /*
    *   采集车站健康度存放入数据库
    * */
    public int gatherStationHI() throws BusinessException;
}
