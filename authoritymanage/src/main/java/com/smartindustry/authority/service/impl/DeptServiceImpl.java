package com.smartindustry.authority.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.authority.dto.DeptDTO;
import com.smartindustry.authority.dto.OperateDTO;
import com.smartindustry.authority.service.IDeptService;
import com.smartindustry.authority.vo.DeptVO;
import com.smartindustry.authority.vo.UserVO;
import com.smartindustry.common.bo.am.DeptBO;
import com.smartindustry.common.bo.om.OutboundBO;
import com.smartindustry.common.mapper.am.DeptMapper;
import com.smartindustry.common.mapper.am.UserMapper;
import com.smartindustry.common.pojo.am.DeptPO;
import com.smartindustry.common.pojo.am.UserPO;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

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
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData){
        Page<DeptBO> page = PageQueryUtil.startPage(reqData);
        List<DeptBO> bos = deptMapper.deptPageQuery(reqData);
        List<DeptVO> vos = DeptVO.convert(bos);
        for (DeptVO vo:vos){
            List<Long> deptIds = getDeptLevel(vo.getDid());
            Collections.reverse(deptIds);
            vo.setDcode(deptIds);
        }
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), vos));
    }

    @Override
    public ResultVO batchUpdate(List<OperateDTO> dtos){
        List<DeptPO> pos = DeptDTO.updateList(dtos);
        deptMapper.updateBatch(pos);
        return ResultVO.ok();
    }

    @Override
    public ResultVO insert(DeptDTO dto){
        Integer result = deptMapper.judgeRepeatName(dto.getDname(),dto.getDid());
        if(result.equals(1)){
            return new ResultVO(1004);
        }
        DeptPO po = DeptDTO.createPO(dto);
        deptMapper.insert(po);
        return ResultVO.ok();
    }

    @Override
    public ResultVO update(DeptDTO dto){
        Integer result = deptMapper.judgeRepeatName(dto.getDname(),dto.getDid());
        if(result.equals(1)){
            return new ResultVO(1004);
        }
        DeptPO po = DeptDTO.createPO(dto);
        deptMapper.updateByPrimaryKeySelective(po);
        return ResultVO.ok();
    }

    @Override
    public ResultVO delete(List<Long> dids){
        if (dids.contains(1L)){
            dids.remove(1L);
        }
        if(dids.size()!=0){
            deptMapper.deleteBatch(dids);
        }
        return ResultVO.ok();
    }


    @Override
    public ResultVO queryDeptName(){
        //查询根菜单列表
        List<DeptBO> bos = deptMapper.queryChildren(null);
        List<DeptVO> vos = DeptVO.convert(bos);
        return ResultVO.ok().setData(getDeptTreeList(vos));
    }

    /**
     * 递归
     */
    private List<DeptVO> getDeptTreeList(List<DeptVO> vos){
        for(DeptVO vo : vos){
            if(deptMapper.judgeExist(vo.getPid()).equals(1)){
                List<DeptVO> vosTemp = getDeptTreeList(DeptVO.convert(deptMapper.queryChildren(vo.getDid())));
                vo.setChildren(vosTemp);
            }
        }
        return vos;
    }

    /**
     * 根据当前的部门id,获得父节点列表
     * @param deptId
     * @return
     */
    private List<Long> getDeptLevel(Long deptId){
        List<Long> deptIds = new ArrayList<>();
        while (deptId !=null){
            DeptPO po = deptMapper.selectByPrimaryKey(deptId);
            deptIds.add(po.getDeptId());
            deptId = po.getParentId();
        }
        return deptIds;
    }

    @Override
    public ResultVO queryLeader(OperateDTO dto){
        List<UserPO> pos = userMapper.selectAll(dto.getName());
        return ResultVO.ok().setData(UserVO.convertPO(pos));
    }

}
