package com.ht.healthindex.service;

import com.ht.healthindex.dataobject.HistoryAlarmDO;

import java.util.List;

public interface HistoryAlarmService {

    List<HistoryAlarmDO> listByAlarmDevice(String falarmdevice);
    //    设备名称字段不唯一（多站可能重名），因此需要根据站id 和 设备名称两个字段进行查询
    List<HistoryAlarmDO> listByAlarmDeviceAndStationId(String falarmdevice,Integer fstationid);
}
