package com.ht.healthindex.service.impl;

import com.ht.healthindex.dao.DeviceTypeHIDOMapper;
import com.ht.healthindex.dao.HealthIndexByTypeDOMapper;
import com.ht.healthindex.dao.StationHIDOMapper;
import com.ht.healthindex.dataobject.DeviceTypeHIDO;
import com.ht.healthindex.dataobject.HealthIndexByTypeDO;
import com.ht.healthindex.dataobject.StationHIDO;
import com.ht.healthindex.error.BusinessException;
import com.ht.healthindex.error.EmBusinessError;
import com.ht.healthindex.service.ComputeService;
import com.ht.healthindex.service.GatherService;
import com.ht.healthindex.service.model.DeviceTypeHIModel;
import com.ht.healthindex.service.model.HealthIndexByTypeModel;
import com.ht.healthindex.service.model.StationHIModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GatherServiceImpl implements GatherService {
    @Autowired
    private HealthIndexByTypeDOMapper healthIndexByTypeDOMapper;
    @Autowired
    private ComputeService computeService;
    @Autowired
    private DeviceTypeHIDOMapper deviceTypeHIDOMapper;
    @Autowired
    private StationHIDOMapper stationHIDOMapper;

    @Override
    public int gatherHealthIndexByType(List<HealthIndexByTypeModel> healthIndexList) throws BusinessException {
//        入参校验
        if(healthIndexList == null || healthIndexList.size() == 0){
            return 0;
        }

        HealthIndexByTypeDO healthIndexByTypeDO = new HealthIndexByTypeDO();
        int count = 0;

        for(HealthIndexByTypeModel healthIndexByTypeModel:healthIndexList){
            BeanUtils.copyProperties(healthIndexByTypeModel,healthIndexByTypeDO);
            healthIndexByTypeDO.setCreateDate(new Date());
            if(healthIndexByTypeDOMapper.insertSelective(healthIndexByTypeDO) == 1){
                count++;
            }else{
                throw new BusinessException(EmBusinessError.UNKOWN_ERROR,"写入记录失败");
            }
        }
        return count++;
    }

    @Scheduled(cron = "0 0 12/1 * * *")
    @Override
    public int gatherHealthIndexByType() throws BusinessException {
        List<HealthIndexByTypeModel> healthIndexList = computeService.listHealthIndexByType();
        if(healthIndexList == null || healthIndexList.size() == 0){
            return 0;
        }

        HealthIndexByTypeDO healthIndexByTypeDO = new HealthIndexByTypeDO();
        int count = 0;

        for(HealthIndexByTypeModel healthIndexByTypeModel:healthIndexList){
            BeanUtils.copyProperties(healthIndexByTypeModel,healthIndexByTypeDO);
            healthIndexByTypeDO.setCreateDate(new Date());
            if(healthIndexByTypeDOMapper.insertSelective(healthIndexByTypeDO) == 1){
                count++;
            }else{
                throw new BusinessException(EmBusinessError.UNKOWN_ERROR,"写入记录失败");
            }
        }
        return count++;
    }

    @Scheduled(cron = "0 5 8/1 * * *")
    @Override
    public int gatherDeviceTypeHI() throws BusinessException{
        List<DeviceTypeHIModel> deviceTypeHIModelList = computeService.listDeviceTypeHI();
        if(deviceTypeHIModelList == null || deviceTypeHIModelList.size() == 0){
            return 0;
        }

        int count = 0;
        for(DeviceTypeHIModel deviceTypeHIModel:deviceTypeHIModelList){
            DeviceTypeHIDO deviceTypeHIDO = new DeviceTypeHIDO();
            BeanUtils.copyProperties(deviceTypeHIModel,deviceTypeHIDO);
            deviceTypeHIDO.setCreateDate(new Date());

            if(deviceTypeHIDOMapper.insertSelective(deviceTypeHIDO) == 1){
                count++;
            }else{
                throw new BusinessException(EmBusinessError.UNKOWN_ERROR,"写入记录失败");
            }
        }

        return count;
    }

    @Scheduled(cron = "0 10 8/1 * * *")
    @Override
    public int gatherStationHI() throws BusinessException{
        List<StationHIModel> stationHIModelList = computeService.listStationHI();

        if(null == stationHIModelList || stationHIModelList.size()==0){
            return 0;
        }
        int count = 0;
        for(StationHIModel stationHIModel:stationHIModelList){
            StationHIDO stationHIDO = new StationHIDO();
            BeanUtils.copyProperties(stationHIModel,stationHIDO);
            stationHIDO.setCreateDate(new Date());

            if(stationHIDOMapper.insertSelective(stationHIDO) == 1){
                count++;
            }else{
                throw new BusinessException(EmBusinessError.UNKOWN_ERROR,"写入记录失败");
            }
        }
        return count;
    }

}
