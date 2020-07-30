package com.smartindustry.basic.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.basic.constant.BasicConstant;
import com.smartindustry.basic.dto.MaterialDTO;
import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.service.IMaterialService;
import com.smartindustry.basic.vo.MaterialVO;
import com.smartindustry.basic.vo.SupplierRecordVO;
import com.smartindustry.common.bo.si.MaterialBO;
import com.smartindustry.common.mapper.si.MaterialMapper;
import com.smartindustry.common.mapper.si.MaterialRecordMapper;
import com.smartindustry.common.mapper.si.MaterialSpecificationMapper;
import com.smartindustry.common.pojo.si.MaterialPO;
import com.smartindustry.common.pojo.si.MaterialRecordPO;
import com.smartindustry.common.pojo.si.SupplierRecordPO;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/7/29 9:19
 * @description: 物料管理
 * @version: 1.0
 */
@Service
public class MaterialServiceImpl implements IMaterialService {
    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    private MaterialRecordMapper materialRecordMapper;
    @Autowired
    private MaterialSpecificationMapper materialSpecificationMapper;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<MaterialBO> page = PageQueryUtil.startPage(reqData);
        List<MaterialBO> bos = materialMapper.pageQuery(reqData);

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), MaterialVO.convert(bos)));
    }

    @Override
    public ResultVO edit(MaterialDTO dto) {
        MaterialPO existPO = materialMapper.queryByMno(dto.getMno());
        if (null != existPO && (null == dto.getMid() || !dto.getMid().equals(existPO.getMaterialId()))) {
            return new ResultVO(1004);
        }

        if (null == dto.getMid()) {
            // 新增
            MaterialPO materialPO = MaterialDTO.createPO(dto);
            materialMapper.insert(materialPO);
            materialRecordMapper.insert(new MaterialRecordPO(materialPO.getMaterialId(), 1L, BasicConstant.RECORD_ADD));
            return ResultVO.ok();
        }
        // 编辑
        MaterialPO materialPO = materialMapper.selectByPrimaryKey(dto.getMid());
        if (null == materialPO) {
            return new ResultVO(1002);
        }

        MaterialDTO.buildPO(materialPO, dto);
        materialPO.setUpdateTime(new Date());
        materialMapper.updateByPrimaryKey(materialPO);

        materialRecordMapper.insert(new MaterialRecordPO(materialPO.getMaterialId(), 1L, BasicConstant.RECORD_MODIFY));

        return ResultVO.ok();
    }

    @Override
    public ResultVO delete(List<Long> sids) {
        // TODO : 采购、销售、工单、出入库、收料单关联，则提示“该物料有关联单据，不可删除”

        materialMapper.batchDelete(sids);
        return ResultVO.ok();
    }

    @Override
    public ResultVO detail(OperateDTO dto) {
        MaterialBO materialBO = materialMapper.queryByMid(dto.getMid());
        if (null == materialBO) {
            return new ResultVO(1002);
        }

        return ResultVO.ok().setData(MaterialVO.convert(materialBO));
    }

    @Override
    public ResultVO record(OperateDTO dto) {
        List<SupplierRecordPO> supplierRecordPOs = materialRecordMapper.queryByMid(dto.getMid());
        return ResultVO.ok().setData(SupplierRecordVO.convert(supplierRecordPOs));
    }

    @Override
    public ResultVO upload(MultipartFile file) {
        return null;
    }
}
