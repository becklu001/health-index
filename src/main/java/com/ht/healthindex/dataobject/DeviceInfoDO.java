package com.ht.healthindex.dataobject;

import java.util.Date;

public class DeviceInfoDO {
    private Integer id;

    private String deviceName;

    private Integer stationId;

    private String deviceType;

    private Date produceDate;

    private Date inuseDate;

    private Integer perspectLife;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    public Date getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(Date produceDate) {
        this.produceDate = produceDate;
    }

    public Date getInuseDate() {
        return inuseDate;
    }

    public void setInuseDate(Date inuseDate) {
        this.inuseDate = inuseDate;
    }

    public Integer getPerspectLife() {
        return perspectLife;
    }

    public void setPerspectLife(Integer perspectLife) {
        this.perspectLife = perspectLife;
    }
}