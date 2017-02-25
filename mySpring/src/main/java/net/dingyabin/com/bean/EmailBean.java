package net.dingyabin.com.bean;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by MrDing
 * Date: 2017/2/25.
 * Time:3:35
 */
public class EmailBean {

    private String fromName;

    private Date createTime;

    private String subject;

    private String[] toWhere;

    private String content;

    private List<File> attachment;

    private List<File> inline;


    public EmailBean() {
    }

    public EmailBean(String fromName, Date createTime, String subject, String[] toWhere, String content, List<File> attachment, List<File> inline) {
        this.fromName = fromName;
        this.createTime = createTime;
        this.subject = subject;
        this.toWhere = toWhere;
        this.content = content;
        this.attachment = attachment;
        this.inline = inline;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String[] getToWhere() {
        return toWhere;
    }

    public void setToWhere(String[] toWhere) {
        this.toWhere = toWhere;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<File> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<File> attachment) {
        this.attachment = attachment;
    }

    public List<File> getInline() {
        return inline;
    }

    public void setInline(List<File> inline) {
        this.inline = inline;
    }
}