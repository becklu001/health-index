package com.ht.healthindex.service.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class DeviceTypeHIModel {
    private Integer id;

    private Integer stationId;

    private String stationName;

    private String deviceType;

    private BigDecimal healthIndex;

    private Integer healthyCount;

    private Integer subhealthyCount;

    private Integer abnormalCount;

    private Integer morbidCount;

    private Integer errorCount;

    private Date createDate;
}
