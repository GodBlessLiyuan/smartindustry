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

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData){
        Page<RolePO> page = PageQueryUtil.startPage(reqData);
        List<RolePO> pos = roleMapper.rolePageQuery(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), RoleVO.convert(pos)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO batchUpdate(List<OperateDTO> dtos){
        UserPO user = ServletUtil.getUserBO().getUser();
        if (OperateDTO.hasAdmin(dtos)){
            return new ResultVO(1023);
        }
        List<RolePO> pos = RoleDTO.updateList(dtos);
        roleMapper.updateBatch(pos);
        for(OperateDTO dto:dtos){
            if(dto.getStatus().equals(AuthorityConstant.STATUS_DISABLE)){
                roleRecordMapper.insert(new RoleRecordPO(dto.getRid(),user.getUserId(),new Date(), AuthorityConstant.RECORD_DISABLE));
            }else{
                roleRecordMapper.insert(new RoleRecordPO(dto.getRid(),user.getUserId(),new Date(), AuthorityConstant.RECORD_USE));
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
        roleRecordMapper.insert(new RoleRecordPO(po.getRoleId(),1L,new Date(), AuthorityConstant.RECORD_INSERT));
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
        roleRecordMapper.insert(new RoleRecordPO(dto.getRid(),1L,new Date(), AuthorityConstant.RECORD_UPDATE));
        return ResultVO.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO delete(List<Long> rids){
        if(OperateDTO.isAdmin(rids)){
            return new ResultVO(1023);
        }
        for(Long rid:rids){
            deleteRole(rid);
            roleRecordMapper.insert(new RoleRecordPO(rid,1L,new Date(), AuthorityConstant.RECORD_DELETE));
        }
        roleMapper.deleteBatch(rids);
        return ResultVO.ok();
    }

    @Override
    public ResultVO queryAllMenu() {
        Object menuObject = redisTemplate.opsForValue().get(AuthorityConstant.NAME_MENU);
        Map<String, Object> menuMap = new LinkedHashMap<>();
        if(menuObject == null){
            List<AuthorityBO> bos = authorityMapper.queryChild(null, AuthorityConstant.TYPE_MENU);
            List<AuthorityVO> vos = AuthorityVO.convert(bos);
            List<AuthorityVO> lastMenu = new ArrayList<>();
            menuMap.put(AuthorityConstant.NAME_MENU,getAuthTree(vos,lastMenu, AuthorityConstant.TYPE_MENU));
            redisTemplate.opsForValue().set(AuthorityConstant.NAME_MENU, menuMap, AuthorityConstant.EXPIRE_TIME, TimeUnit.DAYS);
        }else {
            menuMap = JSON.parseObject(JSON.toJSONString(menuObject),LinkedHashMap.class,Feature.OrderedField);
        }
        return ResultVO.ok().setData(menuMap);
    }

    @Override
    public ResultVO queryAllButton(){
        Object buttonObject = redisTemplate.opsForValue().get(AuthorityConstant.NAME_BUTTON);
        Map<String, Object> res = new HashMap<>();
        if(buttonObject == null){
            List<AuthorityBO> bos = authorityMapper.queryChild(null, AuthorityConstant.TYPE_MENU);
            List<AuthorityVO> vos = AuthorityVO.convert(bos);

            List<AuthorityVO> lastMenu = new ArrayList<>();
            getAuthTree(vos,lastMenu, AuthorityConstant.TYPE_MENU);
            List<AuthorityVO> lastButton = new ArrayList<>();
            CollectionUtils.addAll(lastButton, new Object[lastMenu.size()]);
            Collections.copy(lastButton,lastMenu);
            res.put(AuthorityConstant.NAME_BUTTON,getAuthTree(lastMenu,new ArrayList<>(), AuthorityConstant.TYPE_BUTTON));
            redisTemplate.opsForValue().set(AuthorityConstant.NAME_BUTTON, res, AuthorityConstant.EXPIRE_TIME, TimeUnit.DAYS);
        }else {
            res = JSON.parseObject(JSON.toJSONString(buttonObject), LinkedHashMap.class,Feature.OrderedField);
        }
        return ResultVO.ok().setData(res);
    }


    /**
     * 递归查找权限列表
     */
    private List<AuthorityVO> getAuthTree(List<AuthorityVO> vos,List<AuthorityVO> lastMenu,Byte type){
        for(AuthorityVO vo : vos){
            if (authorityMapper.judgeExist(vo.getAid(),type).equals(1)){
                List<AuthorityVO> vosTemp = getAuthTree(AuthorityVO.convert(authorityMapper.queryChild(vo.getAid(),type)),lastMenu,type);
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
        if(dto.getPerms().size()!=0){
            roleAuthorityMapper.insertBatch(dto.getRid(),dto.getPerms());
            // 先根据角色id找出所有符合用户角色的用户id
            List<Long> uids = userMapper.queryUser(dto.getRid());
            // 再更新用户权限中间表
            for(Long uid:uids){
                mUserAuthorityMapper.deleteByUserId(uid);
                mUserAuthorityMapper.insertBatch(uid,dto.getPerms());
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
        userMapper.updateRoleId(roleId);
        roleAuthorityMapper.deleteByRoleId(roleId);
    }

    @Override
    public ResultVO queryRoleRecord(Map<String, Object> reqData){
        List<RoleRecordBO> bos = roleRecordMapper.queryRoleRecord(reqData);
        return ResultVO.ok().setData(RoleRecordVO.convert(bos));
    }

}
