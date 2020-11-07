package com.smartindustry.datasynchronize.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.common.bo.am.DeptBO;
import com.smartindustry.common.bo.am.UserBO;
import com.smartindustry.common.bo.si.ClientBO;
import com.smartindustry.common.bo.si.MaterialBO;
import com.smartindustry.common.bo.si.SupplierBO;
import com.smartindustry.common.mapper.am.DeptMapper;
import com.smartindustry.common.mapper.am.RoleMapper;
import com.smartindustry.common.mapper.am.UserMapper;
import com.smartindustry.common.mapper.dd.MeasureUnitMapper;
import com.smartindustry.common.mapper.si.ClientMapper;
import com.smartindustry.common.mapper.si.MaterialMapper;
import com.smartindustry.common.mapper.si.SupplierMapper;
import com.smartindustry.common.pojo.am.DeptPO;
import com.smartindustry.common.pojo.am.RolePO;
import com.smartindustry.common.pojo.am.UserPO;
import com.smartindustry.common.pojo.dd.MeasureUnitPO;
import com.smartindustry.common.pojo.ds.sqlserver.*;
import com.smartindustry.common.pojo.si.ClientPO;
import com.smartindustry.common.pojo.si.MaterialPO;
import com.smartindustry.common.pojo.si.SupplierPO;
import com.smartindustry.common.sqlserver.*;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.datasynchronize.constant.SyncConstant;
import com.smartindustry.datasynchronize.service.IBasicService;
import com.smartindustry.datasynchronize.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

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
    private ClientMapper clientMapper;

    @Autowired
    private DeptErMapper deptErpMapper;

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private OperatorMapper operatorMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleErpMapper roleErpMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private SupplierErpMapper supplierErpMapper;

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private MeasureUnitMapper measureUnitMapper;

    /**
     * 物料同步数据
     * @param reqData
     * @return
     */
    @Override
    public ResultVO material(Map<String, Object> reqData) {
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
                unit = new MeasureUnitPO();
                unit.setCreateTime(Calendar.getInstance().getTime());
                unit.setMeasureUnitName(erppo.getMaterialUnit());
                unit.setUserId(1L);
                measureUnitMapper.insert(unit);
            }
            po.setMeasureUnitId(unit.getMeasureUnitId());
            po.setUserId(1L);
            po.setDr((byte)1);
            po.setCreateTime(Calendar.getInstance().getTime());
            MaterialPO p = materialMapper.queryByMaterialNo(po.getMaterialNo(), po.getMaterialName());
            if (p == null) {
                pos.add(po);
            }
        }
        if (!pos.isEmpty()) {
            materialMapper.batchInsert(pos);
        }

        //查询
        Calendar c =Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY)-2);
        reqData.put("ctime", c.getTime());
        Page<Long> page = PageQueryUtil.startPage(reqData);
        List<MaterialBO> list = materialMapper.pageQueryStorage(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), MaterialVO.convert(list)));
    }

    /**
     * 客户同步数据
     * @param reqData
     * @return
     */
    @Override
    public ResultVO client(Map<String, Object> reqData) {
        List<ClientErpPO> clients = clientErpMapper.queryAll();
        List<ClientPO> pos = new ArrayList<>(clients.size());
        for (ClientErpPO erppo: clients) {
            ClientPO po = new ClientPO();
            po.setClientName(erppo.getClientName());
            po.setClientNo(erppo.getClientCode());
            po.setClientTypeId(1L);
            po.setAddress(erppo.getAddress());
            po.setPhone(erppo.getCellphone()!= null?erppo.getCellphone():"");
            po.setContact(erppo.getContact() != null?erppo.getContact():"");
            po.setEmail(erppo.getEmail());
            po.setFax(erppo.getFax());
            po.setUrl(erppo.getHomeAddress());
            po.setRemark(erppo.getRemark());
            po.setCreateTime(Calendar.getInstance().getTime());
            po.setDr((byte) 1);
            if (clientMapper.queryByClientNo(po.getClientNo()) == null) {
                pos.add(po);
            }
        }
        if (!pos.isEmpty()) {
            clientMapper.batchInsert(pos);
        }
        //查询
        Calendar c =Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY)-2);
        reqData.put("ctime", c.getTime());
        Page<Long> page = PageQueryUtil.startPage(reqData);
        List<ClientPO> list = clientMapper.pageQuery(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), ClientVO.convert(list)));
    }

    /**
     * 供应商数据同步
     * @param reqData
     * @return
     */
    @Override
    public ResultVO supplier(Map<String, Object> reqData) {
        List<SupplierErpPO> suppliers = supplierErpMapper.queryAll();
        List<SupplierPO> pos = new ArrayList<>(suppliers.size());
        for (SupplierErpPO ep: suppliers) {
            SupplierPO po = new SupplierPO();
            po.setSupplierNo(ep.getSupplierCode());
            po.setSupplierName(ep.getSupplierName());
            po.setContactName(ep.getContact());
            po.setPhone(ep.getCellphone());
            po.setFax(ep.getFax());
            po.setSite(ep.getHomeAddress());
            po.setMail(ep.getEmail());
            po.setAddress(ep.getAddress());
            po.setRemark(ep.getRemark());
            po.setUserId(1L);
            po.setCreateTime(Calendar.getInstance().getTime());
            po.setDr((byte)1);
            if (supplierMapper.queryBySno(po.getSupplierNo()) == null) {
                pos.add(po);
            }
        }
        if (!pos.isEmpty()) {
            supplierMapper.batchInsert(pos);
        }
        //查询
        Calendar c =Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY)-2);
        reqData.put("ctime", c.getTime());
        Page<Long> page = PageQueryUtil.startPage(reqData);
        List<SupplierPO> list = supplierMapper.pageQuery(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), SupplierVO.convert(list)));
    }

    /**
     * 部门数据同步
     * @param reqData
     * @return
     */
    @Override
    public ResultVO dept(Map<String, Object> reqData) {
        List<DeptErpPO> depts = deptErpMapper.queryAll();
        List<DeptPO> pos = new ArrayList<>(depts.size());
        for (DeptErpPO ep: depts) {
            DeptPO po = new DeptPO();
            po.setDeptCode(ep.getDeptCode());
            po.setDeptName(ep.getDeptName());
            po.setUserId(1L);
            po.setStatus((byte)1);
            po.setCreateTime(Calendar.getInstance().getTime());
            po.setDr((byte)1);
            if (deptMapper.queryByCode(po.getDeptCode()) == null ) {
                pos.add(po);
            }
        }
        if (!pos.isEmpty()) {
            deptMapper.batchInsert(pos);
        }
        //查询
        Calendar c =Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY)-2);
        reqData.put("ctime", c.getTime());
        Page<Long> page = PageQueryUtil.startPage(reqData);
        List<DeptBO> list = deptMapper.deptPageQuery(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), DeptVO.convert(list)));
    }

    /**
     * 用户角色数据同步
     * @param reqData
     * @return
     */
    @Override
    public ResultVO role(Map<String, Object> reqData) {
        List<RoleErpPO> roles = roleErpMapper.queryAll();
        List<RolePO> pos = new ArrayList<>(roles.size());
        for (RoleErpPO ep: roles) {
            RolePO po = new RolePO();
            po.setRoleCode(ep.getRoleCode());
            po.setRoleName(ep.getRoleName());
            po.setStatus((byte)1);
            po.setCreateTime(Calendar.getInstance().getTime());
            po.setDr((byte)1);
            if (roleMapper.queryByCode(po.getRoleCode()) == null) {
                pos.add(po);
            }
        }
        if (!pos.isEmpty()) {
            roleMapper.batchInsert(pos);
        }
        //查询
        Calendar c =Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY)-2);
        reqData.put("ctime", c.getTime());
        Page<Long> page = PageQueryUtil.startPage(reqData);
        List<RolePO> list = roleMapper.rolePageQuery(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), RoleVO.convert(list)));
    }

    /**
     * 用户数据同步
     * @param reqData
     * @return
     */
    @Override
    public ResultVO user(Map<String, Object> reqData) {
        List<OperatorPO> operators = operatorMapper.queryAll();
        List<UserPO> pos = new ArrayList<>(operators.size());
        for (OperatorPO ep: operators) {
            UserPO po = new UserPO();
            if (ep.getRoleId() != null) {
                RolePO role = roleMapper.queryByCode(ep.getRoleCode());
                if (role == null) {
                    continue;
                }
                po.setRoleId(role.getRoleId());
            }
            if (ep.getDeptId() != null) {
                DeptPO dept = deptMapper.queryByCode(ep.getDeptCode());
                if (dept == null) {
                    continue;
                }
                po.setDeptId(dept.getDeptId());
            }
            po.setUserCode(ep.getOperatorNo());
            po.setName(ep.getOperatorName());
            po.setUsername(ep.getOperatorName());
            po.setPassword(ep.getPassword());
            po.setStatus((byte)1);
            po.setCreateTime(Calendar.getInstance().getTime());
            po.setDr((byte)1);
            if (userMapper.queryByCode(po.getUserCode()) == null) {
                pos.add(po);
            }
        }
        if (!pos.isEmpty()) {
            userMapper.batchInsert(pos);
        }
        //查询
        Calendar c =Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY)-2);
        reqData.put("ctime", c.getTime());
        Page<Long> page = PageQueryUtil.startPage(reqData);
        List<UserBO> list = userMapper.userPageQuery(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), UserVO.convert(list)));
    }
}
