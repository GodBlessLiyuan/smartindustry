package com.smartindustry.storage.task;

import com.smartindustry.common.constant.SecurityConstant;
import com.smartindustry.common.mapper.sm.StorageHeadMapper;
import com.smartindustry.common.pojo.sm.StorageHeadPO;
import com.smartindustry.common.util.DateUtil;
import com.smartindustry.storage.constant.StorageConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 19:08 2020/10/22
 * @version: 1.0.0
 * @description: 当时间到7:30 或者 到19:30的时候，将备料区入库单的状态更改为入库
 */
@Component
@EnableScheduling
public class StoragePrepareTask {
    private static Logger logger = LoggerFactory.getLogger(StoragePrepareTask.class);
    @Autowired
    private StorageHeadMapper storageHeadMapper;

    @Scheduled(cron = "${schedule.storage.trigger}")
    public void StoragePrepareStatus () {
        logger.info("StoragePrepare time, the current time is : [{}]", DateUtil.date2Str(Calendar.getInstance().getTime(), DateUtil.Y_M_D_T));
        Date date = new Date();
        //将备料入库单的状态更改为已入库
        //首先查询出目前的入库中的备货入库单
        List<StorageHeadPO> pos = storageHeadMapper.queryByStatus(StorageConstant.STATUS_INSTORAGE,StorageConstant.TYPE_PRE_STORAGE);
        for(StorageHeadPO po : pos){
            po.setStatus(StorageConstant.STATUS_STORED);
            po.setStorageTime(date);
        }
    }
}
