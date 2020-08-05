package com.smartindustry.authority.service.impl;

import com.google.code.kaptcha.Constants;
import com.smartindustry.authority.dto.LoginDTO;
import com.smartindustry.authority.dto.OperateDTO;
import com.smartindustry.authority.service.ILoginService;
import com.smartindustry.authority.dto.LoginUserDTO;
import com.smartindustry.authority.service.TokenService;
import com.smartindustry.authority.util.SecurityUtils;
import com.smartindustry.authority.vo.AuthorityVO;
import com.smartindustry.authority.vo.DeptVO;
import com.smartindustry.common.bo.am.AuthorityBO;
import com.smartindustry.common.mapper.am.AuthorityMapper;
import com.smartindustry.common.mapper.am.UserMapper;
import com.smartindustry.common.pojo.am.AuthorityPO;
import com.smartindustry.common.pojo.am.UserPO;
import org.springframework.security.core.Authentication;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:23 2020/8/3
 * @version: 1.0.0
 * @description:
 */
@Service
public class LoginServiceImpl implements ILoginService {
    @Autowired
    UserMapper userMapper;
    @Resource
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    AuthorityMapper authorityMapper;
    @Override
    public ResultVO login(HttpSession session,
                        HttpServletResponse response,LoginDTO dto){
        // 得到验证码
        String code = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (null == code) {
            // 验证码过期
            return new ResultVO(2102);
        }
        if (!code.equalsIgnoreCase(dto.getCode())) {
            //验证码错误
            return new ResultVO(2103);
        }
        // 用户验证
        Authentication authentication = null;
        // 生成token
        authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        LoginUserDTO user = (LoginUserDTO) authentication.getPrincipal();
        return ResultVO.ok().setData(tokenService.createToken(user));
    }

    /**
     * 根据当前的用户id得到所有的用户权限,分三级权限,树形结构
     * @param dto
     * @return
     */
    @Override
    public ResultVO getInfo(OperateDTO dto){
        Long userId = dto.getUid();
        //首先根据parentId找到一级菜单权限
        List<AuthorityBO> bos = authorityMapper.queryChildren(null,userId);
        List<AuthorityVO> vos = AuthorityVO.convert(bos);
        Map<String, Object> res = new HashMap<>();
        res.put("menu",getAuthTreeList(vos,userId));


        return ResultVO.ok().setData(res);
    }

    /**
     * 递归查找权限列表
     */
    private List<AuthorityVO> getAuthTreeList(List<AuthorityVO> vos,Long userId){
        for(AuthorityVO vo : vos){
            if (authorityMapper.judgeExist(vo.getAid()).equals(1)){
                List<AuthorityVO> vosTemp = getAuthTreeList(AuthorityVO.convert(authorityMapper.queryChildren(vo.getAid(),userId)),userId);
                vo.setChildren(vosTemp);
            }
        }
        return vos;
    }

}
