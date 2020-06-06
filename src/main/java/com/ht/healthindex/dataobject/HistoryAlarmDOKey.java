package com.ht.healthindex.dataobject;

import java.util.Date;

public class HistoryAlarmDOKey {
    private String funikey;

    private Date falarmtime;

    public String getFunikey() {
        return funikey;
    }

    public void setFunikey(String funikey) {
        this.funikey = funikey == null ? null : funikey.trim();
    }

    public Date getFalarmtime() {
        return falarmtime;
    }

    public void setFalarmtime(Date falarmtime) {
        this.falarmtime = falarmtime;
    }
}