package com.ht.healthindex.controller.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class HealthIndexBySystemVO {
    private Integer id;

    private Integer stationId;

    private String stationName;

    private String interfaceName;

    private BigDecimal healthIndex;

    private Integer healthyCount;

    private Integer subhealthyCount;

    private Integer abnormalCount;

    private Integer morbidCount;

    private Integer errorCount;

    private Date createDate;
}
