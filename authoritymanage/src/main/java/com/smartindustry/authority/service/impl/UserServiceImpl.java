package com.smartindustry.authority.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.authority.constant.AuthorityConstant;
import com.smartindustry.authority.dto.EditDTO;
import com.smartindustry.common.bo.am.LoginUserBO;
import com.smartindustry.authority.dto.OperateDTO;
import com.smartindustry.authority.dto.UserDTO;
import com.smartindustry.authority.service.IUserService;
import com.smartindustry.authority.vo.RoleVO;
import com.smartindustry.authority.vo.UserRecordVO;
import com.smartindustry.authority.vo.UserVO;
import com.smartindustry.common.bo.am.UserBO;
import com.smartindustry.common.bo.am.UserRecordBO;
import com.smartindustry.common.constant.SecurityConstant;
import com.smartindustry.common.mapper.am.*;
import com.smartindustry.common.pojo.am.DeptPO;
import com.smartindustry.common.pojo.am.RolePO;
import com.smartindustry.common.pojo.am.UserPO;
import com.smartindustry.common.pojo.am.UserRecordPO;
import com.smartindustry.common.security.service.TokenService;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.util.SecurityUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * @author: jiangzhaojie
 * @date: Created in 18:53 2020/7/30
 * @version: 1.0.0
 * @description:
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private AuthorityMapper authorityMapper;
    @Autowired
    private MUserAuthorityMapper mUserAuthorityMapper;
    @Autowired
    private UserRecordMapper userRecordMapper;
    @Autowired
    private TokenService tokenService;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData){
        Page<UserBO> page = PageQueryUtil.startPage(reqData);
        List<UserBO> bos = userMapper.userPageQuery(reqData);
        List<UserVO> vos = UserVO.convert(bos);
        for (UserVO vo:vos){
            List<Long> deptIds = getDeptLevel(vo.getDid());
            Collections.reverse(deptIds);
            vo.setDcode(deptIds);
        }
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), vos));
    }

    /**
     * 根据当前的部门id,获得父节点列表
     * @param deptId
     * @return
     */
    private List<Long> getDeptLevel(Long deptId){
        List<Long> deptIds = new ArrayList<>();
        while (deptId != null){
            DeptPO po = deptMapper.selectByPrimaryKey(deptId);
            deptIds.add(po.getDeptId());
            deptId = po.getParentId();
        }
        return deptIds;
    }

    @Override
    public ResultVO batchUpdate(List<OperateDTO> dtos){
        List<UserPO> pos = UserDTO.updateList(dtos);
        for(UserPO po:pos){
            if(po.isAdmin()){
                return new ResultVO(1023);
            }
        }
        userMapper.updateBatch(pos);
        for(OperateDTO dto:dtos){
            if(dto.getStatus().equals(AuthorityConstant.STATUS_DISABLE)){
                userRecordMapper.insert(new UserRecordPO(dto.getUid(),1L,new Date(), AuthorityConstant.RECORD_DISABLE));
            }else{
                userRecordMapper.insert(new UserRecordPO(dto.getUid(),1L,new Date(), AuthorityConstant.RECORD_USE));
            }
        }
        return ResultVO.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO delete(List<Long> uids){
        userMapper.deleteBatch(uids);
        for(Long uid:uids){
            if(UserPO.isAdmin(uid)){
                return new ResultVO(1023);
            }
            deleteUser(uid);
            userRecordMapper.insert(new UserRecordPO(uid,1L,new Date(), AuthorityConstant.RECORD_DELETE));
        }
        return ResultVO.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO insert(UserDTO dto){
        Integer result = userMapper.judgeRepeatName(dto.getUname(),dto.getUid());
        if(result.equals(1)){
            return new ResultVO(1004);
        }
        UserPO po = UserDTO.createPO(dto);
        userMapper.insert(po);
        userRecordMapper.insert(new UserRecordPO(po.getUserId(),1L,new Date(), AuthorityConstant.RECORD_INSERT));
        return ResultVO.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO update(UserDTO dto){
        Integer result = userMapper.judgeRepeatName(dto.getUname(),dto.getUid());
        if(result.equals(1)){
            return new ResultVO(1004);
        }
        UserPO po = UserDTO.createPO(dto);
        boolean specialUserInfo = OperateDTO.isAdmin(po.getRoleId()) && SecurityConstant.SUPER_ADMIN.equals(po.getUsername());
        if(po.isAdmin() && !specialUserInfo){
            return new ResultVO(1023);
        }
        userMapper.updateByPrimaryKeySelective(po);
        userRecordMapper.insert(new UserRecordPO(dto.getUid(),1L,new Date(), AuthorityConstant.RECORD_UPDATE));
        return ResultVO.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public  ResultVO updateUser(HttpServletRequest session,@RequestBody UserDTO dto){
        LoginUserBO userDto = tokenService.getLoginUser(session);
        //从session中获取userId的值
        Long userId = userDto.getUser().getUserId();
        UserPO po = UserDTO.createPO(dto);
        po.setUserId(userId);
        userMapper.updateByPrimaryKeySelective(po);
        return ResultVO.ok();
    }

    @Override
    public ResultVO updatePassword(OperateDTO dto){
        UserPO po = UserDTO.buildPO(dto);
        if(po.isAdmin()){
            return new ResultVO(1023);
        }
        userMapper.updateByPrimaryKeySelective(po);
        return ResultVO.ok();
    }

    @Override
    public ResultVO queryRole(){
        List<RolePO> pos = roleMapper.selectAll();
        return ResultVO.ok().setData(RoleVO.convert(pos));
    }

    @Override
    public ResultVO queryHavePerms(OperateDTO dto){
        List<Long> perms = authorityMapper.queryByUserId(dto.getUid());
        return ResultVO.ok().setData(perms);
    }

    /**
     * 当禁用或删除某个用户，那么部门表的负责人需要置空，用户权限中间表需要删除
     * @param userId
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long userId){
        deptMapper.updateBossId(userId);
        mUserAuthorityMapper.deleteByUserId(userId);
    }


    @Override
    public ResultVO queryUserRecord(Map<String, Object> reqData){
        List<UserRecordBO> bos = userRecordMapper.queryUserRecord(reqData);
        return ResultVO.ok().setData(UserRecordVO.convert(bos));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO editPassword(HttpServletRequest session,@RequestBody EditDTO dto){
        LoginUserBO userDto = tokenService.getLoginUser(session);
        //从session中获取userId的值
        Long userId = userDto.getUser().getUserId();
        if (userId == null) {
            // 用户过期
            return new ResultVO(1013);
        }
        //根据id,检索到当前用户，以便获得password
        UserPO userPo = userMapper.selectByPrimaryKey(userId);
        // 比较输入旧密码是否等于用户本身存在数据库的密码
        if (!SecurityUtil.matchesPassword(dto.getOpassword(),userPo.getPassword())) {
            // 旧密码输入错误
            return new ResultVO(1023);
        }else {
            // 如果没有问题，则将当前userId用户更新密码
            userMapper.updatePassword(SecurityUtil.encryptPassword(dto.getNpassword()),userId);
        }
        return ResultVO.ok();
    }

    @Override
    public ResultVO queryUserMsg(HttpServletRequest session){
        LoginUserBO dto = tokenService.getLoginUser(session);
        UserBO bo = userMapper.queryUserMsg(dto.getUser().getUserId());
        return ResultVO.ok().setData(UserVO.convertToUserMsg(bo));
    }


}
