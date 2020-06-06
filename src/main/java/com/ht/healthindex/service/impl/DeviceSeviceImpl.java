package com.ht.healthindex.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.healthindex.dao.DeviceInfoDOMapper;
import com.ht.healthindex.dataobject.DeviceInfoDO;
import com.ht.healthindex.service.DeviceService;
import com.ht.healthindex.service.model.DeviceInfoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceSeviceImpl implements DeviceService{
    @Autowired
    private DeviceInfoDOMapper deviceInfoDOMapper;

    @Override
    public List<DeviceInfoDO> listDeviceInfo() {
        return deviceInfoDOMapper.listDeviceInfo();
    }

    @Override
    public PageInfo<DeviceInfoDO> listDeviceInfoByPage(Integer page, Integer number) {
        PageHelper.startPage(page,number).setOrderBy("id desc");
        PageInfo<DeviceInfoDO> deviceInfoDOPageInfo = new PageInfo<>(deviceInfoDOMapper.listDeviceInfo());
        return deviceInfoDOPageInfo;
    }

    @Override
    public List<DeviceInfoModel> listDeviceInfoModel() {
        return deviceInfoDOMapper.listDeviceInfoModel();
    }

}
