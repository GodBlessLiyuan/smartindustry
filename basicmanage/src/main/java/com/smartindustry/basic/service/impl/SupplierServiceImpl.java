package com.smartindustry.basic.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.dto.SupplierDTO;
import com.smartindustry.basic.service.ISupplierService;
import com.smartindustry.basic.vo.SupplierVO;
import com.smartindustry.common.bo.si.SupplierBO;
import com.smartindustry.common.mapper.si.MaterialMapper;
import com.smartindustry.common.mapper.si.SupplierMapper;
import com.smartindustry.common.pojo.am.UserPO;
import com.smartindustry.common.pojo.si.MaterialPO;
import com.smartindustry.common.pojo.si.SupplierPO;
import com.smartindustry.common.security.service.TokenService;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    private MaterialMapper materialMapper;
    @Autowired
    TokenService tokenService;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<SupplierBO> page = PageQueryUtil.startPage(reqData);
        List<SupplierPO> bos = supplierMapper.pageQuery(reqData);

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), SupplierVO.convert(bos)));
    }

    @Override
    public ResultVO detail(OperateDTO dto) {
        SupplierPO po = supplierMapper.selectByPrimaryKey(dto.getSid());
        if (null == po) {
            return new ResultVO(1002);
        }
        return ResultVO.ok().setData(SupplierVO.convert(po));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO edit(SupplierDTO dto) {
        UserPO user = tokenService.getLoginUser();
        SupplierPO existPO = supplierMapper.queryBySno(dto.getSno());
        if (null != existPO && !existPO.getSupplierId().equals(dto.getSid())) {
            return new ResultVO(1004);
        }

        if (null == dto.getSid()) {
            // 新增
            SupplierPO supplierPO = SupplierDTO.createPO(dto);
            supplierMapper.insert(supplierPO);
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
    public ResultVO queryAll() {
        List<Map<String, Object>> res = supplierMapper.queryAll();
        return ResultVO.ok().setData(res);
    }
}
