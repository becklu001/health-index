package com.ht.healthindex.dataobject;

import java.math.BigDecimal;
import java.util.Date;

public class DeviceTypeHIDO {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    public BigDecimal getHealthIndex() {
        return healthIndex;
    }

    public void setHealthIndex(BigDecimal healthIndex) {
        this.healthIndex = healthIndex;
    }

    public Integer getHealthyCount() {
        return healthyCount;
    }

    public void setHealthyCount(Integer healthyCount) {
        this.healthyCount = healthyCount;
    }

    public Integer getSubhealthyCount() {
        return subhealthyCount;
    }

    public void setSubhealthyCount(Integer subhealthyCount) {
        this.subhealthyCount = subhealthyCount;
    }

    public Integer getAbnormalCount() {
        return abnormalCount;
    }

    public void setAbnormalCount(Integer abnormalCount) {
        this.abnormalCount = abnormalCount;
    }

    public Integer getMorbidCount() {
        return morbidCount;
    }

    public void setMorbidCount(Integer morbidCount) {
        this.morbidCount = morbidCount;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}