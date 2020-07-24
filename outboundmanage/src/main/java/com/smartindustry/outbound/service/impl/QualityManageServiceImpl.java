package com.smartindustry.outbound.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smartindustry.common.mapper.om.PickCheckMapper;
import com.smartindustry.common.mapper.om.PickHeadMapper;
import com.smartindustry.common.pojo.om.PickCheckPO;
import com.smartindustry.common.pojo.om.PickHeadPO;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.constant.OutboundConstant;
import com.smartindustry.outbound.service.IQualityManageService;
import com.smartindustry.outbound.vo.PickHeadVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO pickOqcButton(Long pickHeadId){
        //1 更新物料状态
        int result = pickHeadMapper.updateStatus(pickHeadId, OutboundConstant.MATERIAL_STATUS_CHECK);
        //2 新增OQC检测表
        PickCheckPO po = new PickCheckPO();
        po.setPickHeadId(pickHeadId);
        po.setStatus((byte)3);
        int resultIn = pickCheckMapper.insert(po);
        return ResultVO.ok();
    }


    @Override
    public ResultVO queryOqc(int pageNum, int pageSize, String pickNo, String orderNo){
        Page<PickHeadPO> page = PageHelper.startPage(pageNum, pageSize);
        List<PickHeadPO> pos = pickHeadMapper.queryOqc(pickNo,orderNo);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), PickHeadVO.convert(pos)));
    }
}
