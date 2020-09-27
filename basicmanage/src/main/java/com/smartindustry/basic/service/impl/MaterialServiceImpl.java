package com.smartindustry.basic.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.basic.constant.BasicConstant;
import com.smartindustry.basic.dto.MaterialAttributeDTO;
import com.smartindustry.basic.dto.MaterialDTO;
import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.service.IMaterialService;
import com.smartindustry.basic.vo.MaterialAttributeVO;
import com.smartindustry.basic.vo.MaterialRecordVO;
import com.smartindustry.basic.vo.MaterialVO;
import com.smartindustry.common.bo.im.MaterialInventoryBO;
import com.smartindustry.common.bo.si.MaterialAttributeBO;
import com.smartindustry.common.bo.si.MaterialBO;
import com.smartindustry.common.bo.si.MaterialRecordBO;
import com.smartindustry.common.config.FilePathConfig;
import com.smartindustry.common.constant.ResultConstant;
import com.smartindustry.common.mapper.im.MaterialInventoryMapper;
import com.smartindustry.common.mapper.im.SafeStockMapper;
import com.smartindustry.common.mapper.om.PickBodyMapper;
import com.smartindustry.common.mapper.si.MaterialAttributeMapper;
import com.smartindustry.common.mapper.si.MaterialMapper;
import com.smartindustry.common.mapper.si.MaterialRecordMapper;
import com.smartindustry.common.mapper.si.MaterialSpecificationMapper;
import com.smartindustry.common.mapper.sm.ReceiptBodyMapper;
import com.smartindustry.common.pojo.am.UserPO;
import com.smartindustry.common.pojo.im.MaterialInventoryPO;
import com.smartindustry.common.pojo.im.SafeStockPO;
import com.smartindustry.common.pojo.om.PickBodyPO;
import com.smartindustry.common.pojo.si.MaterialAttributePO;
import com.smartindustry.common.pojo.si.MaterialPO;
import com.smartindustry.common.pojo.si.MaterialRecordPO;
import com.smartindustry.common.pojo.sm.ReceiptBodyPO;
import com.smartindustry.common.security.service.TokenService;
import com.smartindustry.common.util.FileUtil;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.util.ServletUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @author: xiahui
 * @date: Created in 2020/7/29 9:19
 * @description: 物料管理
 * @version: 1.0
 */
