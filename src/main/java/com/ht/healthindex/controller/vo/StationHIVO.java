package com.ht.healthindex.controller.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class StationHIVO {
    private Integer id;

    private Integer stationId;

    private String stationName;

    private String lineName;

    private String workshopName;

    private String sectionName;

    private String companyName;

    private BigDecimal healthIndex;

    private Date createDate;
}
