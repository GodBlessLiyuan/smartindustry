package com.smartindustry.pda.service.impl;

import com.smartindustry.common.bo.om.OutboundForkliftBO;
import com.smartindustry.common.bo.om.OutboundHeadBO;
import com.smartindustry.common.bo.si.LocationBO;
import com.smartindustry.common.bo.sm.StorageHeadBO;
import com.smartindustry.common.mapper.om.OutboundBodyMapper;
import com.smartindustry.common.mapper.om.OutboundForkliftMapper;
import com.smartindustry.common.mapper.om.OutboundHeadMapper;
import com.smartindustry.common.mapper.si.ForkliftMapper;
import com.smartindustry.common.mapper.si.LocationMapper;
import com.smartindustry.common.mapper.sm.StorageDetailMapper;
import com.smartindustry.common.mapper.sm.StorageForkliftMapper;
import com.smartindustry.common.mapper.sm.StorageHeadMapper;
import com.smartindustry.common.pojo.om.OutboundBodyPO;
import com.smartindustry.common.pojo.om.OutboundForkliftPO;
import com.smartindustry.common.pojo.om.OutboundHeadPO;
import com.smartindustry.common.pojo.si.ForkliftPO;
import com.smartindustry.common.pojo.si.LocationPO;
import com.smartindustry.common.pojo.sm.StorageDetailPO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.pda.constant.CommonConstant;
import com.smartindustry.pda.dto.CommonDTO;
import com.smartindustry.pda.service.ICommonService;
import com.smartindustry.pda.service.IOutboundService;
import com.smartindustry.pda.service.IStorageService;
import com.smartindustry.pda.socket.WebSocketServer;
import com.smartindustry.pda.vo.PdaListVO;
import com.smartindustry.pda.socket.WebSocketVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author: xiahui
 * @date: Created in 2020/11/4 18:32
 * @description: 公共模块
 * @version: 1.0
 */
