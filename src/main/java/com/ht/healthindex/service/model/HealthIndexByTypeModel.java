package com.ht.healthindex.service.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class HealthIndexByTypeModel {
    private Integer deviceId;
    private String deviceName;
    private String deviceType;
    private Integer stationId;
    private String stationName;

//    按设备类型计算的设备健康度指数
    private BigDecimal healthIndex;

//    预期剩余使用寿命
    private Integer lifeLeft;
}
