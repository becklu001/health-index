package com.ht.healthindex.dataobject;

import java.util.Date;

public class HistoryAlarmDO extends HistoryAlarmDOKey {
    private String fstationcode;

    private String falarmdevice;

    private Integer fstationid;

    private Integer falarmcode;

    private Integer flevel;

    private String fcontent;

    private Date frecovertime;

    private Integer fisrecover;

    private Integer fishandle;

    private Integer fisfix;

    private Integer fdevicetype;

    private Integer finterfacetype;

    private String frelateddatakeys;

    private Integer fdatakey;

    private Integer falarmreasoncode;

    private String falarmreason;

    private String falarmadvice;

    private Integer falarmfollow;

    private Date inserttime;

    private Integer fislook;

    private Integer fequmentstate;

    private Integer falarmidentification;

    private Date fdealtime;

    private String falarmreasonafter;

    private String freasonpeople;

    private Date freasontime;

    private String frootuinkey;

    private Integer falarmreasoncodeafter;

    private String falarmadviceafter;

    private Integer fispeoplesure;

    private Integer fmonitoritem;

    private String ffaultlocation;

    private Integer fqudevicetypeid;

    private Integer fobstaclephenomenonid;

    private Integer freasonlocationid;

    private String flevelpromotemsg;

    public String getFstationcode() {
        return fstationcode;
    }

    public void setFstationcode(String fstationcode) {
        this.fstationcode = fstationcode == null ? null : fstationcode.trim();
    }

    public String getFalarmdevice() {
        return falarmdevice;
    }

    public void setFalarmdevice(String falarmdevice) {
        this.falarmdevice = falarmdevice == null ? null : falarmdevice.trim();
    }

    public Integer getFstationid() {
        return fstationid;
    }

    public void setFstationid(Integer fstationid) {
        this.fstationid = fstationid;
    }

    public Integer getFalarmcode() {
        return falarmcode;
    }

    public void setFalarmcode(Integer falarmcode) {
        this.falarmcode = falarmcode;
    }

    public Integer getFlevel() {
        return flevel;
    }

    public void setFlevel(Integer flevel) {
        this.flevel = flevel;
    }

    public String getFcontent() {
        return fcontent;
    }

    public void setFcontent(String fcontent) {
        this.fcontent = fcontent == null ? null : fcontent.trim();
    }

    public Date getFrecovertime() {
        return frecovertime;
    }

    public void setFrecovertime(Date frecovertime) {
        this.frecovertime = frecovertime;
    }

    public Integer getFisrecover() {
        return fisrecover;
    }

    public void setFisrecover(Integer fisrecover) {
        this.fisrecover = fisrecover;
    }

    public Integer getFishandle() {
        return fishandle;
    }

    public void setFishandle(Integer fishandle) {
        this.fishandle = fishandle;
    }

    public Integer getFisfix() {
        return fisfix;
    }

    public void setFisfix(Integer fisfix) {
        this.fisfix = fisfix;
    }

    public Integer getFdevicetype() {
        return fdevicetype;
    }

    public void setFdevicetype(Integer fdevicetype) {
        this.fdevicetype = fdevicetype;
    }

    public Integer getFinterfacetype() {
        return finterfacetype;
    }

    public void setFinterfacetype(Integer finterfacetype) {
        this.finterfacetype = finterfacetype;
    }

    public String getFrelateddatakeys() {
        return frelateddatakeys;
    }

    public void setFrelateddatakeys(String frelateddatakeys) {
        this.frelateddatakeys = frelateddatakeys == null ? null : frelateddatakeys.trim();
    }

    public Integer getFdatakey() {
        return fdatakey;
    }

    public void setFdatakey(Integer fdatakey) {
        this.fdatakey = fdatakey;
    }

    public Integer getFalarmreasoncode() {
        return falarmreasoncode;
    }

    public void setFalarmreasoncode(Integer falarmreasoncode) {
        this.falarmreasoncode = falarmreasoncode;
    }

    public String getFalarmreason() {
        return falarmreason;
    }

    public void setFalarmreason(String falarmreason) {
        this.falarmreason = falarmreason == null ? null : falarmreason.trim();
    }

