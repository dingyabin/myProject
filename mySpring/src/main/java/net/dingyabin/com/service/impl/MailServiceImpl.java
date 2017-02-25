package net.dingyabin.com.service.impl;

import net.dingyabin.com.bean.EmailBean;
import net.dingyabin.com.service.MailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * Created by MrDing
 * Date: 2017/2/25.
 * Time:5:17
 */
@Service("mailService")
public class MailServiceImpl implements MailService {

    @Resource
    private JavaMailSender javaMailSender;


    @Override
    public boolean sendMail(EmailBean emailBean) {
        //参数校验
        if (emailBean == null || !emailBean.isok()) {
            return false;
        }
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, !CollectionUtils.isEmpty(emailBean.getAttachment()) || !CollectionUtils.isEmpty(emailBean.getInline()), "utf-8");
            mimeMessageHelper.setFrom(emailBean.getFromName());
            mimeMessageHelper.setTo(emailBean.getToWhere());
            mimeMessageHelper.setText(emailBean.getContent(), false);//默认不是html
            mimeMessageHelper.setSubject(emailBean.getSubject());
            mimeMessageHelper.setSentDate(emailBean.getCreateTime());
            //附件不为空则循环添加附件
            if (!CollectionUtils.isEmpty(emailBean.getAttachment())) {
                for (File attach : emailBean.getAttachment()) {
                    mimeMessageHelper.addAttachment(attach.getName(), attach);
                }
            }
            //内联图片不为空则循环添加图片
            if (!CollectionUtils.isEmpty(emailBean.getInline())) {
                mimeMessageHelper.setText(emailBean.getContent(), true);//检测到inline之后说明是html
                for (File file : emailBean.getInline()) {
                    mimeMessageHelper.addInline(file.getName(), file);
                }
            }
            //开始发送...
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
