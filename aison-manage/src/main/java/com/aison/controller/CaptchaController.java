package com.aison.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 验证码
 */
@Api(value = "验证码")
@RestController
public class CaptchaController {
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @GetMapping(value = "/captcha")
    @ApiModelProperty(value = "验证码")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setDateHeader("Expires",0);
        response.setHeader("Cache-Control","no-store,no-cache,must-revalidate");
        response.addHeader("Cache-Control","post-check=0,pre-check=0");
        response.setHeader("Pragma","no-cache");
        response.setContentType("image/jpeg");
        //-------发送验证码
        String text = defaultKaptcha.createText();
        System.out.println("text:"+text);
        request.getSession().setAttribute("captcha",text);
        BufferedImage bufferedImage = defaultKaptcha.createImage(text);
        ServletOutputStream outputStream = null;

        try{
            outputStream=response.getOutputStream();
            ImageIO.write(bufferedImage,"jpeg",outputStream);
            outputStream.flush();
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            outputStream.close();
        }
        //-----生成验证码
    }
}
