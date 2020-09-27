package com.smartindustry.basic.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.basic.constant.BasicConstant;
import com.smartindustry.basic.dto.BomBodyDTO;
import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.service.IBomService;
import com.smartindustry.basic.vo.*;
import com.smartindustry.common.bo.si.BomBodyBO;
import com.smartindustry.common.bo.si.BomHeadBO;
import com.smartindustry.common.bo.si.BomRecordBO;
import com.smartindustry.common.bo.si.MaterialBO;
import com.smartindustry.common.mapper.dd.MaterialPropertyMapper;
import com.smartindustry.common.mapper.dd.ProcessMapper;
import com.smartindustry.common.mapper.om.PickBodyMapper;
import com.smartindustry.common.mapper.si.BomBodyMapper;
import com.smartindustry.common.mapper.si.BomHeadMapper;
import com.smartindustry.common.mapper.si.BomRecordMapper;
import com.smartindustry.common.mapper.si.MaterialMapper;
import com.smartindustry.common.pojo.am.UserPO;
import com.smartindustry.common.pojo.si.BomBodyPO;
import com.smartindustry.common.pojo.si.BomHeadPO;
import com.smartindustry.common.pojo.si.BomRecordPO;
import com.smartindustry.common.security.service.TokenService;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author: jiangzhaojie
 * @date: Created in 10:28 2020/8/13
 * @version: 1.0.0
 * @description:
 */
