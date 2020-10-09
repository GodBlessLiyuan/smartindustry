package com.smartindustry.workbench.service.impl;

import com.smartindustry.common.bo.am.LoginUserBO;
import com.smartindustry.common.bo.wm.WorkBenchBO;
import com.smartindustry.common.config.FilePathConfig;
import com.smartindustry.common.mapper.om.OutboundMapper;
import com.smartindustry.common.mapper.om.PickHeadMapper;
import com.smartindustry.common.mapper.sm.ReceiptBodyMapper;
import com.smartindustry.common.mapper.sm.StorageMapper;
import com.smartindustry.common.mapper.wm.WorkBenchMapper;
import com.smartindustry.common.security.service.TokenService;
import com.smartindustry.common.util.ServletUtil;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.workbench.constants.WorkBenchConstant;
import com.smartindustry.workbench.dto.OperateDTO;
import com.smartindustry.workbench.service.IWorkBenchService;
import com.smartindustry.workbench.vo.WorkBenchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hui.feng
 * @date created in 2020/9/25
 * @description
 */
@Service
public class WorkBenchServiceImpl implements IWorkBenchService {

    @Autowired
    private WorkBenchMapper workBenchMapper;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ReceiptBodyMapper receiptBodyMapper;

    @Autowired
    private StorageMapper storageMapper;

    @Autowired
    private PickHeadMapper pickHeadMapper;

    @Autowired
    private OutboundMapper outboundMapper;

    @Autowired
    private FilePathConfig filePathConfig;

    /**
     * 获取用户在只能工作台的权限列表
     *
     * @param dto
     * @return
     */
    @Override
    public ResultVO work(OperateDTO dto) {

        dto.setBtype((byte) 1);
        List<WorkBenchBO> bos = query(dto);
        //查询各个模块运行数量
        List<WorkBenchVO> vos = WorkBenchVO.convert(bos, filePathConfig.getPublicPath());
        for (WorkBenchVO vo : vos) {
            Long wbId = vo.getWbid();
            Integer num = 0;
            //QE待质检
            if (wbId.equals(WorkBenchConstant.WORK_QE_WAIT_CHECK)) {
                num = receiptBodyMapper.countHandleNum(WorkBenchConstant.RECEIPT_STATUS_QE_CHECK, WorkBenchConstant.QUALITY_UNCHECK);
            }
            //IQC检测
            if (wbId.equals(WorkBenchConstant.WORK_IQC_WAIT_CHECK)) {
                num = receiptBodyMapper.countHandleNum(WorkBenchConstant.RECEIPT_STATUS_IQC_CHECK, WorkBenchConstant.QUALITY_UNCHECK);
            }
            //QE待确认
            if (wbId.equals(WorkBenchConstant.WORK_QE_WAIT_CONFIRM)) {
                num = receiptBodyMapper.countHandleNum(WorkBenchConstant.RECEIPT_STATUS_QE_CONFIRM, WorkBenchConstant.QUALITY_UNCHECK);
            }
            //待入库
            if(wbId.equals(WorkBenchConstant.WORK_WAIT_STORAGE)) {
                //收料单
                Map<String, Object> reqData = new HashMap<>(WorkBenchConstant.NUM_1);
                reqData.put("status", WorkBenchConstant.STORAGE_STATUS_WAIT);
                List<Long> sids = storageMapper.pageQuery(reqData);
                num += sids!= null && !sids.isEmpty()?storageMapper.queryBySids(sids).size():0;
                //其他入库单
                num+= storageMapper.pageQueryOther(reqData).size();
            }
            //待拣货
            if (wbId.equals(WorkBenchConstant.WORK_WAIT_PICK)) {
                num = pickHeadMapper.countHandleNum(WorkBenchConstant.PICK_HEAD_STATUS_UNHANDLE, null);
            }
            //OQC待检验
            if (wbId.equals(WorkBenchConstant.WORK_OQC_WAIT_CHECK)) {
                num = pickHeadMapper.countHandleNum(WorkBenchConstant.PICK_HEAD_AUDIT_OQC, WorkBenchConstant.PICK_HEAD_SOURCE_SAIL);
            }
            //工单待审核
            if (wbId.equals(WorkBenchConstant.WORK_ORDER_WAIT_AUDIT)) {
                num = pickHeadMapper.countHandleNum(WorkBenchConstant.PICK_HEAD_AUDIT_OQC, WorkBenchConstant.PICK_HEAD_SOURCE_ORDER);
            }

            //待出库
            if (wbId.equals(WorkBenchConstant.WORK_WAIT_OUTBOUND)) {
                num = outboundMapper.countHandleNum(WorkBenchConstant.OUT_BOUND_STATUS_WAIT);
            }
            vo.setNum(num);
        }

        return ResultVO.ok().setData(vos);
    }

    /**
     * 获取用户在快捷访问中的模块列表
     *
     * @param dto
     * @return
     */
    @Override
    public ResultVO shortcut(OperateDTO dto) {
        //获取 添加用户权限的用户信息
        LoginUserBO userBO = tokenService.getLoginUser(ServletUtil.getRequest());
        dto.setBtype((byte) 2);
        List<WorkBenchVO> vos = WorkBenchVO.convert(query(dto),filePathConfig.getPublicPath());
        //按照工作台权限模块进行划分
        Map<Byte, List<WorkBenchVO>> map = vos.stream().collect(Collectors.toMap(WorkBenchVO::getBmodule, p -> {
            List<WorkBenchVO> list = new ArrayList<>();
            list.add(p);
            return list;
        }, (List<WorkBenchVO> values1, List<WorkBenchVO> values2) -> {
            values1.addAll(values2);
            return values1;
        }));
        return ResultVO.ok().setData(map);
    }


    //---------------------- private method-----------------------------

    /**
     * 查询工作台权限列表
     *
     * @param dto
     * @return
     */
    private List<WorkBenchBO> query(OperateDTO dto) {
        //获取 添加用户权限的用户信息
        LoginUserBO userBO = tokenService.getLoginUser(ServletUtil.getRequest());
        List<Long> permissionIds = userBO.getPermissionIds();
        //查找所有属于该模块的工作台权限
        List<WorkBenchBO> wbBos = workBenchMapper.queryByModuleId(dto.getBmodule(), dto.getBtype());
        List<WorkBenchBO> filterWbBos = wbBos.stream().filter(WorkBenchBO -> (permissionIds.contains(WorkBenchBO.getAuthorityId()))).collect(Collectors.toList());
        return filterWbBos;
    }
}
