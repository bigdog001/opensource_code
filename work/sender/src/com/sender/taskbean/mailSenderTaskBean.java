package com.sender.taskbean;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 10/18/12
 * Time: 3:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class mailSenderTaskBean {
    private String mailFrom;
    private String mailTo[];
    private String smtpHost;
    private String smtpPort;
    private String smtpUsername;
    private String smtppassword;

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String[] getMailTo() {
        return mailTo;
    }

    public void setMailTo(String[] mailTo) {
        this.mailTo = mailTo;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort;
    }

    public String getSmtpUsername() {
        return smtpUsername;
    }

    public void setSmtpUsername(String smtpUsername) {
        this.smtpUsername = smtpUsername;
    }

    public String getSmtppassword() {
        return smtppassword;
    }

    public void setSmtppassword(String smtppassword) {
        this.smtppassword = smtppassword;
    }


}
