package com.smartindustry.basic.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.service.ISupplierService;
import com.smartindustry.basic.vo.SupplierVO;
import com.smartindustry.common.bo.si.SupplierBO;
import com.smartindustry.common.mapper.si.SupplierMapper;
import com.smartindustry.common.pojo.si.SupplierPO;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
}
