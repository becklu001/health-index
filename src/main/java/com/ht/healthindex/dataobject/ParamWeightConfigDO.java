package com.ht.healthindex.dataobject;

import java.math.BigDecimal;
import java.util.Date;

public class ParamWeightConfigDO {
    private Integer id;

    private BigDecimal level1AlarmWeight;

    private BigDecimal level2AlarmWeight;

    private BigDecimal level3AlarmWeight;

    private BigDecimal forecastWeight;

    private BigDecimal countWeight;

    private BigDecimal skylightWeight;

    private BigDecimal manualWeight;

    private Date manualUpdateDate;

    private BigDecimal lifeWeight;

    private Date createTime;

    private Date updateTime;

    private Integer updateBy;

    private String remarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getLevel1AlarmWeight() {
        return level1AlarmWeight;
    }

    public void setLevel1AlarmWeight(BigDecimal level1AlarmWeight) {
        this.level1AlarmWeight = level1AlarmWeight;
    }

    public BigDecimal getLevel2AlarmWeight() {
        return level2AlarmWeight;
    }

    public void setLevel2AlarmWeight(BigDecimal level2AlarmWeight) {
        this.level2AlarmWeight = level2AlarmWeight;
    }

    public BigDecimal getLevel3AlarmWeight() {
        return level3AlarmWeight;
    }

    public void setLevel3AlarmWeight(BigDecimal level3AlarmWeight) {
        this.level3AlarmWeight = level3AlarmWeight;
    }

    public BigDecimal getForecastWeight() {
        return forecastWeight;
    }

    public void setForecastWeight(BigDecimal forecastWeight) {
        this.forecastWeight = forecastWeight;
    }

    public BigDecimal getCountWeight() {
        return countWeight;
    }

    public void setCountWeight(BigDecimal countWeight) {
        this.countWeight = countWeight;
    }

    public BigDecimal getSkylightWeight() {
        return skylightWeight;
    }

    public void setSkylightWeight(BigDecimal skylightWeight) {
        this.skylightWeight = skylightWeight;
    }

    public BigDecimal getManualWeight() {
        return manualWeight;
    }

    public void setManualWeight(BigDecimal manualWeight) {
        this.manualWeight = manualWeight;
    }

    public Date getManualUpdateDate() {
        return manualUpdateDate;
    }

    public void setManualUpdateDate(Date manualUpdateDate) {
        this.manualUpdateDate = manualUpdateDate;
    }

    public BigDecimal getLifeWeight() {
        return lifeWeight;
    }

    public void setLifeWeight(BigDecimal lifeWeight) {
        this.lifeWeight = lifeWeight;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }
}