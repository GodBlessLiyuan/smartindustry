package com.smartindustry.basic.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.basic.constant.BasicConstant;
import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.dto.SupplierDTO;
import com.smartindustry.basic.service.ISupplierService;
import com.smartindustry.basic.vo.SupplierRecordVO;
import com.smartindustry.basic.vo.SupplierVO;
import com.smartindustry.common.bo.si.SupplierBO;
import com.smartindustry.common.constant.ResultConstant;
import com.smartindustry.common.mapper.si.MaterialMapper;
import com.smartindustry.common.mapper.si.SupplierMapper;
import com.smartindustry.common.mapper.si.SupplierRecordMapper;
import com.smartindustry.common.pojo.am.UserPO;
import com.smartindustry.common.pojo.si.MaterialPO;
import com.smartindustry.common.pojo.si.SupplierPO;
import com.smartindustry.common.pojo.si.SupplierRecordPO;
import com.smartindustry.common.security.service.TokenService;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.util.ServletUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
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
 * @date: Created in 2020/7/29 9:19
 * @description: 供应商管理
 * @version: 1.0
 */
@EnableTransactionManagement
@Service
public class SupplierServiceImpl implements ISupplierService {
    @Autowired
    private SupplierMapper supplierMapper;
    @Autowired
    private SupplierRecordMapper supplierRecordMapper;
    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    TokenService tokenService;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<SupplierBO> page = PageQueryUtil.startPage(reqData);
        List<SupplierBO> bos = supplierMapper.pageQuery(reqData);

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), SupplierVO.convert(bos)));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO edit(SupplierDTO dto) {
        UserPO user = tokenService.getLoginUser();
        SupplierPO existPO = supplierMapper.queryBySno(dto.getSno());
        if (null != existPO && (null == dto.getSid() || !dto.getSid().equals(existPO.getSupplierId()))) {
            return new ResultVO(1004);
        }

        if (null == dto.getSid()) {
            // 新增
            SupplierPO supplierPO = SupplierDTO.createPO(dto);
            supplierMapper.insert(supplierPO);
            supplierRecordMapper.insert(new SupplierRecordPO(supplierPO.getSupplierId(), user.getUserId(), BasicConstant.RECORD_ADD));
            return ResultVO.ok();
        }
        // 编辑
        SupplierPO supplierPO = supplierMapper.selectByPrimaryKey(dto.getSid());
        if (null == supplierPO) {
            return new ResultVO(1002);
        }

        SupplierDTO.buildPO(supplierPO, dto);
        supplierPO.setUpdateTime(new Date());
        supplierMapper.updateByPrimaryKey(supplierPO);

        supplierRecordMapper.insert(new SupplierRecordPO(supplierPO.getSupplierId(), user.getUserId(), BasicConstant.RECORD_MODIFY));

        return ResultVO.ok();
    }

    @Override
    public ResultVO delete(List<Long> sids) {
        List<MaterialPO> materialPOs = materialMapper.queryBySids(sids);
        if (null != materialPOs && materialPOs.size() > 0) {
            return new ResultVO(1007);
        }

        supplierMapper.batchDelete(sids);
        return ResultVO.ok();
    }

    @Override
    public ResultVO detail(OperateDTO dto) {
        SupplierBO supplierBO = supplierMapper.queryBySid(dto.getSid());
        if (null == supplierBO) {
            return new ResultVO(1002);
        }

        return ResultVO.ok().setData(SupplierVO.convert(supplierBO));
    }

    @Override
    public ResultVO record(OperateDTO dto) {
        Map<String, Object> res = new HashMap<>(1);
        List<SupplierRecordPO> supplierRecordPOs = supplierRecordMapper.queryBySid(dto.getSid());
        res.put(ResultConstant.OPERATE_RECORD, SupplierRecordVO.convert(supplierRecordPOs));
        return ResultVO.ok().setData(res);
    }

    @Override
    public ResultVO queryAll() {
        List<Map<String, Object>> res = supplierMapper.queryAll();
        return ResultVO.ok().setData(res);
    }
}