@EnableTransactionManagement
@Service
public class MaterialServiceImpl implements IMaterialService {
    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    private MaterialAttributeMapper materialAttributeMapper;
    @Autowired
    private MaterialRecordMapper materialRecordMapper;
    @Autowired
    private MaterialSpecificationMapper materialSpecificationMapper;
    @Autowired
    private MaterialInventoryMapper materialInventoryMapper;
    @Autowired
    private ReceiptBodyMapper receiptBodyMapper;
    @Autowired
    private PickBodyMapper pickBodyMapper;
    @Autowired
    TokenService tokenService;
    @Autowired
    private FilePathConfig filePathConfig;
    @Autowired
    private SafeStockMapper safeStockMapper;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<MaterialBO> page = PageQueryUtil.startPage(reqData);
        List<MaterialBO> bos = materialMapper.pageQuery(reqData);

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), MaterialVO.convert(bos, filePathConfig)));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO edit(MaterialDTO dto) {
        UserPO user = tokenService.getLoginUser();
        MaterialPO existPO = materialMapper.queryByMno(dto.getMno());
        if (null != existPO && !existPO.getMaterialId().equals(dto.getMid())) {
            return new ResultVO(1004);
        }

        if (null == dto.getMid()) {
            // 新增
            MaterialPO materialPO = MaterialDTO.createPO(dto);

            // 物料属性
            if (null != dto.getMattribute()) {
                materialMapper.insert(materialPO);

                MaterialAttributePO attributePO = MaterialAttributeDTO.createPO(dto.getMattribute());
                attributePO.setMaterialId(materialPO.getMaterialId());
                materialAttributeMapper.insert(attributePO);


                // 物料库存信息
                MaterialInventoryPO materialInventoryPO = new MaterialInventoryPO();
                materialInventoryPO.setMaterialId(materialPO.getMaterialId());
                materialInventoryMapper.insert(materialInventoryPO);

                // 物料库存
                SafeStockPO stockPO = new SafeStockPO();
                stockPO.setMaterialInventoryId(materialInventoryPO.getMaterialInventoryId());
                stockPO.setLowerLimit(dto.getMattribute().getLlimit());
                stockPO.setWay((byte) (null != dto.getMattribute().getWay() && dto.getMattribute().getWay() ? 1 : 2));
                stockPO.setUserId(user.getUserId());
                stockPO.setCreateTime(new Date());
                safeStockMapper.insert(stockPO);

                MaterialInventoryBO inventoryBO = materialInventoryMapper.queryByMid(materialPO.getMaterialId());
                materialInventoryMapper.updateByPrimaryKey(inventoryBO.updatePO(new MaterialInventoryPO()));
            } else {
                materialMapper.insert(materialPO);

                // 物料库存信息
                MaterialInventoryPO materialInventoryPO = new MaterialInventoryPO();
                materialInventoryPO.setMaterialId(materialPO.getMaterialId());
                materialInventoryMapper.insert(materialInventoryPO);
            }

            materialRecordMapper.insert(new MaterialRecordPO(materialPO.getMaterialId(), user.getUserId(), BasicConstant.RECORD_ADD));

            if (null != dto.getFiles() && dto.getFiles().size() > 0) {
                dto.setMid(materialPO.getMaterialId());
                materialSpecificationMapper.batchInsert(MaterialDTO.createFilePO(dto, filePathConfig));
            }

            return ResultVO.ok();
        }
        // 编辑
        MaterialPO materialPO = materialMapper.selectByPrimaryKey(dto.getMid());
        if (null == materialPO) {
            return new ResultVO(1002);
        }

        MaterialAttributePO attributePO = materialAttributeMapper.queryByMid(materialPO.getMaterialId());
        // 物料属性
        if (null == attributePO) {
            if (null != dto.getMattribute()) {
                attributePO = MaterialAttributeDTO.createPO(dto.getMattribute());
                attributePO.setMaterialId(materialPO.getMaterialId());
                materialAttributeMapper.insert(attributePO);

                // 物料库存信息
                MaterialInventoryBO inventoryBO = materialInventoryMapper.queryByMid(materialPO.getMaterialId());

                // 物料库存
                SafeStockPO stockPO = new SafeStockPO();
                stockPO.setMaterialInventoryId(inventoryBO.getMaterialInventoryId());
                stockPO.setLowerLimit(dto.getMattribute().getLlimit());
                stockPO.setWay((byte) (null != dto.getMattribute().getWay() && dto.getMattribute().getWay() ? 1 : 2));
                stockPO.setUserId(user.getUserId());
                stockPO.setCreateTime(new Date());
                safeStockMapper.insert(stockPO);

                inventoryBO.setWay(stockPO.getWay());
                inventoryBO.setLowerLimit(stockPO.getLowerLimit());

                materialInventoryMapper.updateByPrimaryKey(inventoryBO.updatePO(new MaterialInventoryPO()));
            }
        } else {
            // 物料下限及是否在途未发生变化时不更新物料库存信息
            if (attributePO.getLowerLimit().compareTo(dto.getMattribute().getLlimit()) != 0 || attributePO.getWay() == 1 != dto.getMattribute().getWay()) {
                MaterialInventoryBO inventoryBO = materialInventoryMapper.queryByMid(materialPO.getMaterialId());

                SafeStockPO stockPO = safeStockMapper.queryByMiid(inventoryBO.getMaterialInventoryId());
                stockPO.setWay((byte) (dto.getMattribute().getWay() != null && dto.getMattribute().getWay() ? 1 : 2));
                stockPO.setLowerLimit(dto.getMattribute().getLlimit());
                safeStockMapper.updateByPrimaryKey(stockPO);
                materialInventoryMapper.updateByPrimaryKey(inventoryBO.updatePO(new MaterialInventoryPO()));
            }

            MaterialAttributeDTO.buildPO(attributePO, dto.getMattribute());
            materialAttributeMapper.updateByPrimaryKey(attributePO);
        }

        MaterialDTO.buildPO(materialPO, dto);
        materialPO.setUpdateTime(new Date());
        materialMapper.updateByPrimaryKey(materialPO);

        materialRecordMapper.insert(new MaterialRecordPO(materialPO.getMaterialId(), user.getUserId(), BasicConstant.RECORD_EDIT));

        materialSpecificationMapper.deleteByMid(dto.getMid());
        if (null != dto.getFiles() && dto.getFiles().size() > 0) {
            materialSpecificationMapper.batchInsert(MaterialDTO.createFilePO(dto, filePathConfig));
        }

        return ResultVO.ok();
    }

    @Override
    public ResultVO delete(List<Long> mids) {
        // TODO : 采购、销售、工单、出入库、收料单关联，则提示“该物料有关联单据，不可删除”
        List<ReceiptBodyPO> receiptBodyPOs = receiptBodyMapper.queryByMids(mids);
        if (null != receiptBodyPOs && receiptBodyPOs.size() > 0) {
            return new ResultVO(1007);
        }

        List<PickBodyPO> pickBodyPOs = pickBodyMapper.queryByMids(mids);
        if (null != pickBodyPOs && pickBodyPOs.size() > 0) {
            return new ResultVO(1007);
        }

        materialMapper.batchDelete(mids);

        materialInventoryMapper.batchDelete(mids);

        return ResultVO.ok();
    }

    @Override
    public ResultVO detail(OperateDTO dto) {
        MaterialBO materialBO = materialMapper.queryByMid(dto.getMid());
        if (null == materialBO) {
            return new ResultVO(1002);
        }

        MaterialVO vo = MaterialVO.convert(materialBO, filePathConfig);
        MaterialAttributeBO attributeBO = materialAttributeMapper.detail(materialBO.getMaterialId());
        if (null != attributeBO) {
            vo.setMattribute(MaterialAttributeVO.convert(attributeBO));
        }

        return ResultVO.ok().setData(vo);
    }

    @Override
    public ResultVO record(OperateDTO dto) {
        Map<String, Object> res = new HashMap<>(1);
        List<MaterialRecordBO> materialRecordBOs = materialRecordMapper.queryByMid(dto.getMid());
        res.put(ResultConstant.OPERATE_RECORD, MaterialRecordVO.convert(materialRecordBOs));
        return ResultVO.ok().setData(res);
    }

    @Override
    public ResultVO upload(MultipartFile file) {
        String picture = FileUtil.uploadFile(file, filePathConfig.getLocalPath(), filePathConfig.getProjectDir() + filePathConfig.getBasicDir() + filePathConfig.getMaterialDir(), BasicConstant.FILE_MATERIAL);
        return ResultVO.ok().setData(filePathConfig.getPublicPath() + picture);
    }
}
