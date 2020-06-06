package com.ht.healthindex.service.impl;

import com.ht.healthindex.dao.HistoryAlarmDOMapper;
import com.ht.healthindex.dataobject.HistoryAlarmDO;
import com.ht.healthindex.service.HistoryAlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryAlarmServiceImpl implements HistoryAlarmService {
    @Autowired
    private HistoryAlarmDOMapper historyAlarmDOMapper;

    /*
    *   根据设备名称 查找历史报警信息，不分页
    *   设备名称字段不唯一（多站可能重名），因此需要根据站id 和 设备名称两个字段进行查询
    * */
    @Override
    public List<HistoryAlarmDO> listByAlarmDevice(String falarmdevice) {
//        List<HistoryAlarmDO> alarmDOList = new ArrayList<>();
//        return historyAlarmDOMapper.selectByAlarmDevice(falarmdevice);
        return null;
    }

    @Override
    public List<HistoryAlarmDO> listByAlarmDeviceAndStationId(String falarmdevice, Integer fstationid) {
        //入参校验
        List<HistoryAlarmDO> alarmDOList = historyAlarmDOMapper.listByAlarmDeviceAndStationId(falarmdevice,fstationid);

        return alarmDOList;
    }
}
