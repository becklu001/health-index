package com.ht.healthindex.service;

import com.github.pagehelper.PageInfo;
import com.ht.healthindex.dataobject.DeviceInfoDO;
import com.ht.healthindex.service.model.DeviceInfoModel;

import java.util.List;

public interface DeviceService {
    List<DeviceInfoDO> listDeviceInfo();
    PageInfo<DeviceInfoDO> listDeviceInfoByPage(Integer page, Integer number);

    /*
    *   查询所有的设备列表，返回DeviceInfoModel，冗余了stationName字段
    * */
    List<DeviceInfoModel> listDeviceInfoModel();
}
