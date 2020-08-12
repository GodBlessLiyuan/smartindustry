package com.smartindustry.basic.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.basic.constant.BasicConstant;
import com.smartindustry.basic.dto.MaterialDTO;
import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.service.IMaterialService;
import com.smartindustry.basic.vo.MaterialRecordVO;
import com.smartindustry.basic.vo.MaterialVO;
import com.smartindustry.common.bo.si.MaterialBO;
import com.smartindustry.common.config.FilePathConfig;
import com.smartindustry.common.constant.ResultConstant;
import com.smartindustry.common.mapper.im.MaterialInventoryMapper;
import com.smartindustry.common.mapper.si.MaterialMapper;
import com.smartindustry.common.mapper.si.MaterialRecordMapper;
import com.smartindustry.common.mapper.si.MaterialSpecificationMapper;
import com.smartindustry.common.pojo.im.MaterialInventoryPO;
import com.smartindustry.common.pojo.si.MaterialPO;
import com.smartindustry.common.pojo.si.MaterialRecordPO;
import com.smartindustry.common.util.FileUtil;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private MaterialRecordMapper materialRecordMapper;
    @Autowired
    private MaterialSpecificationMapper materialSpecificationMapper;
    @Autowired
    private MaterialInventoryMapper materialInventoryMapper;

    @Autowired
    private FilePathConfig filePathConfig;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<MaterialBO> page = PageQueryUtil.startPage(reqData);
        List<MaterialBO> bos = materialMapper.pageQuery(reqData);

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), MaterialVO.convert(bos, filePathConfig)));
    }

    @Transactional(rollbackFor = Exception.class)
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

            if (null != dto.getFiles() && dto.getFiles().size() > 0) {
                dto.setMid(materialPO.getMaterialId());
                materialSpecificationMapper.batchInsert(MaterialDTO.createFilePO(dto, filePathConfig));
            }

            // 物料库存信息
            MaterialInventoryPO materialInventoryPO = new MaterialInventoryPO();
            materialInventoryPO.setMaterialId(materialPO.getMaterialId());
            materialInventoryMapper.insert(materialInventoryPO);

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

        materialSpecificationMapper.deleteByMid(dto.getMid());
        if (null != dto.getFiles() && dto.getFiles().size() > 0) {
            materialSpecificationMapper.batchInsert(MaterialDTO.createFilePO(dto, filePathConfig));
        }

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

        return ResultVO.ok().setData(MaterialVO.convert(materialBO, filePathConfig));
    }

    @Override
    public ResultVO record(OperateDTO dto) {
        Map<String, Object> res = new HashMap<>(1);
        List<MaterialRecordPO> materialRecordPOs = materialRecordMapper.queryByMid(dto.getMid());
        res.put(ResultConstant.OPERATE_RECORD, MaterialRecordVO.convert(materialRecordPOs));
        return ResultVO.ok().setData(res);
    }

    @Override
    public ResultVO upload(MultipartFile file) {
        String picture = FileUtil.uploadFile(file, filePathConfig.getLocalPath(), filePathConfig.getProjectDir() + filePathConfig.getBasicDir() + filePathConfig.getMaterialDir(), BasicConstant.FILE_MATERIAL);
        return ResultVO.ok().setData(filePathConfig.getPublicPath() + picture);
    }
}
