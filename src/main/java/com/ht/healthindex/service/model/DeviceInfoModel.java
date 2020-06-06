package com.ht.healthindex.service.model;

import lombok.Data;

import java.util.Date;

@Data
public class DeviceInfoModel {
    private Integer id;

    private String deviceName;

    private Integer stationId;

    private String stationName;

    private String deviceType;

    private Date produceDate;

    private Date inuseDate;

    private Integer perspectLife;
}