@EnableTransactionManagement
@Service
public class BomServiceImpl implements IBomService {
    @Autowired
    private BomHeadMapper bomHeadMapper;
    @Autowired
    private BomBodyMapper bomBodyMapper;
    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    private MaterialPropertyMapper propertyMapper;
    @Autowired
    private ProcessMapper processMapper;
    @Autowired
    private BomRecordMapper bomRecordMapper;
    @Autowired
    private TokenService tokenService;



    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<BomHeadBO> page = PageQueryUtil.startPage(reqData);
        List<BomHeadBO> bos = bomHeadMapper.pageQuery(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), BomHeadVO.convert(bos)));
    }

    /**
     * 生成BOM清单时的主物料选择列表查询
     *
     * @param reqData
     * @return
     */
    @Override
    public ResultVO pageQueryBom(Map<String, Object> reqData) {
        Page<MaterialBO> page = PageQueryUtil.startPage(reqData);
        List<MaterialBO> pos = materialMapper.pageQueryBom(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), MaterialBomVO.convert(pos)));
    }


    /**
     * 新增主bom清单
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO insert(OperateDTO dto) {
        UserPO user = tokenService.getLoginUser();
        List<BomHeadPO> pos = bomHeadMapper.judgeHaveBom(dto.getMid());
        if (!pos.isEmpty()) {
            //已经有当前物料的主BOM清单
            return new ResultVO(1004);
        } else {
            BomHeadPO po = new BomHeadPO();
            po.setMaterialId(dto.getMid());
            po.setUserId(1L);
            po.setRelateNum(BasicConstant.NUM_RELATE_START);
            po.setCreateTime(new Date());
            po.setDr((byte) 1);
            bomHeadMapper.insert(po);
            bomRecordMapper.insert(new BomRecordPO(po.getBomHeadId(), user.getUserId(), new Date(), BasicConstant.RECORD_ADD));
        }
        return ResultVO.ok();
    }

    /**
     * 删除主BOM清单
     *
     * @param bhids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO delete(List<Long> bhids) {
        List<Long> bbids = new ArrayList<>();
        for (Long bhid : bhids) {
            List<Long> ids = bomBodyMapper.queryByBhid(bhid);
            if (!ids.isEmpty()) {
                bbids.addAll(ids);
            }
            //该物料有关联单据，不可删除(工单、出入库单)
            List<Long> pbs = bomHeadMapper.queryByMid(bhid);
            if(!pbs.isEmpty()){
                return new ResultVO(1007);
            }
        }
        if(!bbids.isEmpty()){
            bomBodyMapper.deleteBatch(bbids);
        }
        bomHeadMapper.deleteBatch(bhids);
        return ResultVO.ok();
    }

    /**
     * 批量删除物料明细
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO deleteBody(OperateDTO dto) {
        for(Long bbid:dto.getBbids()){
            BomBodyPO po = bomBodyMapper.queryByPid(bbid);
            if(po != null){
                //当前物料明细存在关联,不可删除
                return new ResultVO(1007);
            }
        }
        bomBodyMapper.deleteBodyBatch(dto.getBbids());
        //根据bhid查询当前物料明细的所有相关物料，若批量删除，则更新相关物料数目
        BomHeadPO bhpo = bomHeadMapper.selectByPrimaryKey(dto.getBhid());
        bhpo.setRelateNum(bhpo.getRelateNum() - dto.getBbids().size());
        bomHeadMapper.updateByPrimaryKey(bhpo);
        return ResultVO.ok();
    }

    /**
     * 根据主bom的ID查询得到所有层级物料明细
     * 传入"bhid"
     * @param dto
     * @return
     */
    @Override
    public ResultVO queryTreeBom(OperateDTO dto) {
        MaterialItemsVO vo = queryTree(dto.getBhid());
        return ResultVO.ok().setData(vo);
    }

    private MaterialItemsVO queryTree(Long bomHeadId){
        //从主BOM清单查询根节点
        BomHeadBO bo = bomHeadMapper.queryMainMaterial(bomHeadId);
        MaterialItemsVO vo = MaterialItemsVO.convert(bo);
        //根据物料明细id查询出其子物料明细
        List<BomHeadBO> bos = bomBodyMapper.queryChildren(null, vo.getBhid());
        List<MaterialItemsVO> children = getBomTreeList(MaterialItemsVO.convert(bos));
        vo.setChildren(children);
        return vo;
    }

    /**
     * 递归
     */
    private List<MaterialItemsVO> getBomTreeList(List<MaterialItemsVO> vos) {
        for (MaterialItemsVO vo : vos) {
            List<BomHeadBO> bos = bomBodyMapper.queryChildren(vo.getBbid(), vo.getBhid());
            if (bos != null && bos.size() != 0) {
                List<MaterialItemsVO> vosTemp = getBomTreeList(MaterialItemsVO.convert(bos));
                vo.setChildren(vosTemp);
            }
        }
        return vos;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO editDetail(BomBodyDTO dto) {

        //在编辑时不能让上级物料明细id和本身的物料明细id相同
        if (dto.getBbid()!= null && dto.getPid() != null && dto.getBbid().equals(dto.getPid())){
            return new ResultVO(1025);
        }
        UserPO user = tokenService.getLoginUser();
        BomBodyPO po1 = bomBodyMapper.queryParentId(dto.getPid());
        int curDeep = (dto.getPid() == null)? 2:po1.getLevel()+1;
        if(curDeep > BasicConstant.LEVEL_LIMIT){
            return new ResultVO(1024);
        }
        if (dto.getBbid() == null) {
            List<BomBodyPO> pos1 = bomBodyMapper.judgeHaveBody(dto.getMid(), dto.getBhid());
            if (!pos1.isEmpty()) {
                //已经有当前物料的主BOM清单
                return new ResultVO(1004);
            } else {
                BomBodyPO po = BomBodyDTO.createPO(dto,curDeep);
                bomBodyMapper.insert(po);
                // 将主BOM清单中的关联物料+1
                BomHeadPO bhpo = bomHeadMapper.selectByPrimaryKey(dto.getBhid());
                bhpo.setRelateNum(bhpo.getRelateNum() + 1);
                bomHeadMapper.updateByPrimaryKey(bhpo);
            }
        } else {
            BomBodyPO po = bomBodyMapper.selectByPrimaryKey(dto.getBbid());
            BomBodyPO po2 = BomBodyDTO.buildPO(po, dto,curDeep);
            bomBodyMapper.updateByPrimaryKey(po2);
        }
        bomRecordMapper.insert(new BomRecordPO(dto.getBhid(), user.getUserId(), new Date(), BasicConstant.RECORD_MODIFY));
        return ResultVO.ok();
    }

    @Override
    public ResultVO queryBomBody(Map<String, Object> reqData) {
        Page<BomBodyBO> page = PageQueryUtil.startPage(reqData);
        List<BomBodyBO> bos = bomBodyMapper.pageQuery(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), BomBodyVO.convert(bos)));
    }

    @Override
    public ResultVO queryBomRecord(OperateDTO dto) {
        List<BomRecordBO> bos = bomRecordMapper.queryByBhid(dto.getBhid());
        Map<String, Object> map = new HashMap<>();
        map.put("record", bos.isEmpty() ? null : BomRecordVO.convert(bos));
        return ResultVO.ok().setData(map);
    }
}
