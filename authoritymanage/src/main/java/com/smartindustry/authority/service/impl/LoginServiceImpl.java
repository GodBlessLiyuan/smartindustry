package com.smartindustry.authority.service.impl;

import com.smartindustry.authority.constant.AuthorityConstant;

import com.smartindustry.authority.dto.LoginDTO;
import com.smartindustry.authority.dto.OperateDTO;
import com.smartindustry.authority.service.ILoginService;
import com.smartindustry.common.bo.am.LoginUserBO;
import com.smartindustry.authority.vo.AuthorityVO;
import com.smartindustry.common.bo.am.AuthorityBO;
import com.smartindustry.common.constant.SecurityConstant;
import com.smartindustry.common.mapper.am.AuthorityMapper;
import com.smartindustry.common.mapper.am.UserMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import com.smartindustry.common.security.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:23 2020/8/3
 * @version: 1.0.0
 * @description:
 */
@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthorityMapper authorityMapper;

    @Override
    public ResultVO login(HttpSession session,
                          HttpServletResponse response, LoginDTO dto) {
        // 得到验证码
        String code = (String) session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        if (null == code) {
            // 验证码过期
            return new ResultVO(1010);
        }
        if (!code.equalsIgnoreCase(dto.getCode())) {
            //验证码错误
            return new ResultVO(1011);
        }
        // 用户验证
        Authentication authentication = null;
        // 生成token  该方法会去调用UserDetailsServiceImpl.loadUserByUsername
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                //输入密码错误
                return new ResultVO(1012);
            }
        }
        LoginUserBO user = (LoginUserBO) authentication.getPrincipal();
        Map<String, Object> map = new HashMap<>();
        map.put(SecurityConstant.SESSION_TOKEN, tokenService.createToken(user));
        map.put(SecurityConstant.SESSION_USER, user);
        session.setAttribute(SecurityConstant.SESSION_USER, user);
        session.setMaxInactiveInterval(30*60*60);
        return ResultVO.ok().setData(map);
    }

    /**
     * 根据当前的角色id得到所有的角色权限,分三级权限,树形结构
     *
     * @param dto
     * @return
     */
    @Override
    public ResultVO getInfo(OperateDTO dto) {
        Long roleId = dto.getRid();
        //首先根据parentId找到一级菜单权限
        List<AuthorityBO> bos = authorityMapper.queryChildren(null, roleId, AuthorityConstant.TYPE_MENU);
        List<AuthorityVO> vos = AuthorityVO.convert(bos);
        Map<String, Object> res = new LinkedHashMap<>();
        List<AuthorityVO> lastMenu = new ArrayList<>();
        res.put(AuthorityConstant.NAME_MENU, getAuthTreeList(vos, roleId, lastMenu, AuthorityConstant.TYPE_MENU));

        List<AuthorityVO> lastButton = new ArrayList<>();
        CollectionUtils.addAll(lastButton, new Object[lastMenu.size()]);
        Collections.copy(lastButton, lastMenu);

        res.put(AuthorityConstant.NAME_BUTTON, getAuthTreeList(lastMenu, roleId, new ArrayList<>(), AuthorityConstant.TYPE_BUTTON));
        return ResultVO.ok().setData(res);
    }

    /**
     * 递归查找权限列表
     */
    private List<AuthorityVO> getAuthTreeList(List<AuthorityVO> vos, Long roleId, List<AuthorityVO> lastMenu, Byte type) {
        for (AuthorityVO vo : vos) {
            if (authorityMapper.judgeExist(vo.getAid(), type).equals(1)) {
                List<AuthorityVO> vosTemp = getAuthTreeList(AuthorityVO.convert(authorityMapper.queryChildren(vo.getAid(), roleId, type)), roleId, lastMenu, type);
                vo.setChildren(vosTemp);
            } else {
                lastMenu.add((AuthorityVO) vo.clone());
            }
        }
        return vos;
    }


}
