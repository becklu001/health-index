package com.ht.healthindex.dataobject;

import java.math.BigDecimal;
import java.util.Date;

public class HealthIndexByTypeDO {
    private Integer id;

    private Integer deviceId;

    private String deviceName;

    private String deviceType;

    private Integer stationId;

    private String stationName;

    private BigDecimal healthIndex;

    private Integer lifeLeft;

    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName == null ? null : stationName.trim();
    }

    public BigDecimal getHealthIndex() {
        return healthIndex;
    }

    public void setHealthIndex(BigDecimal healthIndex) {
        this.healthIndex = healthIndex;
    }

    public Integer getLifeLeft() {
        return lifeLeft;
    }

    public void setLifeLeft(Integer lifeLeft) {
        this.lifeLeft = lifeLeft;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}