package com.smartindustry.pda.task;

import com.smartindustry.common.mapper.sm.StorageHeadMapper;
import com.smartindustry.common.mapper.sm.StorageRecordMapper;
import com.smartindustry.common.pojo.sm.StorageHeadPO;
import com.smartindustry.common.pojo.sm.StorageRecordPO;
import com.smartindustry.common.util.DateUtil;
import com.smartindustry.pda.constant.StorageConstant;
import com.smartindustry.pda.util.StorageNoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

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
    @Autowired
    private StorageRecordMapper storageRecordMapper;

    @Scheduled(cron = "${schedule.storage.trigger}")
    public void storagePrepareStatus() {
        logger.info("StoragePrepare time, the current time is : [{}]", DateUtil.date2Str(Calendar.getInstance().getTime(), DateUtil.Y_M_D_T));
        Date date = new Date();
        //将备料入库单的状态更改为已入库
        //首先查询出目前的入库中的备货入库单
        List<StorageHeadPO> pos = storageHeadMapper.queryByStatus(StorageConstant.STATUS_STOREING, StorageConstant.TYPE_PRE_STORAGE);
        if (pos.size() > 0) {
            for (StorageHeadPO po : pos) {
                po.setStatus(StorageConstant.STATUS_STORED);
                po.setStorageTime(date);
                storageHeadMapper.updateByPrimaryKey(po);
            }
        }
        //插入入库完成操作记录
        storageRecordMapper.insert(new StorageRecordPO(pos.get(0).getStorageHeadId(), null, StorageConstant.OPERATE_NAME_FINISH));

        //创建新的任务单
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                newStoragebill();
            }
        });
    }

    /**
     * @Description 创建新的入库单
     * @Param
     * @Return
     * @Author AnHongxu.
     * @Date 2020/11/9
     * @Time 18:22
     */
    public void newStoragebill() {
        // 生成备料区入库单表头
        StorageHeadPO storageHeadPO = new StorageHeadPO();
        storageHeadPO.setStorageNo(StorageNoUtil.genStorageHeadNo(storageHeadMapper, StorageNoUtil.RECEIPT_HEAD_YP, new Date()));
        storageHeadPO.setSourceType(StorageConstant.TYPE_PRE_STORAGE);
        storageHeadPO.setStatus(StorageConstant.STATUS_STOREING);
        storageHeadPO.setCreateTime(new Date());
        storageHeadPO.setDr((byte) 1);
        storageHeadMapper.insert(storageHeadPO);
    }

}
