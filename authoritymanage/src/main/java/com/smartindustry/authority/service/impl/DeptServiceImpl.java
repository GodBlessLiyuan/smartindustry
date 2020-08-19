package com.smartindustry.authority.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.authority.constant.AuthorityConstant;
import com.smartindustry.authority.dto.DeptDTO;
import com.smartindustry.authority.dto.OperateDTO;
import com.smartindustry.authority.service.IDeptService;
import com.smartindustry.authority.vo.DeptRecordVO;
import com.smartindustry.authority.vo.DeptVO;
import com.smartindustry.authority.vo.UserVO;
import com.smartindustry.common.bo.am.DeptBO;
import com.smartindustry.common.bo.am.DeptRecordBO;
import com.smartindustry.common.mapper.am.DeptMapper;
import com.smartindustry.common.mapper.am.DeptRecordMapper;
import com.smartindustry.common.mapper.am.UserMapper;
import com.smartindustry.common.pojo.am.DeptPO;
import com.smartindustry.common.pojo.am.DeptRecordPO;
import com.smartindustry.common.pojo.am.UserPO;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.util.ServletUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:24 2020/7/30
 * @version: 1.0.0
 * @description:
 */
@EnableTransactionManagement
@Service
public class DeptServiceImpl implements IDeptService {
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DeptRecordMapper deptRecordMapper;
    @Override
    public ResultVO pageQuery(Map<String, Object> reqData){
        Page<DeptBO> page = PageQueryUtil.startPage(reqData);
        List<DeptBO> bos = deptMapper.deptPageQuery(reqData);
        List<DeptVO> vos = DeptVO.convert(bos);
        for (DeptVO vo:vos){
            List<Long> deptIds = getDeptLevel(vo.getDid());
            Collections.reverse(deptIds);
            vo.setDcode(deptIds.subList(0,deptIds.size()-1));
        }
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), vos));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO batchUpdate(List<OperateDTO> dtos){
        UserPO user = ServletUtil.getUserBO().getUser();
        List<DeptPO> pos = DeptDTO.updateList(dtos);
        deptMapper.updateBatch(pos);
        for(OperateDTO dto:dtos){
            if(dto.getStatus().equals(AuthorityConstant.STATUS_DISABLE)){
                deptRecordMapper.insert(new DeptRecordPO(dto.getDid(),user.getUserId(),new Date(), AuthorityConstant.RECORD_DISABLE));
            }else {
                deptRecordMapper.insert(new DeptRecordPO(dto.getDid(),user.getUserId(),new Date(), AuthorityConstant.RECORD_USE));
            }
        }
        return ResultVO.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO insert(DeptDTO dto){
        UserPO user = ServletUtil.getUserBO().getUser();
        Integer result = deptMapper.judgeRepeatName(dto.getDname(),dto.getDid());
        if(result.equals(1)){
            return new ResultVO(1004);
        }
        DeptPO po = DeptDTO.createPO(dto);
        deptMapper.insert(po);
        deptRecordMapper.insert(new DeptRecordPO(po.getDeptId(),user.getUserId(),new Date(), AuthorityConstant.RECORD_INSERT));
        return ResultVO.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO update(DeptDTO dto){
        UserPO user = ServletUtil.getUserBO().getUser();
        Integer result = deptMapper.judgeRepeatName(dto.getDname(),dto.getDid());
        if(result.equals(1)){
            return new ResultVO(1004);
        }
        if (dto.getDid().equals(dto.getPid())){
            return new ResultVO(1005);
        }
        DeptPO po1 = deptMapper.selectByPrimaryKey(dto.getPid());
        if(po1.getParentId()!=null && po1.getParentId().equals(dto.getDid())){
            return new ResultVO(1008);
        }
        DeptPO po = DeptDTO.createPO(dto);
        deptMapper.updateByPrimaryKeySelective(po);
        deptRecordMapper.insert(new DeptRecordPO(dto.getDid(),user.getUserId(),new Date(), AuthorityConstant.RECORD_UPDATE));
        return ResultVO.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO delete(List<Long> dids){
        UserPO user = ServletUtil.getUserBO().getUser();
        if (dids.contains(1L)){
            dids.remove(1L);
        }
        for(Long did:dids){
            List<DeptPO> pos = deptMapper.queryByParent(did);
            if(pos!=null && pos.size()!=0){
               return new ResultVO(1009);
            }else {
                deptMapper.deleteByPrimaryKey(did);
            }
            deleteDept(did);
            deptRecordMapper.insert(new DeptRecordPO(did,user.getUserId(),new Date(), AuthorityConstant.RECORD_DELETE));
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
            if(deptMapper.judgeExist(vo.getDid()).equals(1)){
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

    /**
     * 若禁用或者删除某个部门id,那么相关联的部门的上级部门置为null,用户的关联部门置为null
     * @param deptId
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteDept(Long deptId){
        deptMapper.updateParentId(deptId);
        userMapper.updateDeptId(deptId);
    }

    @Override
    public ResultVO queryDeptRecord(Map<String, Object> reqData){
        List<DeptRecordBO> bos = deptRecordMapper.queryDeptRecord(reqData);
        return ResultVO.ok().setData(DeptRecordVO.convert(bos));
    }

}
