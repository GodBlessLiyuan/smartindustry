package com.smartindustry.authority.controller;

import com.google.code.kaptcha.Producer;
import com.smartindustry.authority.constant.Constants;
import com.smartindustry.authority.dto.LoginDTO;
import com.smartindustry.authority.service.ILoginService;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:04 2020/8/3
 * @version: 1.0.0
 * @description:
 */
@RequestMapping("login")
@RestController
public class LoginController {
    @Autowired
    ILoginService loginService;
    @Autowired
    private Producer producer;

    @RequestMapping("getCode")
    public void captcha(HttpServletRequest request, HttpServletResponse response)throws IOException {
        HttpSession session = request.getSession();
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        //获取图片验证码
        String capText = producer.createText();
        session.setAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY, capText);
        //向客户端写出
        BufferedImage bi = producer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

    @PostMapping("/login")
    public ResultVO login(HttpSession session,
                          HttpServletResponse response, @RequestBody LoginDTO dto){
        return loginService.login(session,response,dto);
    }

    /**
     * 获得用户详情 以及 所有的 权限列表
     * @return
     */
    @PostMapping("/getInfo")
    public ResultVO getInfo(){
        return ResultVO.ok();
    }
}
