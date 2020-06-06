package com.ht.healthindex.service.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DeviceHealthIndexModel {
    private Integer deviceId;
    private String fDeviceName;
    private BigDecimal alarmDeviceHealthIndex;
}
