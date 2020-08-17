package com.smartindustry.basic.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.basic.constant.BasicConstant;
import com.smartindustry.basic.dto.BomBodyDTO;
import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.service.IBomService;
import com.smartindustry.basic.vo.*;
import com.smartindustry.common.bo.si.BomBodyBO;
import com.smartindustry.common.bo.si.BomHeadBO;
import com.smartindustry.common.bo.si.MaterialBO;
import com.smartindustry.common.mapper.dd.MaterialPropertyMapper;
import com.smartindustry.common.mapper.dd.ProcessMapper;
import com.smartindustry.common.mapper.si.BomBodyMapper;
import com.smartindustry.common.mapper.si.BomHeadMapper;
import com.smartindustry.common.mapper.si.MaterialMapper;
import com.smartindustry.common.pojo.si.BomBodyPO;
import com.smartindustry.common.pojo.si.BomHeadPO;
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



    @Override
    public ResultVO pageQuery(Map<String, Object> reqData){
        Page<BomHeadBO> page = PageQueryUtil.startPage(reqData);
        List<BomHeadBO> bos = bomHeadMapper.pageQuery(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), BomHeadVO.convert(bos)));
    }

    /**
     * 生成BOM清单时的主物料选择列表查询
     * @param reqData
     * @return
     */
    @Override
    public ResultVO pageQueryBom(Map<String, Object> reqData){
        Page<MaterialBO> page = PageQueryUtil.startPage(reqData);
        List<MaterialBO> pos = materialMapper.pageQueryBom(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), MaterialBomVO.convert(pos)));
    }

    /**
     * 新增主bom清单
     * @param dto
     * @return
     */
    @Override
    public ResultVO insert(OperateDTO dto){
        List<BomHeadPO> pos = bomHeadMapper.judgeHaveBom(dto.getMid());
        if (!pos.isEmpty()){
            //已经有当前物料的主BOM清单
            return new ResultVO(1004);
        }else{
            BomHeadPO po = new BomHeadPO();
            po.setMaterialId(dto.getMid());
            po.setUserId(1L);
            po.setRelateNum(BasicConstant.NUM_RELATE_START);
            po.setCreateTime(new Date());
            po.setDr((byte)1);
            bomHeadMapper.insert(po);
        }
        return ResultVO.ok();
    }

    /**
     * 删除主BOM清单
     * @param bhids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO delete(List<Long> bhids){
        for(Long bhid:bhids){
            List<BomBodyPO> pos = bomBodyMapper.queryByBhid(bhid);
            if(!pos.isEmpty()){
                bomBodyMapper.deleteBatch(pos);
            }
            bomHeadMapper.deleteByPrimaryKey(bhid);
        }
        return ResultVO.ok();
    }

    /**
     * 根据主bom的ID查询得到所有层级物料明细
     * @param dto
     * @return
     */
    @Override
    public ResultVO queryTreeBom(OperateDTO dto){
        //从主BOM清单查询根节点
        BomHeadBO bo = bomHeadMapper.queryMainMaterial(dto.getBhid());
        MaterialItemsVO vo = MaterialItemsVO.convert(bo);
        List<BomHeadBO> bos = bomBodyMapper.queryChildren(vo.getMid());
        List<MaterialItemsVO> children= getBomTreeList(MaterialItemsVO.convert(bos));
        vo.setChildren(children);
        return ResultVO.ok().setData(vo);
    }

    /**
     * 递归
     */
    private List<MaterialItemsVO> getBomTreeList(List<MaterialItemsVO> vos){
        for(MaterialItemsVO vo : vos){
            List<BomHeadBO> bos = bomBodyMapper.queryChildren(vo.getMid());
            if(bos!=null && bos.size()!=0){
                List<MaterialItemsVO> vosTemp = getBomTreeList(MaterialItemsVO.convert(bos));
                vo.setChildren(vosTemp);
            }
        }
        return vos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO insertDetail(BomBodyDTO dto){
        List<BomBodyPO> pos1 = bomBodyMapper.judgeHaveBody(dto.getMid(),dto.getBhid());
        if (!pos1.isEmpty()){
            //已经有当前物料的主BOM清单
            return new ResultVO(1004);
        }else{
            BomBodyPO po = BomBodyDTO.createPO(dto);
            bomBodyMapper.insert(po);
            // 将主BOM清单中的关联物料+1
            BomHeadPO bhpo = bomHeadMapper.selectByPrimaryKey(dto.getBhid());
            bhpo.setRelateNum(bhpo.getRelateNum()+1);
            bomHeadMapper.updateByPrimaryKey(bhpo);
        }
        return ResultVO.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO updateDetail(BomBodyDTO dto){
        BomBodyPO po = bomBodyMapper.selectByPrimaryKey(dto.getBbid());
        BomBodyPO po1 = BomBodyDTO.buildPO(po,dto);
        bomBodyMapper.updateByPrimaryKey(po1);
        return ResultVO.ok();
    }


    @Override
    public ResultVO queryBomBody(Map<String, Object> reqData){
        Page<BomBodyBO> page = PageQueryUtil.startPage(reqData);
        List<BomBodyBO> bos = bomBodyMapper.pageQuery(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), BomBodyVO.convert(bos)));
    }
}
