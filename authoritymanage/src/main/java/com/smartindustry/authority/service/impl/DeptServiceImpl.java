package com.smartindustry.authority.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.authority.dto.DeptDTO;
import com.smartindustry.authority.service.IDeptService;
import com.smartindustry.authority.vo.DeptVO;
import com.smartindustry.common.bo.am.DeptBO;
import com.smartindustry.common.bo.om.OutboundBO;
import com.smartindustry.common.mapper.am.DeptMapper;
import com.smartindustry.common.pojo.am.DeptPO;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:24 2020/7/30
 * @version: 1.0.0
 * @description:
 */
@Service
public class DeptServiceImpl implements IDeptService {
    @Autowired
    private DeptMapper deptMapper;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData){
        Page<DeptBO> page = PageQueryUtil.startPage(reqData);
        List<DeptBO> bos = deptMapper.deptPageQuery(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), DeptVO.convert(bos)));
    }

    @Override
    public ResultVO updateBatch(List<DeptDTO> dtos){
        List<DeptPO> pos = DeptDTO.updateList(dtos);
        deptMapper.updateBatch(pos);
        return ResultVO.ok();
    }

    @Override
    public ResultVO insert(DeptDTO dto){
        DeptPO po = DeptDTO.insertPO(dto);
        deptMapper.insert(po);
        return ResultVO.ok();
    }

    @Override
    public ResultVO deleteBatch(List<DeptDTO> dtos){
        List<DeptPO> pos = DeptDTO.updateList(dtos);
        deptMapper.deleteBatch(pos);
        return ResultVO.ok();
    }


    @Override
    public ResultVO queryDeptName(){
        //查询根菜单列表
        List<DeptBO> bos = deptMapper.queryChildren(null);
        return ResultVO.ok().setData(getDeptTreeList(bos));
    }

    /**
     * 递归
     */
    private List<DeptBO> getDeptTreeList(List<DeptBO> deptList){
        for(DeptBO bo : deptList){
            if(deptMapper.judgeExist(bo.getParentId()).equals(1)){
                List<DeptBO> bos = getDeptTreeList(deptMapper.queryChildren(bo.getDeptId()));
                bo.setChildren(bos);
            }
        }
        return deptList;
    }


}
