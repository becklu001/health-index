package com.ht.healthindex.dataobject;

import java.util.Date;

public class SkylightRecordDO {
    private Integer id;

    private Integer stationId;

    private Date startTime;

    private Date endTime;

    private Byte type;

    private Byte mode;

    private String remarks;

    private String deviceCollection;

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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getMode() {
        return mode;
    }

    public void setMode(Byte mode) {
        this.mode = mode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getDeviceCollection() {
        return deviceCollection;
    }

    public void setDeviceCollection(String deviceCollection) {
        this.deviceCollection = deviceCollection == null ? null : deviceCollection.trim();
    }
}