    public String getFalarmadvice() {
        return falarmadvice;
    }

    public void setFalarmadvice(String falarmadvice) {
        this.falarmadvice = falarmadvice == null ? null : falarmadvice.trim();
    }

    public Integer getFalarmfollow() {
        return falarmfollow;
    }

    public void setFalarmfollow(Integer falarmfollow) {
        this.falarmfollow = falarmfollow;
    }

    public Date getInserttime() {
        return inserttime;
    }

    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
    }

    public Integer getFislook() {
        return fislook;
    }

    public void setFislook(Integer fislook) {
        this.fislook = fislook;
    }

    public Integer getFequmentstate() {
        return fequmentstate;
    }

    public void setFequmentstate(Integer fequmentstate) {
        this.fequmentstate = fequmentstate;
    }

    public Integer getFalarmidentification() {
        return falarmidentification;
    }

    public void setFalarmidentification(Integer falarmidentification) {
        this.falarmidentification = falarmidentification;
    }

    public Date getFdealtime() {
        return fdealtime;
    }

    public void setFdealtime(Date fdealtime) {
        this.fdealtime = fdealtime;
    }

    public String getFalarmreasonafter() {
        return falarmreasonafter;
    }

    public void setFalarmreasonafter(String falarmreasonafter) {
        this.falarmreasonafter = falarmreasonafter == null ? null : falarmreasonafter.trim();
    }

    public String getFreasonpeople() {
        return freasonpeople;
    }

    public void setFreasonpeople(String freasonpeople) {
        this.freasonpeople = freasonpeople == null ? null : freasonpeople.trim();
    }

    public Date getFreasontime() {
        return freasontime;
    }

    public void setFreasontime(Date freasontime) {
        this.freasontime = freasontime;
    }

    public String getFrootuinkey() {
        return frootuinkey;
    }

    public void setFrootuinkey(String frootuinkey) {
        this.frootuinkey = frootuinkey == null ? null : frootuinkey.trim();
    }

    public Integer getFalarmreasoncodeafter() {
        return falarmreasoncodeafter;
    }

    public void setFalarmreasoncodeafter(Integer falarmreasoncodeafter) {
        this.falarmreasoncodeafter = falarmreasoncodeafter;
    }

    public String getFalarmadviceafter() {
        return falarmadviceafter;
    }

    public void setFalarmadviceafter(String falarmadviceafter) {
        this.falarmadviceafter = falarmadviceafter == null ? null : falarmadviceafter.trim();
    }

    public Integer getFispeoplesure() {
        return fispeoplesure;
    }

    public void setFispeoplesure(Integer fispeoplesure) {
        this.fispeoplesure = fispeoplesure;
    }

    public Integer getFmonitoritem() {
        return fmonitoritem;
    }

    public void setFmonitoritem(Integer fmonitoritem) {
        this.fmonitoritem = fmonitoritem;
    }

    public String getFfaultlocation() {
        return ffaultlocation;
    }

    public void setFfaultlocation(String ffaultlocation) {
        this.ffaultlocation = ffaultlocation == null ? null : ffaultlocation.trim();
    }

    public Integer getFqudevicetypeid() {
        return fqudevicetypeid;
    }

    public void setFqudevicetypeid(Integer fqudevicetypeid) {
        this.fqudevicetypeid = fqudevicetypeid;
    }

    public Integer getFobstaclephenomenonid() {
        return fobstaclephenomenonid;
    }

    public void setFobstaclephenomenonid(Integer fobstaclephenomenonid) {
        this.fobstaclephenomenonid = fobstaclephenomenonid;
    }

    public Integer getFreasonlocationid() {
        return freasonlocationid;
    }

    public void setFreasonlocationid(Integer freasonlocationid) {
        this.freasonlocationid = freasonlocationid;
    }

    public String getFlevelpromotemsg() {
        return flevelpromotemsg;
    }

    public void setFlevelpromotemsg(String flevelpromotemsg) {
        this.flevelpromotemsg = flevelpromotemsg == null ? null : flevelpromotemsg.trim();
    }
}