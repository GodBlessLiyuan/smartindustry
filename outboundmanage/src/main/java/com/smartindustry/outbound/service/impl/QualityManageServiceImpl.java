package com.smartindustry.outbound.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smartindustry.common.bo.am.LoginUserBO;
import com.smartindustry.common.constant.ConfigConstant;
import com.smartindustry.common.mapper.om.OutboundMapper;
import com.smartindustry.common.mapper.om.OutboundRecordMapper;
import com.smartindustry.common.mapper.om.PickCheckMapper;
import com.smartindustry.common.mapper.om.PickHeadMapper;
import com.smartindustry.common.mapper.si.ConfigMapper;
import com.smartindustry.common.mapper.si.PrintLabelMapper;
import com.smartindustry.common.pojo.am.UserPO;
import com.smartindustry.common.pojo.om.OutboundPO;
import com.smartindustry.common.pojo.om.OutboundRecordPO;
import com.smartindustry.common.pojo.om.PickCheckPO;
import com.smartindustry.common.pojo.om.PickHeadPO;
import com.smartindustry.common.pojo.si.ConfigPO;
import com.smartindustry.common.pojo.si.PrintLabelPO;
import com.smartindustry.common.util.ServletUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.constant.OutboundConstant;
import com.smartindustry.outbound.dto.OperateDTO;
import com.smartindustry.outbound.service.IQualityManageService;
import com.smartindustry.outbound.util.OmNoUtil;
import com.smartindustry.outbound.vo.OutboundRecordVO;
import com.smartindustry.outbound.vo.PickHeadVO;
import com.smartindustry.outbound.vo.PrintLabelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/7/14 19:03
 * @description: 质量管理
 * @version: 1.0
 */
@EnableTransactionManagement
@Service
public class QualityManageServiceImpl implements IQualityManageService {
    @Autowired
    private PickHeadMapper pickHeadMapper;
    @Autowired
    private PickCheckMapper pickCheckMapper;
    @Autowired
    private OutboundRecordMapper outboundRecordMapper;
    @Autowired
    private PrintLabelMapper printLabelMapper;
    @Autowired
    private ConfigMapper configMapper;
    @Autowired
    private OutboundMapper outboundMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO pickOqcButton(Long pickHeadId){
        UserPO user = ServletUtil.getUserBO().getUser();
        ConfigPO configPo = configMapper.queryByKey(ConfigConstant.K_OUTBOUND_QUALITY_KEY);
        if (null != configPo && ConfigConstant.V_NO.equals(configPo.getConfigValue())) {
            pickHeadMapper.updateStatus(pickHeadId, OutboundConstant.MATERIAL_STATUS_STORAGE);
            OutboundPO po = new OutboundPO();
            po.setPickHeadId(pickHeadId);
            Date date = new Date();
            po.setOutboundNo(OmNoUtil.getOutboundNo(outboundMapper, OmNoUtil.OUTBOUND, date));
            po.setStatus(OutboundConstant.OUTBOUND_STATUS_WAIT);
            po.setCreateTime(date);
            po.setDr((byte) 1);
            outboundMapper.insert(po);
            outboundRecordMapper.insert(new OutboundRecordPO(pickHeadId, null, user.getUserId(), user.getName(), OutboundConstant.RECORD_AGREE, OutboundConstant.MATERIAL_STATUS_CHECK));
            outboundRecordMapper.insert(new OutboundRecordPO(pickHeadId, po.getOutboundId(), user.getUserId(), user.getName(), OutboundConstant.RECORD_ADD, OutboundConstant.MATERIAL_STATUS_STORAGE));
        } else{
            //1 更新物料状态
            pickHeadMapper.updateStatus(pickHeadId, OutboundConstant.MATERIAL_STATUS_CHECK);
            //2 新增OQC检测表
            PickCheckPO po = new PickCheckPO();
            po.setPickHeadId(pickHeadId);
            po.setStatus(OutboundConstant.PENDING);
            pickCheckMapper.insert(po);
            outboundRecordMapper.insert(new OutboundRecordPO(pickHeadId,null,user.getUserId(), user.getName(),OutboundConstant.RECORD_OQC,OutboundConstant.MATERIAL_STATUS_PICK));
            outboundRecordMapper.insert(new OutboundRecordPO(pickHeadId,null,user.getUserId(), user.getName(),OutboundConstant.RECORD_ADD,OutboundConstant.MATERIAL_STATUS_CHECK));
        }
        return ResultVO.ok();
    }


    @Override
    public ResultVO queryOqc(int pageNum, int pageSize, String pickNo, String orderNo){
        Page<PickHeadPO> page = PageHelper.startPage(pageNum, pageSize);
        List<PickHeadPO> pos = pickHeadMapper.queryOqc(pickNo,orderNo);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), PickHeadVO.convert(pos)));
    }

    @Override
    public ResultVO queryRecordMsg(OperateDTO dto){
        List<OutboundRecordPO> outs= outboundRecordMapper.queryByPhid(dto.getPhid(),dto.getStatus());
        List<PrintLabelPO> prints = pickHeadMapper.queryByPhidItems(dto.getPhid());
        Map<String,Object> listMap = new HashMap<String,Object>(){
            {
                put("record",null);
                put("print",null);
            }
        };
        if(null != outs && outs.size() !=0){
            listMap.put("record",OutboundRecordVO.convert(outs));
        }
        if(null != prints && prints.size() !=0){
            listMap.put("print",PrintLabelVO.convert(prints));
        }
        return ResultVO.ok().setData(listMap);
    }
}
