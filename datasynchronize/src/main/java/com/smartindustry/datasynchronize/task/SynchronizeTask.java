package com.smartindustry.datasynchronize.task;

import com.smartindustry.datasynchronize.service.IBasicService;
import com.smartindustry.datasynchronize.service.IPurchaseStorageService;
import com.smartindustry.datasynchronize.service.ISalesOutboundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;

/**
 * @author hui.feng
 * @date created in 2020/11/14
 * @description
 */
@Slf4j
@Component
@EnableScheduling
public class SynchronizeTask {

    @Resource
    private IBasicService basicService;

    @Resource
    private IPurchaseStorageService purchaseStorageService;

    @Resource
    private ISalesOutboundService salesOutboundService;

    @Scheduled(cron = "${schedule.data.synchronize}")
    public void dataSynchronize() {
        log.info("start data synchronize for client, the start time is [{}]", Calendar.getInstance().getTime());
        basicService.client();
        log.info("start data synchronize for client, the end time is [{}]", Calendar.getInstance().getTime());
        log.info("start data synchronize for dept, the start time is [{}]", Calendar.getInstance().getTime());
        basicService.dept();
        log.info("start data synchronize for dept, the end time is [{}]", Calendar.getInstance().getTime());
        log.info("start data synchronize for role, the start time is [{}]", Calendar.getInstance().getTime());
        basicService.role();
        log.info("start data synchronize for role, the end time is [{}]", Calendar.getInstance().getTime());
        log.info("start data synchronize for user, the start time is [{}]", Calendar.getInstance().getTime());
        basicService.user();
        log.info("start data synchronize for user, the end time is [{}]", Calendar.getInstance().getTime());
        log.info("start data synchronize for supplier, the start time is [{}]", Calendar.getInstance().getTime());
        basicService.supplier();
        log.info("start data synchronize for supplier, the end time is [{}]", Calendar.getInstance().getTime());
        log.info("start data synchronize for material, the start time is [{}]", Calendar.getInstance().getTime());
        basicService.material();
        log.info("start data synchronize for material, the end time is [{}]", Calendar.getInstance().getTime());
        log.info("start data synchronize for purchase, the start time is [{}]", Calendar.getInstance().getTime());
        purchaseStorageService.sync();
        log.info("start data synchronize for purchase, the end time is [{}]", Calendar.getInstance().getTime());
        log.info("start data synchronize for salesoutbound, the start time is [{}]", Calendar.getInstance().getTime());
        salesOutboundService.sync();
        log.info("start data synchronize for salesoutbound, the end time is [{}]", Calendar.getInstance().getTime());

    }

}
