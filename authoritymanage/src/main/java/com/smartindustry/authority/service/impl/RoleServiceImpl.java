package com.smartindustry.authority.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.github.pagehelper.Page;
import com.smartindustry.authority.constant.AuthorityConstant;
import com.smartindustry.authority.dto.OperateDTO;
import com.smartindustry.authority.dto.RoleDTO;
import com.smartindustry.authority.service.IRoleService;
import com.smartindustry.authority.vo.AuthorityVO;
import com.smartindustry.authority.vo.RoleRecordVO;
import com.smartindustry.authority.vo.RoleVO;
import com.smartindustry.common.bo.am.AuthorityBO;
import com.smartindustry.common.bo.am.RoleRecordBO;
import com.smartindustry.common.mapper.am.*;
import com.smartindustry.common.pojo.am.RolePO;
import com.smartindustry.common.pojo.am.RoleRecordPO;
import com.smartindustry.common.pojo.am.UserPO;
import com.smartindustry.common.security.service.TokenService;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.util.ServletUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:27 2020/7/31
 * @version: 1.0.0
 * @description:
 */
@EnableTransactionManagement
@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AuthorityMapper authorityMapper;
    @Autowired
    private RoleAuthorityMapper roleAuthorityMapper;
    @Autowired
    private MUserAuthorityMapper mUserAuthorityMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleRecordMapper roleRecordMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    TokenService tokenService;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData){
        Page<RolePO> page = PageQueryUtil.startPage(reqData);
        List<RolePO> pos = roleMapper.rolePageQuery(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), RoleVO.convert(pos)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO batchUpdate(List<OperateDTO> dtos){
        UserPO user = tokenService.getLoginUser();
        if (OperateDTO.hasAdmin(dtos)){
            return new ResultVO(1023);
        }
        List<RolePO> pos = RoleDTO.updateList(dtos);
        roleMapper.updateBatch(pos);
        for(OperateDTO dto:dtos){
            if(dto.getStatus().equals(AuthorityConstant.STATUS_DISABLE)){
                roleRecordMapper.insert(new RoleRecordPO(dto.getRid(),user.getUserId(), AuthorityConstant.RECORD_DISABLE));
            }else{
                roleRecordMapper.insert(new RoleRecordPO(dto.getRid(),user.getUserId(), AuthorityConstant.RECORD_USE));
            }
        }
        return ResultVO.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO insert(RoleDTO dto){
        Integer result = roleMapper.judgeRepeatName(dto.getRname(),dto.getRid());
        if(result.equals(1)){
            return new ResultVO(1004);
        }
        RolePO po = RoleDTO.createPO(dto);
        roleMapper.insert(po);
        roleRecordMapper.insert(new RoleRecordPO(po.getRoleId(),1L, AuthorityConstant.RECORD_INSERT));
        return ResultVO.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO update(RoleDTO dto){
        Integer result = roleMapper.judgeRepeatName(dto.getRname(),dto.getRid());
        if(result.equals(1)){
            return new ResultVO(1004);
        }
        RolePO po = RoleDTO.createPO(dto);
        if(OperateDTO.isAdmin(po.getRoleId())){
            return new ResultVO(1023);
        }
        roleMapper.updateByPrimaryKeySelective(po);
        roleRecordMapper.insert(new RoleRecordPO(dto.getRid(),1L, AuthorityConstant.RECORD_UPDATE));
        return ResultVO.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO delete(List<Long> rids){
        if(OperateDTO.isAdmin(rids)){
            return new ResultVO(1023);
        }
        for(Long rid:rids){
            List<UserPO> pos = userMapper.queryUserRole(rid);
            if(!pos.isEmpty()){
                return new ResultVO(1007);
            }
            deleteRole(rid);
            roleRecordMapper.insert(new RoleRecordPO(rid,1L, AuthorityConstant.RECORD_DELETE));
        }
        roleMapper.deleteBatch(rids);
        return ResultVO.ok();
    }

    @Override
    public ResultVO queryAuthority(){
        Object SysObject = redisTemplate.opsForValue().get(AuthorityConstant.NAME_SYS_AUTH);
        List<AuthorityVO> result;
        if(SysObject == null){
            List<AuthorityBO> bos = authorityMapper.queryChild(null);
            List<AuthorityVO> vos = AuthorityVO.convert(bos);
            List<AuthorityVO> lastMenu = new ArrayList<>();
            result = getAuthTree(vos,lastMenu);
            redisTemplate.opsForValue().set(AuthorityConstant.NAME_SYS_AUTH, result, AuthorityConstant.EXPIRE_TIME, TimeUnit.DAYS);
        }else {
            result = JSON.parseObject(JSON.toJSONString(SysObject), List.class,Feature.OrderedField);
        }
        return ResultVO.ok().setData(result);
    }

    /**
     * 递归查找权限列表
     */
    private List<AuthorityVO> getAuthTree(List<AuthorityVO> vos,List<AuthorityVO> lastMenu){
        for(AuthorityVO vo : vos){
            if (authorityMapper.judgeExist(vo.getAid()).equals(AuthorityConstant.FLAG_EXIST)){
                List<AuthorityVO> vosTemp = getAuthTree(AuthorityVO.convert(authorityMapper.queryChild(vo.getAid())),lastMenu);
                vo.setChildren(vosTemp);
            }else {
                lastMenu.add((AuthorityVO) vo.clone());
            }
        }
        return vos;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO updatePerms(OperateDTO dto){
        if(OperateDTO.isAdmin(dto.getRid())){
            return new ResultVO(1023);
        }
        // 先删除角色权限表关于当前角色的所有权限
        roleAuthorityMapper.deleteByRoleId(dto.getRid());
        // 再根据权限id列表更新角色权限表
        if(dto.getPerms() != null && dto.getPerms().size()!=0){
            roleAuthorityMapper.insertBatch(dto.getRid(),dto.getPerms());
            // 先根据角色id找出所有符合用户角色的用户id
            List<Long> uids = userMapper.queryUser(dto.getRid());
            // 再更新用户权限中间表
            if(!uids.isEmpty()){
                mUserAuthorityMapper.deleteByUserId(uids);
                mUserAuthorityMapper.insertBatch(uids,dto.getPerms());
            }
        }
        return ResultVO.ok();
    }

    @Override
    public ResultVO queryRolePerms(OperateDTO dto){
        List<Long> perms = roleAuthorityMapper.queryByRoleId(dto.getRid());
        return ResultVO.ok().setData(perms);
    }

    /**
     * 若某个角色被禁用或删除，那么相应的用户表关联角色置空，角色权限表关联角色全部删除
     * @param roleId
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Long roleId){
        roleAuthorityMapper.deleteByRoleId(roleId);
    }

    @Override
    public ResultVO queryRoleRecord(Map<String, Object> reqData){
        List<RoleRecordBO> bos = roleRecordMapper.queryRoleRecord(reqData);
        return ResultVO.ok().setData(RoleRecordVO.convert(bos));
    }


}
