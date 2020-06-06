package com.ht.healthindex.service.model;

import lombok.Data;

/*
*   车站不同设备类型健康状态占比 领域数据模型
*
* */
@Data
public class HealthStatusByTypeModel {
    private Integer stationId;

    private String stationName;

    private String deviceType;

    /*
    *   健康设备数量
    * */
    private Integer healthyCount;

    /*
    *   亚健康（健康度大于等于70小于85）装备设备数量
    * */
    private Integer subhealthyCount;
    /*
    *   异常状态(健康度大于等于60小于70)设备数量
    * */
    private Integer abnormalCount;

    /*
    *   病态(健康度大于等于40小于60)设备数量
    * */
    private Integer morbidCount;

    /*
    *   故障状态（健康度小于40）的设备数量
    * */
    private Integer errorCount;
}
