package com.smartindustry.authority.controller;

import com.google.code.kaptcha.Producer;
import com.smartindustry.authority.constant.AuthorityConstant;
import com.smartindustry.authority.dto.LoginDTO;
import com.smartindustry.authority.dto.OperateDTO;
import com.smartindustry.authority.service.ILoginService;
import com.smartindustry.authority.util.RedisCache;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
    public ResultVO captcha(HttpServletRequest request, HttpServletResponse response)throws IOException {
        HttpSession session = request.getSession();
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        String png_base64 = null;
        //获取图片验证码
        String capText = producer.createText();
        System.out.println("验证码"+capText);
        session.setAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY, capText);
        //向客户端写出
        BufferedImage bi = producer.createImage(capText);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        BASE64Encoder encoder = new BASE64Encoder();
        png_base64 = encoder.encodeBuffer(bytes).trim();
        png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");
        Map<String,Object> map = new HashMap<>();
        map.put("code",png_base64);
        try {
            baos.flush();
        } finally {
            baos.close();
        }
        return ResultVO.ok().setData(map);
    }

    @PostMapping("/login")
    public ResultVO login(HttpSession session,
                          HttpServletResponse response, @RequestBody LoginDTO dto){
        return loginService.login(session,response,dto);
    }

    /**
     * 获得用户详情 以及 所有的 权限列表(三级权限)
     * @return
     */
    @PostMapping("/getInfo")
    public ResultVO getInfo(@RequestBody OperateDTO dto){
        return loginService.getInfo(dto);
    }

    @PostMapping("/logout")
    public ResultVO logout(){
        return ResultVO.ok();
    }
}