@Service
public class CommonServiceImpl implements ICommonService {
    private static final Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);

    @Autowired
    private LocationMapper locationMapper;
    @Autowired
    private ForkliftMapper forkliftMapper;
    @Autowired
    private OutboundHeadMapper outboundHeadMapper;
    @Autowired
    private OutboundBodyMapper outboundBodyMapper;
    @Autowired
    private OutboundForkliftMapper outboundForkliftMapper;
    @Autowired
    private StorageHeadMapper storageHeadMapper;
    @Autowired
    private StorageDetailMapper storageDetailMapper;
    @Autowired
    private StorageForkliftMapper storageForkliftMapper;
    @Autowired
    private IStorageService storageService;

    /**
     * 备料区 RFID
     */
    private static String prepareRFID;

    @PostConstruct
    public void initPrepareMaterialNo() {
        List<LocationPO> locationPOs = locationMapper.queryByLtid((long) 1);
        if (null == locationPOs || locationPOs.size() == 0) {
            prepareRFID = "";
        } else {
            prepareRFID = locationPOs.get(0).getLocationNo();
        }
    }

    /**
     * 上线
     *
     * @param session
     * @param dto
     * @return
     */
    @Override
    public ResultVO online(HttpSession session, CommonDTO dto) {
        if (StringUtils.isEmpty(dto.getImei())) {
            return new ResultVO(1001);
        }

        // 叉车信息
        ForkliftPO forkliftPO = forkliftMapper.queryByImei(dto.getImei());
        if (null == forkliftPO) {
            return new ResultVO(1002);
        }

        session.setAttribute(CommonConstant.SESSION_IMEI, dto.getImei());
        session.setMaxInactiveInterval(30 * 24 * 60 * 60);

        return ResultVO.ok().setData(forkliftPO.getForkliftName());
    }

    /**
     * 出入库列表区域
     *
     * @param session
     * @param dto     列表类型：1-待执行；2-执行中；3-已执行；4-未完成生产
     * @return
     */
    @Override
    public ResultVO list(HttpSession session, CommonDTO dto) {
        if (null == dto.getType()) {
            return new ResultVO(1001);
        }
        //String imei = "868374034386920";
        String imei = (String) session.getAttribute(CommonConstant.SESSION_IMEI);
        if (null == imei) {
            return new ResultVO(1111);
        }

        // 叉车信息
        ForkliftPO forkliftPO = forkliftMapper.queryByImei(imei);
        if (null == forkliftPO) {
            return new ResultVO(1002);
        }

        // 列表信息
        List<OutboundHeadBO> ohBOs = new ArrayList<>();
        if (!CommonConstant.TYPE_LIST_UNDONE.equals(dto.getType())) {
            // 出库信息
            ohBOs = outboundHeadMapper.queryOlistByPdaType(dto.getType());
            if (CommonConstant.TYPE_LIST_DOING.equals(dto.getType())) {
                // 执行中需要计算当前数量
                List<Long> hids = new ArrayList<>(ohBOs.size());
                for (OutboundHeadBO headBO : ohBOs) {
                    hids.add(headBO.getOutboundHeadId());
                }
                if (hids.size() != 0) {
                    Map<Long, Integer> fnumMap = outboundForkliftMapper.queryFnumByHids(hids);
                    for (OutboundHeadBO headBO : ohBOs) {
                        headBO.setExpectNum(headBO.getExpectNum().add(BigDecimal.valueOf(fnumMap.getOrDefault(headBO.getOutboundHeadId(), 0))));
                    }
                }
            }
        }

        // 入库信息
        List<StorageHeadBO> shBOs = storageHeadMapper.queryPdaByType(dto.getType());
        if (CommonConstant.TYPE_LIST_DOING.equals(dto.getType())) {
            // 执行中需要计算当前数量
            List<Long> sids = new ArrayList<>(shBOs.size());
            for (StorageHeadBO headBO : shBOs) {
                sids.add(headBO.getStorageHeadId());
            }
            if (sids.size() != 0) {
                Map<Long, Integer> fnumMap = storageForkliftMapper.queryFnumBySids(sids);
                for (StorageHeadBO headBO : shBOs) {
                    BigDecimal storageNum = headBO.getStorageNum() == null ? BigDecimal.valueOf(0) : headBO.getStorageNum();
                    if (fnumMap == null) {
                        headBO.setStorageNum(storageNum);
                    } else {
                        headBO.setStorageNum(storageNum.add(BigDecimal.valueOf(fnumMap.getOrDefault(headBO.getStorageHeadId(), 0))));
                    }
                }
            }
        }

        return ResultVO.ok().setData(PdaListVO.convert(forkliftPO.getWorkArea() == 2, shBOs, ohBOs));
    }

    /**
     * 数采 RFID
     *
     * @param session
     * @param dto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO rfid(HttpSession session, CommonDTO dto) {
        // 当前叉车信息
        String imei = (String) session.getAttribute(CommonConstant.SESSION_IMEI);
        if (null == imei) {
            return new ResultVO(1111);
        }

        Byte status = this.checkRfids(session, dto);
        if (CommonConstant.RFID_INVALID.equals(status)) {
            return ResultVO.ok();
        }

        logger.info("当前RFID捕捉状态值：{}", status);

        ForkliftPO fPO = forkliftMapper.queryByImei(imei);
        if (null == fPO) {
            return new ResultVO(1002);
        }
        if (CommonConstant.RFID_STORAGE_START_RAW.equals(status)) {
            // 入库开始（原材料区）
            storageService.execute(session, dto.getMrfid());
            return ResultVO.ok().setData("入库开始（原材料区）");
        }
        if (CommonConstant.RFID_STORAGE_START_PREPARE.equals(status)) {
            // 入库开始(备料区）
            storageService.executeForPre(session, dto.getMrfid());
            return ResultVO.ok().setData("入库开始(备料区）");
        }
        if (CommonConstant.RFID_STORAGE_END_RAW_PRODUCT.equals(status)) {
            // 入库完成(原材料入成品区）
            storageService.finishedOriginToStorage(session, (String) session.getAttribute(CommonConstant.SESSION_MRFID), dto.getLrfid());
            session.removeAttribute(CommonConstant.SESSION_MRFID);
            return ResultVO.ok().setData("入库完成(原材料入成品区）");
        }
        if (CommonConstant.RFID_STORAGE_END_RAW_PREPARE.equals(status)) {
            // 入库完成(原材料入备料区）
            storageService.finishedOriginToSpareArea(session, (String) session.getAttribute(CommonConstant.SESSION_MRFID), dto.getLrfid());
            session.removeAttribute(CommonConstant.SESSION_MRFID);
            return ResultVO.ok().setData("入库完成(原材料入备料区）");
        }
        if (CommonConstant.RFID_STORAGE_END_PREPARE_PRODUCT.equals(status)) {
            // 入库完成(备料入成品区）
            storageService.finishedSpareAreaToStorage(session, (String) session.getAttribute(CommonConstant.SESSION_MRFID), dto.getLrfid());
            session.removeAttribute(CommonConstant.SESSION_MRFID);
            return ResultVO.ok().setData("入库完成(备料入成品区）");
        }
        if (CommonConstant.RFID_STORAGE_MEET_PREPARE.equals(status)) {
            // 检测到备料区
            storageService.chooseMaterialShow((String) session.getAttribute(CommonConstant.SESSION_MRFID));
            session.removeAttribute(CommonConstant.SESSION_MRFID);
            return ResultVO.ok().setData("检测到备料区");
        }

        OutboundForkliftPO outboundForkliftPO = outboundForkliftMapper.queryByFid(fPO.getForkliftId());
        if (CommonConstant.FORKLIFT_WORK_OUTBOUND_START.equals(status)) {
            // 出库，叉起物料
            outboundForkliftPO.setRfid(dto.getMrfid());
            outboundForkliftMapper.updateByPrimaryKey(outboundForkliftPO);

            StorageDetailPO detailPO = storageDetailMapper.queryByRfidAndStatus(dto.getMrfid(), CommonConstant.STATUS_RFID_STORAGE);
            detailPO.setStorageStatus(CommonConstant.STATUS_RFID_STORAGE);
            storageDetailMapper.updateByPrimaryKey(detailPO);

            return ResultVO.ok().setData("出库，叉起物料");
        }
        if (CommonConstant.RFID_OUTBOUND_RETURN.equals(status)) {
            // 出库，物料放回原位
            outboundForkliftPO.setRfid(null);
            outboundForkliftMapper.updateByPrimaryKey(outboundForkliftPO);

            StorageDetailPO detailPO = storageDetailMapper.queryByRfidAndStatus((String) session.getAttribute(CommonConstant.SESSION_MRFID), CommonConstant.STATUS_RFID_OUTBOUND_SALE);
            detailPO.setStorageStatus(CommonConstant.STATUS_RFID_STORAGE);
            storageDetailMapper.updateByPrimaryKey(detailPO);

            session.removeAttribute(CommonConstant.SESSION_MRFID);

            return ResultVO.ok().setData("出库，物料放回原位");
        }
        if (CommonConstant.RFID_OUTBOUND_END.equals(status)) {
            // 出库
            outboundForkliftPO.setRfid(null);
            outboundForkliftMapper.updateByPrimaryKey(outboundForkliftPO);

            StorageDetailPO detailPO = storageDetailMapper.queryByRfidAndStatus((String) session.getAttribute(CommonConstant.SESSION_MRFID), CommonConstant.STATUS_RFID_OUTBOUND_SALE);
            detailPO.setStorageStatus(CommonConstant.STATUS_RFID_OUTBOUND_DONE);
            storageDetailMapper.updateByPrimaryKey(detailPO);

            OutboundHeadPO headPO = outboundHeadMapper.selectByPrimaryKey(outboundForkliftPO.getOutboundHeadId());
            headPO.setOutboundNum(headPO.getOutboundNum().add(BigDecimal.ONE));
            if (headPO.getExpectNum().equals(headPO.getOutboundNum())) {
                // 出库完成
                headPO.setStatus((byte) 1);
                headPO.setOutboundTime(new Date());
                // 发送消息
                List<OutboundForkliftBO> ofBOs = outboundForkliftMapper.queryByOhid(headPO.getOutboundHeadId());
                List<String> imeis = new ArrayList<>();
                for (OutboundForkliftBO ofBO : ofBOs) {
                    if (!imei.equals(ofBO.getImeiNo())) {
                        imeis.add(ofBO.getImeiNo());
                    }
                }

                // websocket
                WebSocketVO vo = WebSocketVO.createTitleVO("业务单号：" + headPO.getSourceNo() + "（销售出库），已完成作业任务，任务关闭", CommonConstant.TYPE_TITLE_INTO);
                WebSocketServer.sendMsg(imeis, vo);
            }

            outboundHeadMapper.updateByPrimaryKey(headPO);

            OutboundBodyPO bodyPO = outboundBodyMapper.queryByOhidAndMid(outboundForkliftPO.getOutboundHeadId(), detailPO.getMaterialId());
            bodyPO.setOutboundNum(bodyPO.getOutboundNum().add(BigDecimal.ONE));
            if (bodyPO.getExpectNum().equals(bodyPO.getOutboundNum())) {
                bodyPO.setOutboundTime(new Date());
            }
            outboundBodyMapper.updateByPrimaryKey(bodyPO);

            // 储位数量 -1
            detailPO = storageDetailMapper.queryByRfid((String) session.getAttribute(CommonConstant.SESSION_MRFID));
            LocationPO locationPO = locationMapper.selectByPrimaryKey(detailPO.getLocationId());
            locationPO.setExistNum(locationPO.getExistNum().subtract(BigDecimal.ONE));
            locationMapper.updateByPrimaryKey(locationPO);

            session.removeAttribute(CommonConstant.SESSION_MRFID);
            return ResultVO.ok().setData("出库");
        }

        return ResultVO.ok();
    }

    /**
     * 数采预处理
     *
     * @param session
     * @param dto
     * @return
     */
    private Byte checkRfids(HttpSession session, CommonDTO dto) {
        String imei = (String) session.getAttribute(CommonConstant.SESSION_IMEI);
        if ("ERROR".equals(dto.getMrfid()) || "ERROR".equals(dto.getLrfid())) {
            WebSocketServer.sendMsg(imei, WebSocketVO.createTitleVO("当前阅读器或串口线故障，请检查！", CommonConstant.TYPE_TITLE_ERROR));
            return CommonConstant.RFID_INVALID;
        }

        logger.info("  ");
        logger.info("{}-{}", dto.getMrfid(), dto.getLrfid());
        logger.info("{}-{}", session.getAttribute(CommonConstant.SESSION_MRFID), session.getAttribute(CommonConstant.SESSION_LRFID));

        Byte status = (Byte) session.getAttribute(CommonConstant.SESSION_STATUS_FORKLIFT);
        if (null == status) {
            // 无状态
            if (null == dto.getMrfid()) {
                return CommonConstant.RFID_INVALID;
            }

            StorageDetailPO detailPO = storageDetailMapper.queryByRfidAndStatus(dto.getMrfid(), CommonConstant.STATUS_RFID_STORAGE);
            if (null == detailPO) {
                // 原材料区入库
                session.setAttribute(CommonConstant.SESSION_STATUS_FORKLIFT, CommonConstant.FORKLIFT_STORAGE_START_RAW);
                session.setAttribute(CommonConstant.SESSION_MRFID, dto.getMrfid());
                session.removeAttribute(CommonConstant.SESSION_LRFID);

                return CommonConstant.RFID_STORAGE_START_RAW;
            }

            if (detailPO.getPreparation() == 2) {
                // 备料区入库
                session.setAttribute(CommonConstant.SESSION_STATUS_FORKLIFT, CommonConstant.FORKLIFT_STORAGE_START_PREPARE);
                session.setAttribute(CommonConstant.SESSION_MRFID, dto.getMrfid());
                session.removeAttribute(CommonConstant.SESSION_LRFID);

                return CommonConstant.RFID_STORAGE_START_PREPARE;
            }

            // 出库
            WebSocketServer.sendMsg(imei, WebSocketVO.createTitleVO("该物料无法识别，请停止操作！", CommonConstant.TYPE_TITLE_WARN));
            return CommonConstant.RFID_INVALID;
        }
        if (CommonConstant.FORKLIFT_STORAGE_START_RAW.equals(status)) {
            // 原材料入库
            if (null != dto.getMrfid()) {
                return CommonConstant.RFID_INVALID;
            }

            String lrfid = (String) session.getAttribute(CommonConstant.SESSION_LRFID);
            if (null == lrfid) {
                if (null == dto.getLrfid()) {
                    WebSocketServer.sendMsg(imei, WebSocketVO.createTitleVO("作业错误，成品摆放错误或丢失，请立即处理！", CommonConstant.TYPE_TITLE_WARN));
                    return CommonConstant.RFID_INVALID;
                }
                if (!prepareRFID.equals(dto.getLrfid())) {
                    LocationBO locationBO = locationMapper.queryByLrfidAndMrfid(dto.getLrfid(), dto.getMrfid());
                    if (null == locationBO) {
                        WebSocketServer.sendMsg(imei, WebSocketVO.createTitleVO("作业错误，成品摆放错误或丢失，请立即处理！", CommonConstant.TYPE_TITLE_WARN));
                        return CommonConstant.RFID_INVALID;
                    }
                }

                session.setAttribute(CommonConstant.SESSION_LRFID, dto.getLrfid());
                return CommonConstant.RFID_INVALID;
            }
            if (!lrfid.equals(dto.getLrfid())) {
                session.removeAttribute(CommonConstant.SESSION_STATUS_FORKLIFT);
                return prepareRFID.equals(lrfid) ? CommonConstant.RFID_STORAGE_END_RAW_PREPARE : CommonConstant.RFID_STORAGE_END_RAW_PRODUCT;
            }


            if (prepareRFID.equals(dto.getLrfid())) {
                // 途径备料区
                return CommonConstant.RFID_STORAGE_MEET_PREPARE;
            }

            return CommonConstant.RFID_INVALID;
        }
        if (CommonConstant.FORKLIFT_STORAGE_START_PREPARE.equals(status)) {
            // 备料区入库
            if (null != dto.getMrfid()) {
                return CommonConstant.RFID_INVALID;
            }

            String lrfid = (String) session.getAttribute(CommonConstant.SESSION_LRFID);
            if (null == lrfid) {
                if (null == dto.getLrfid() || prepareRFID.equals(dto.getLrfid())) {
                    WebSocketServer.sendMsg(imei, WebSocketVO.createTitleVO("作业错误，成品摆放错误或丢失，请立即处理！", CommonConstant.TYPE_TITLE_WARN));
                    return CommonConstant.RFID_INVALID;
                }

                session.setAttribute(CommonConstant.SESSION_LRFID, dto.getLrfid());
                return CommonConstant.RFID_INVALID;
            }

            if (!lrfid.equals(dto.getLrfid())) {
                session.removeAttribute(CommonConstant.SESSION_STATUS_FORKLIFT);
                return CommonConstant.RFID_STORAGE_END_PREPARE_PRODUCT;
            }

            return CommonConstant.RFID_INVALID;

        }
        if (CommonConstant.FORKLIFT_WORK_OUTBOUND_START.equals(status)) {
            // 出库 - 叉起货物
            if (null == dto.getMrfid()) {
                return CommonConstant.RFID_INVALID;
            }

            StorageDetailPO detailPO = storageDetailMapper.queryByRfidAndStatus(dto.getMrfid(), CommonConstant.STATUS_RFID_STORAGE);
            if (null == detailPO || detailPO.getPreparation() == 2) {
                WebSocketServer.sendMsg(imei, WebSocketVO.createTitleVO("作业错误，该栈板不在出库作业范围内，请查看可出库储位提示", CommonConstant.TYPE_TITLE_WARN));
                return CommonConstant.RFID_INVALID;
            }

            OutboundBodyPO bodyPO = outboundBodyMapper.queryByOhidAndMid((Long) session.getAttribute(CommonConstant.SESSION_OHID), detailPO.getMaterialId());
            if (null == bodyPO || bodyPO.getExpectNum().equals(bodyPO.getOutboundNum())) {
                WebSocketServer.sendMsg(imei, WebSocketVO.createTitleVO("作业错误，该栈板不在出库作业范围内，请查看可出库储位提示", CommonConstant.TYPE_TITLE_WARN));
                return CommonConstant.RFID_INVALID;
            }

            session.setAttribute(CommonConstant.SESSION_STATUS_FORKLIFT, CommonConstant.FORKLIFT_WORK_OUTBOUND_END);
            session.setAttribute(CommonConstant.SESSION_MRFID, dto.getMrfid());

            return CommonConstant.RFID_OUTBOUND_START;
        }
        if (CommonConstant.FORKLIFT_WORK_OUTBOUND_END.equals(status)) {
            // 出库
            if (null != dto.getMrfid()) {
                return CommonConstant.RFID_INVALID;
            }

            if (null != dto.getLrfid()) {
                StorageDetailPO detailPO = storageDetailMapper.queryByRfidAndStatus((String) session.getAttribute(CommonConstant.SESSION_MRFID), CommonConstant.STATUS_RFID_OUTBOUND_SALE);
                LocationPO locationPO = locationMapper.selectByPrimaryKey(detailPO.getLocationId());
                if (dto.getLrfid().equals(locationPO.getLocationNo())) {
                    // 物料放回原位
                    session.setAttribute(CommonConstant.SESSION_STATUS_FORKLIFT, CommonConstant.FORKLIFT_WORK_OUTBOUND_START);
                    return CommonConstant.RFID_OUTBOUND_RETURN;
                }

                WebSocketServer.sendMsg(imei, WebSocketVO.createTitleVO("作业错误，成品出库错误，请立即处理", CommonConstant.TYPE_TITLE_WARN));
                return CommonConstant.RFID_INVALID;
            }

            session.setAttribute(CommonConstant.SESSION_STATUS_FORKLIFT, CommonConstant.FORKLIFT_WORK_OUTBOUND_START);
            return CommonConstant.RFID_OUTBOUND_END;
        }

        return CommonConstant.RFID_INVALID;
    }
}
