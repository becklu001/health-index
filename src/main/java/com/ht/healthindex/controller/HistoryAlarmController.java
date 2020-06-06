package com.ht.healthindex.controller;

import com.ht.healthindex.dataobject.HistoryAlarmDO;
import com.ht.healthindex.response.CommonResult;
import com.ht.healthindex.service.HistoryAlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/historyAlarm")
public class HistoryAlarmController {
    @Autowired
    private HistoryAlarmService historyAlarmService;

    @GetMapping("/list")
    public CommonResult listByAlarmDevice(){
        List<HistoryAlarmDO> alarmDOList = historyAlarmService.listByAlarmDeviceAndStationId("2DG",1);
        return CommonResult.create(alarmDOList);
    }
}
