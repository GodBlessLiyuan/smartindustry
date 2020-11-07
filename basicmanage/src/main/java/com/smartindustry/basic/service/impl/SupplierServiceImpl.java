package com.smartindustry.basic.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.service.ISupplierService;
import com.smartindustry.common.bo.si.SupplierBO;
import com.smartindustry.common.bo.si.SupplierRecordBO;
import com.smartindustry.common.constant.ResultConstant;
import com.smartindustry.common.mapper.si.SupplierMapper;
import com.smartindustry.common.mapper.si.SupplierRecordMapper;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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


    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<SupplierBO> page = PageQueryUtil.startPage(reqData);
        List<SupplierBO> bos = supplierMapper.pageQuery(reqData);

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), SupplierVO.convert(bos)));
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
        List<SupplierRecordBO> supplierRecordBOs = supplierRecordMapper.queryBySid(dto.getSid());
        res.put(ResultConstant.OPERATE_RECORD, SupplierRecordVO.convert(supplierRecordBOs));
        return ResultVO.ok().setData(res);
    }

    @Override
    public ResultVO queryAll() {
        List<Map<String, Object>> res = supplierMapper.queryAll();
        return ResultVO.ok().setData(res);
    }
}
