package net.dingyabin.com.service;

import net.dingyabin.com.bean.EmailBean;

/**
 * Created by MrDing
 * Date: 2017/2/25.
 * Time:5:11
 */
public interface MailService {

    /**
     * 发送email
     *
     * @param emailBean mailbean信息
     * @return 发送结果(true success, false fail)
     */
    boolean sendMail(EmailBean emailBean);


}
