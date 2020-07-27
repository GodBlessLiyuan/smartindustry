package com.smartindustry.outbound.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smartindustry.common.mapper.om.OutboundRecordMapper;
import com.smartindustry.common.mapper.om.PickCheckMapper;
import com.smartindustry.common.mapper.om.PickHeadMapper;
import com.smartindustry.common.mapper.si.PrintLabelMapper;
import com.smartindustry.common.pojo.om.OutboundRecordPO;
import com.smartindustry.common.pojo.om.PickCheckPO;
import com.smartindustry.common.pojo.om.PickHeadPO;
import com.smartindustry.common.pojo.si.PrintLabelPO;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.constant.OutboundConstant;
import com.smartindustry.outbound.dto.OperateDTO;
import com.smartindustry.outbound.service.IQualityManageService;
import com.smartindustry.outbound.vo.OutboundRecordVO;
import com.smartindustry.outbound.vo.PickHeadVO;
import com.smartindustry.outbound.vo.PrintLabelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO pickOqcButton(Long pickHeadId){
        //1 更新物料状态
        int result = pickHeadMapper.updateStatus(pickHeadId, OutboundConstant.MATERIAL_STATUS_CHECK);
        //2 新增OQC检测表
        PickCheckPO po = new PickCheckPO();
        po.setPickHeadId(pickHeadId);
        po.setStatus(OutboundConstant.PENDING);
        int resultIn = pickCheckMapper.insert(po);
        outboundRecordMapper.insert(new OutboundRecordPO(pickHeadId,null,1L,"jzj",OutboundConstant.NEW_INSERT,new Date(),OutboundConstant.MATERIAL_STATUS_CHECK));
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
        List<OutboundRecordPO> outs= outboundRecordMapper.queryByPhid(dto.getPhid(),OutboundConstant.MATERIAL_STATUS_CHECK);
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
