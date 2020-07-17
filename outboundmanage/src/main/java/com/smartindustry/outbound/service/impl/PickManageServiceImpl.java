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
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/7/14 19:02
 * @description: 拣货单管理
 * @version: 1.0
 */
@EnableTransactionManagement
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
        // 先查询出当前工单号下 物料编码

        // 当前物料的需求数未满时，若扫描了未推荐的PID,则异常列表只显示，扫描了其他推荐的PID

        // 若已拣货量大于需求量时，将未扫描优先推荐的pid以及扫描了其他推荐的pid


        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO pickPidOut(String pickNo,String packageId){
        //1.首先根据输入的PID,得到相应PID的信息，进行展示
        PrintLabelBO bo = pickHeadMapper.pickPid(packageId);
        PickHeadPO po = pickHeadMapper.queryByPickNo(pickNo);
        // 如果该物料的PID不在该工单对应采购单的物料范围内，则提示 该物料并不属于该工单
//        List<String> pidList = pickHeadMapper.judgePidHave(pickNo);

        // 判断当前物料不在拣货清单中，则提示 该物料并不在出库清单中
        List<String> maList = pickHeadMapper.judgeMaterial(pickNo);
        boolean flag = maList.contains(bo.getMaterialNo());
        //2.将拣货单表体表中的已拣量作加操作
        int addResult = pickHeadMapper.addPickNum(bo.getMaterialNo(),bo.getNum());
        //3.查看扫码的PID是否在推荐的库位标签表中是否存在推荐的PID,存在则更新拣货标签表中的是否推荐标志位
        List<String> ReList = pickHeadMapper.queryRecommend(pickNo);
        boolean flagOne = ReList.contains(packageId);
        int recommend = flagOne?1:0;
        int insertResult = pickHeadMapper.insertPickLabel(po.getPickHeadId(),bo.getPrintLabelId(),recommend);

        return ResultVO.ok().setData(bo);
    }


}
