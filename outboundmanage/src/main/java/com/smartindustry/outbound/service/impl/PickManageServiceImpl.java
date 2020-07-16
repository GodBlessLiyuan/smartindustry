package com.smartindustry.outbound.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smartindustry.common.bo.om.PrintLabelBO;
import com.smartindustry.common.mapper.om.LabelRecommendMapper;
import com.smartindustry.common.mapper.om.PickHeadMapper;
import com.smartindustry.common.pojo.om.PickHeadPO;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.common.vo.om.PickHeadVO;
import com.smartindustry.common.vo.om.ScanPickVO;
import com.smartindustry.outbound.service.IPickManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/7/14 19:02
 * @description: 拣货单管理
 * @version: 1.0
 */
@Service
public class PickManageServiceImpl implements IPickManageService {
    @Autowired
    private PickHeadMapper pickHeadMapper;

    @Autowired
    private LabelRecommendMapper labelRecommendMapper;

    @Override
    public ResultVO pageQueryPickHead(int pageNum, int pageSize, Map<String, Object> reqMap){
        Page<PickHeadPO> page = PageHelper.startPage(pageNum, pageSize);

        List<PickHeadPO> vos =  pickHeadMapper.pageQueryPickHeadMsg(reqMap);
        return new ResultVO(1000,new PageInfoVO<>(page.getTotal(), PickHeadVO.convert(vos)));
    }

    @Override
    public ResultVO scanPick(int pageNum,int pageSize,String pickNo){
        Page<PrintLabelBO> page = PageHelper.startPage(pageNum, pageSize);
        List<PrintLabelBO> bos =  pickHeadMapper.scanLabelByPickNo(pickNo);
        return new ResultVO(1000,new PageInfoVO<>(page.getTotal(), ScanPickVO.convert(bos)));
    }

    @Override
    public ResultVO materialLoss(int pageNum,int pageSize,String pickNo){
//        Page<MaterialLossBO> page = PageHelper.startPage(pageNum, pageSize);
//        List<MaterialLossBO> bos =  pickHeadMapper.materialLoss(pickNo);
        return new ResultVO(1000);
    }
}
