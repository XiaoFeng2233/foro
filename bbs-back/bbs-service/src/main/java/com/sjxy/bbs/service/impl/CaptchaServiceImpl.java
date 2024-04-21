package com.sjxy.bbs.service.impl;

import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.sjxy.bbs.entity.constants.RedisConstants;
import com.sjxy.bbs.service.CaptchaService;
import com.sjxy.bbs.util.RedisUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public String generateCaptcha() {
        String id = IdUtil.simpleUUID();
        redisUtil.setEx(String.format(RedisConstants.CAPTCHA_NAME, id), RandomUtil.randomString(4), 300, TimeUnit.SECONDS);
        return id;
    }

    @Override
    public String getCaptcha(String id) {
        String captcha = redisUtil.get("captcha:generate:" + id);
        return captcha;
    }

    @Override
    public void showCaptcha(HttpServletResponse response, String id) throws IOException {
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        String captcha = getCaptcha(id);
        if (captcha != null) {
            LineCaptcha lineCaptcha = new LineCaptcha(200, 100);
            Image image = lineCaptcha.createImage(captcha);
            ImageIO.write((RenderedImage) image, "jpg", response.getOutputStream());
        }
    }
}
