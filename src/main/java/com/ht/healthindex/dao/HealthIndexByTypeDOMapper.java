package com.ht.healthindex.dao;

import com.ht.healthindex.dataobject.HealthIndexByTypeDO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface HealthIndexByTypeDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HealthIndexByTypeDO record);

    int insertSelective(HealthIndexByTypeDO record);

    HealthIndexByTypeDO selectByPrimaryKey(Integer id);

    //    查询所有设备全部健康度采集记录
    List<HealthIndexByTypeDO> listHealthIndex();

    //  查询所有设备的健康度记录（最新的）
    List<HealthIndexByTypeDO> listHealthIndexLatest();

//    查询符合条件的所有设备的健康度记录（最新的）
    List<HealthIndexByTypeDO> listHealthIndexLatestByCondition(HealthIndexByTypeDO healthIndex);

//    根据条件查询符合条件的健康度记录(如果传入createDate,则得到指定的一日内的记录)
    List<HealthIndexByTypeDO> listHealthIndexByCondition(HealthIndexByTypeDO healthIndex);

//    查询传入的时间 日内的健康度记录
    List<HealthIndexByTypeDO> listHealthIndexByDate(Date date);
//    查询最近30天内的符合条件的某设备的健康度记录列表
    List<HealthIndexByTypeDO> listHealthIndexLatest30Days(HealthIndexByTypeDO  healthIndexByTypeDO);

    int updateByPrimaryKeySelective(HealthIndexByTypeDO record);

    int updateByPrimaryKey(HealthIndexByTypeDO record);
}