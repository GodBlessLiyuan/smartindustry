package com.smartindustry.outbound.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smartindustry.common.bo.om.MaterialBO;
import com.smartindustry.common.bo.om.PickHeadBO;
import com.smartindustry.common.bo.si.PrintLabelBO;
import com.smartindustry.common.mapper.om.LabelRecommendMapper;
import com.smartindustry.common.mapper.om.PickHeadMapper;
import com.smartindustry.common.pojo.om.PickHeadPO;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.vo.LackMaterialVO;
import com.smartindustry.outbound.vo.PickHeadVO;
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
    public ResultVO pageQueryPickHead(int pageNum, int pageSize, Map<String, Object> reqMap) {
        Page<PickHeadPO> page = PageHelper.startPage(pageNum, pageSize);
        List<PickHeadPO> vos = pickHeadMapper.pageQueryPickHeadMsg(reqMap);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), PickHeadVO.convert(vos)));
    }


    @Override
    public ResultVO materialLoss(int pageNum, int pageSize, Map<String, Object> reqMap) {
        Page<MaterialBO> page = PageHelper.startPage(pageNum, pageSize);
        List<MaterialBO> bos = pickHeadMapper.materialLoss(reqMap);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), LackMaterialVO.convert(bos)));
    }

    @Override
    public ResultVO queryExItems(int pageNum,int pageSize,String pickNo){

        List<PickHeadBO> recommedItems = pickHeadMapper.queryRecommend(pickNo);
        List<PickHeadBO> realItems = pickHeadMapper.queryRealPid(pickNo);
        return new ResultVO(1000);
    }


}
