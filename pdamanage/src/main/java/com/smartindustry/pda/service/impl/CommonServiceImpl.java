package com.smartindustry.pda.service.impl;

import com.smartindustry.common.bo.om.OutboundHeadBO;
import com.smartindustry.common.bo.sm.StorageHeadBO;
import com.smartindustry.common.mapper.om.OutboundForkliftMapper;
import com.smartindustry.common.mapper.om.OutboundHeadMapper;
import com.smartindustry.common.mapper.si.ForkliftMapper;
import com.smartindustry.common.mapper.sm.StorageBodyMapper;
import com.smartindustry.common.mapper.sm.StorageHeadMapper;
import com.smartindustry.common.pojo.si.ForkliftPO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.pda.constant.CommonConstant;
import com.smartindustry.pda.constant.StorageConstant;
import com.smartindustry.pda.dto.OutboundDTO;
import com.smartindustry.pda.service.ICommonService;
import com.smartindustry.pda.vo.PdaListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    @Autowired
    private ForkliftMapper forkliftMapper;
    @Autowired
    private StorageHeadMapper storageHeadMapper;
    @Autowired
    private StorageBodyMapper storageBodyMapper;
    @Autowired
    private OutboundHeadMapper outboundHeadMapper;
    @Autowired
    private OutboundForkliftMapper outboundForkliftMapper;

    /**
     * 上线
     *
     * @param session
     * @param dto
     * @return
     */
    @Override
    public ResultVO online(HttpSession session, OutboundDTO dto) {
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
     * @param type    列表类型：1-待执行；2-执行中；3-已执行；4-未完成生产
     * @return
     */
    @Override
    public ResultVO list(HttpSession session, Byte type) {
        if (null == type) {
            return new ResultVO(1001);
        }

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
        PdaListVO vo = new PdaListVO();
        vo.setType(forkliftPO.getWorkArea());

        if (!CommonConstant.TYPE_LIST_UNDONE.equals(type)) {
            // 出库信息
            List<OutboundHeadBO> headBOs = outboundHeadMapper.queryOlistByPdaType(type);
            if (CommonConstant.TYPE_LIST_DOING.equals(type)) {
                // 执行中需要计算当前数量
                List<Long> hids = new ArrayList<>(headBOs.size());
                for (OutboundHeadBO headBO : headBOs) {
                    hids.add(headBO.getOutboundHeadId());
                }
                Map<Long, Integer> fnumMap = outboundForkliftMapper.queryFnumByHids(hids);
                for (OutboundHeadBO headBO : headBOs) {
                    headBO.setExpectNum(headBO.getExpectNum().add(BigDecimal.valueOf(fnumMap.get(headBO.getOutboundHeadId()))));
                }
            }
            vo.setOlist(headBOs);
        }

        // 入库信息
        List<StorageHeadBO> storageHeadBOS = storageHeadMapper.queryPdaByType(type);

        Set<Long> shids = new HashSet<>();
        for (StorageHeadBO storageHeadBO : storageHeadBOS) {
            shids.add(storageHeadBO.getStorageHeadId());
        }
        session.setAttribute(StorageConstant.SESSION_SHIDS, shids);
        vo.setSlist(storageHeadBOS);

        return ResultVO.ok().setData(vo);
    }
}
