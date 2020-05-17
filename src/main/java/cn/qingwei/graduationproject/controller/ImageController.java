package cn.qingwei.graduationproject.controller;

import cn.qingwei.graduationproject.service.UserService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping(value = "/img")
public class ImageController {

    /* 注入Kaptcha */
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/code")
    public String defaultKaptcha(HttpServletRequest request, HttpServletResponse response){
        byte[] captchaChallengeAsJpeg;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        /**
         * 生成验证码字符串并保存到session中
         */
        String createText = defaultKaptcha.createText();
        HttpSession session = request.getSession();
        session.setAttribute("code", createText);
        System.out.println("________");
        System.out.println(request.getSession().getAttribute("code"));
        /**
         * 使用生成的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
         */
        BufferedImage challenge = defaultKaptcha.createImage(createText);
        try {
            ImageIO.write(challenge,"jpg",jpegOutputStream);
        } catch (IOException e) {
            log.error("生成图形验证码失败",e);
            throw new IllegalArgumentException("生成图形验证码失败");// 抛出异常，可以不用关心
        }
        /**
         * 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
         */
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires",0);
        response.setContentType("image/jpeg");
        try {
            ServletOutputStream servletOutputStream = response.getOutputStream();
            servletOutputStream.write(captchaChallengeAsJpeg);
            servletOutputStream.flush();
            servletOutputStream.close();
        } catch (IOException e) {
            log.error("输出验证码失败",e);
            throw new IllegalArgumentException("生成图形验证码失败");// 抛出异常，可以不用关心
        }
        return "sss";	// 返回成功提示信息
    }
}

