//package com.smartindustry.authority.service.impl;
//
//import com.google.code.kaptcha.Constants;
//import com.smartindustry.authority.dto.LoginDTO;
//import com.smartindustry.authority.service.ILoginService;
//import com.smartindustry.common.pojo.am.UserPO;
//import com.smartindustry.common.vo.ResultVO;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
///**
// * @author: jiangzhaojie
// * @date: Created in 9:23 2020/8/3
// * @version: 1.0.0
// * @description:
// */
//@Service
//public class LoginServiceImpl implements ILoginService {
//
////    @Override
////    public ResultVO login(HttpSession session,
////                        HttpServletResponse response,LoginDTO dto){
////
////
////        // 首先根据username查询个人信息
////        UserPO userPo = userMapper.queryByName(dto.getUsername());
////
////        if (null!=userPo) {
////            // 查询到的编码是加密过的
////            String realPassword = userPo.getPassword();
//////            try {
//////                password = Md5Util.encodeByMd5(SALT + password);
//////            } catch (Exception e) {
//////                e.printStackTrace();
//////            }
////            if (!password.equals(realPassword)) {
////                //密码错误
////                return new ResultVO(2101);
////            }
////        } else { //用户名错误
////            return new ResultVO(2100);
////        }
////
////        // 得到验证码
////        String code = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
////        if (null == code) {
////            // 验证码过期
////            return new ResultVO(2102);
////        }
////        if (!code.equalsIgnoreCase(checkcode)) {
////            //验证码错误
////            return new ResultVO(2103);
////        }
////        // 生成token
////
////
////        return ResultVO.ok().setData(11);
////    }
////
//}
