package com.sjxy.bbs.service.impl;

import cn.hutool.core.lang.Dict;
import cn.hutool.extra.mail.Mail;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import com.sjxy.bbs.entity.bo.EmailConfigBO;
import com.sjxy.bbs.entity.bo.SiteConfigBO;
import com.sjxy.bbs.entity.constants.EmailConstants;
import com.sjxy.bbs.entity.constants.UserConstants;
import com.sjxy.bbs.service.ConfigService;
import com.sjxy.bbs.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private ConfigService configService;

    private void sendMail(String title, String renderContent, String destEmailAddress) {

        EmailConfigBO emailConfig = configService.getEmailConfig();

        MailAccount account = new MailAccount();
        account.setSslEnable(emailConfig.getUseSSL());
        account.setAuth(true);
        account.setHost(emailConfig.getHost());
        account.setPort(Integer.parseInt(emailConfig.getPort()));
        account.setFrom(emailConfig.getAddress());
        account.setUser(emailConfig.getUsername());
        account.setPass(emailConfig.getPassword());

        Mail mail = Mail
                .create(account)
                .setTitle(title)
                .setContent(renderContent)
                .setTos(destEmailAddress)
                .setHtml(true);
        mail.send();
    }

    @Override
    public void sendRegisterMail(String destEmailAddress, String username, String token, String code) {

        String title = EmailConstants.REGISTER_MAIL_TITLE;

        EmailConfigBO emailConfig = configService.getEmailConfig();
        SiteConfigBO siteConfig = configService.getSiteConfig();


        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig());
        Template template = engine.getTemplate(emailConfig.getRegisterTemplate());
        String render = template.render(Dict.create()
                .set("userName", username)
                .set("siteName", siteConfig.getName())
                .set("activeTime", UserConstants.USER_EMAIL_ACTIVE_TIME)
                .set("activeUrl", String.format(EmailConstants.REGISTER_ACTIVE_URL, siteConfig.getUrl(), token, code))
                .set("logoUrl", siteConfig.getLogo()));

        sendMail(title, render, destEmailAddress);
    }

    @Override
    public void sendForgetPasswordMail(String destEmailAddress, String username, String token, String code) {
        String title = EmailConstants.FORGET_PASSWORD_MAIL_TITLE;

        EmailConfigBO emailConfig = configService.getEmailConfig();
        SiteConfigBO siteConfig = configService.getSiteConfig();


        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig());
        Template template = engine.getTemplate(emailConfig.getForgetTemplate());
        String render = template.render(Dict.create()
                .set("userName", username)
                .set("resetUrl", String.format(EmailConstants.FORGET_PASSWORD_ACTIVE_URL, siteConfig.getUrl(), token, code))
                .set("logoUrl", siteConfig.getLogo()));
        sendMail(title, render, destEmailAddress);

    }
}
