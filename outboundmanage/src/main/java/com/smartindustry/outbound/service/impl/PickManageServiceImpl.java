package com.smartindustry.outbound.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smartindustry.common.bo.om.LabelRecommendBO;
import com.smartindustry.common.bo.om.MaterialBO;
import com.smartindustry.common.bo.om.PickBodyBO;
import com.smartindustry.common.bo.om.PickHeadBO;
import com.smartindustry.common.bo.si.PrintLabelBO;
import com.smartindustry.common.mapper.om.LabelRecommendMapper;
import com.smartindustry.common.mapper.om.PickBodyMapper;
import com.smartindustry.common.mapper.om.PickHeadMapper;
import com.smartindustry.common.mapper.si.StorageLabelMapper;
import com.smartindustry.common.pojo.om.LabelRecommendPO;
import com.smartindustry.common.pojo.om.PickBodyPO;
import com.smartindustry.common.pojo.om.PickHeadPO;
import com.smartindustry.common.pojo.si.StorageLabelPO;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.vo.LackMaterialVO;
import com.smartindustry.outbound.vo.PickBodyVO;
import com.smartindustry.outbound.vo.PickDetailVO;
import com.smartindustry.outbound.vo.PickHeadVO;
import com.smartindustry.outbound.service.IPickManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    private PickBodyMapper pickBodyMapper;
    @Autowired
    private LabelRecommendMapper labelRecommendMapper;
    @Autowired
    private StorageLabelMapper storageLabelMapper;

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
    public ResultVO queryExItems(int pageNum, int pageSize, String pickNo) {

//        List<PickHeadBO> recommedItems = pickHeadMapper.queryRecommend(pickNo);
//        List<PickHeadBO> realItems = pickHeadMapper.queryRealPid(pickNo);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO detail(Long phId) {
        PickHeadPO headPO = pickHeadMapper.selectByPrimaryKey(phId);
        if (null == headPO) {
            return new ResultVO(1002);
        }

        PickDetailVO vo = new PickDetailVO();
        vo.setPickHeadVO(headPO);

        List<PickBodyBO> bodyBOs = pickBodyMapper.queryByHeadId(phId);
        List<PickDetailVO.PickItemVO> itemVOs = new ArrayList<>(bodyBOs.size());
        for (PickBodyBO bo : bodyBOs) {
            PickDetailVO.PickItemVO itemVO = new PickDetailVO.PickItemVO();
            itemVO.setBody(PickBodyVO.convert(bo));
            // 优先推荐
            List<LabelRecommendBO> labelRecommendBOs = labelRecommendMapper.queryByPbid(bo.getPickBodyId());
            Map<String, String> recommendVO = new HashMap<>();
            for (LabelRecommendBO recommendBO : labelRecommendBOs) {
                String key = recommendBO.getLocationNo();
                if (recommendVO.containsKey(key)) {
                    recommendVO.put(key, recommendVO.get(key) + "," + recommendBO.getPackageId());
                } else {
                    recommendVO.put(key, recommendBO.getPackageId());
                }
            }
            List<String> recommendVOs = new ArrayList<>();
            for (Map.Entry<String, String> entry : recommendVO.entrySet()) {
                recommendVOs.add(entry.getKey() + " " + entry.getValue());
            }
            itemVO.setRecommend(recommendVOs);
            // 其他库位
            List<StorageLabelPO> notRecommendPOs = storageLabelMapper.queryNotRecommend(headPO.getOrderNo(), bo.getMaterialNo());
            Set<String> notRecommendVO = new HashSet<>();
            for (StorageLabelPO notRecommendPO : notRecommendPOs) {
                notRecommendVO.add(notRecommendPO.getLocationNo());
            }
            itemVO.setOther(new ArrayList<>(notRecommendVO));
            itemVOs.add(itemVO);
        }
        vo.setItems(itemVOs);

        return ResultVO.ok().setData(vo);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultVO pickPidOut(String pickNo, String packageId) {
        //1.首先根据输入的PID,得到相应PID的信息，进行展示
        PrintLabelBO bo = pickHeadMapper.pickPid(packageId);
        PickHeadPO po = pickHeadMapper.queryByPickNo(pickNo);
        // 如果该物料的PID不在该工单对应采购单的物料范围内，则提示 该物料并不属于该工单
//        List<String> pidList = pickHeadMapper.judgePidHave(pickNo);

        // 判断当前物料不在拣货清单中，则提示 该物料并不在出库清单中
        List<String> maList = pickHeadMapper.judgeMaterial(pickNo);
        boolean flag = maList.contains(bo.getMaterialNo());
        //2.将拣货单表体表中的已拣量作加操作
        int addResult = pickHeadMapper.addPickNum(bo.getMaterialNo(), bo.getNum());
        //3.查看扫码的PID是否在推荐的库位标签表中是否存在推荐的PID,存在则更新拣货标签表中的是否推荐标志位
        List<String> reList = pickHeadMapper.queryRecommend(pickNo);
        boolean flagOne = reList.contains(packageId);
        int recommend = flagOne ? 1 : 0;
        int insertResult = pickHeadMapper.insertPickLabel(po.getPickHeadId(), bo.getPrintLabelId(), recommend);

        return ResultVO.ok().setData(bo);
    }
}
