package com.smartindustry.datasynchronize.service.impl;

import com.smartindustry.common.mapper.dd.MeasureUnitMapper;
import com.smartindustry.common.mapper.si.MaterialMapper;
import com.smartindustry.common.mapper.si.SupplierMapper;
import com.smartindustry.common.pojo.dd.MeasureUnitPO;
import com.smartindustry.common.pojo.ds.sqlserver.*;
import com.smartindustry.common.pojo.si.MaterialPO;
import com.smartindustry.common.pojo.si.SupplierPO;
import com.smartindustry.common.sqlserver.*;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.datasynchronize.constant.SyncConstant;
import com.smartindustry.datasynchronize.service.IBasicService;
import org.apache.poi.hpsf.Decimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author hui.feng
 * @date created in 2020/11/5
 * @description
 */
@Service
@EnableTransactionManagement
public class BasicServiceImpl implements IBasicService {

    @Autowired
    private MaterialErpMapper materialErpMapper;

    @Autowired
    private ClientErpMapper clientErpMapper;

    @Autowired
    private DeptErMapper deptErpMapper;

    @Autowired
    private OperatorMapper operatorMapper;

    @Autowired
    private PurchaseErpMapper purchaseErpMapper;

    @Autowired
    private RoleErpMapper roleErpMapper;

    @Autowired
    private SaleOutboundErpMapper saleOutboundErpMapper;

    @Autowired
    private SupplierErpMapper supplierErpMapper;

    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private MeasureUnitMapper measureUnitMapper;

    @Override
    public ResultVO material() {
        List<MaterialErpPO> materials = materialErpMapper.queryAll();
        List<MaterialPO> pos = new ArrayList<>(materials.size());
        for (MaterialErpPO erppo : materials) {
            MaterialPO po = new MaterialPO();
            po.setMaterialNo(erppo.getMaterialNo());
            po.setMaterialDesc(erppo.getRemark());
            po.setMaterialName(erppo.getMaterialName());
            po.setMaterialModel(erppo.getMaterialModel());
            if (SyncConstant.TYPE_ERP_MATERIAL_PRODUCT.equals(erppo.getMaterialType())) {
                po.setMaterialType(SyncConstant.TYPE_MATERIAL_PRODUCT);
            } else {
                po.setMaterialType(SyncConstant.TYPE_MATERIAL_RAW);
            }
            //计量单位
            MeasureUnitPO unit = measureUnitMapper.queryByName(erppo.getMaterialUnit());
            if (unit == null) {
                unit.setCreateTime(Calendar.getInstance().getTime());
                unit.setMeasureUnitName(erppo.getMaterialUnit());
                unit.setUserId(1L);
                measureUnitMapper.insert(unit);
            }
            po.setMeasureUnitId(unit.getMeasureUnitId());
            po.setUserId(1L);
            po.setDr((byte)1);
            po.setCreateTime(Calendar.getInstance().getTime());
            pos.add(po);
        }
        materialMapper.batchInsert(pos);
        return ResultVO.ok().setData(materials);
    }

    @Override
    public ResultVO client() {
        List<ClientErpPO> clients = clientErpMapper.queryAll();
        return ResultVO.ok().setData(clients);
    }

    @Override
    public ResultVO supplier() {
        List<SupplierErpPO> suppliers = supplierErpMapper.queryAll();
        return ResultVO.ok().setData(suppliers);
    }

    @Override
    public ResultVO dept() {
        List<DeptErpPO> depts = deptErpMapper.queryAll();
        return ResultVO.ok().setData(depts);
    }

    @Override
    public ResultVO role() {
        List<RoleErpPO> roles = roleErpMapper.queryAll();
        return ResultVO.ok().setData(roles);
    }

    @Override
    public ResultVO user() {
        List<OperatorPO> operators = operatorMapper.queryAll();
        return ResultVO.ok().setData(operators);
    }
}
