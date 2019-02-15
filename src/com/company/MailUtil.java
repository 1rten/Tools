package Common.Mail;

import Bean.MailConfig;

public class MailUtil {
    private static final String HOST = MailConfig.host;
    private static final String Port = MailConfig.port;
    private static final String Protocol = MailConfig.protocol;
    private static final String USERNAME = MailConfig.userName;
    private static final String PASSWORD = MailConfig.passWord;
    private static final String MailForm = MailConfig.mailForm;
    private static final String MailTimeout = MailConfig.mailTimeout;
    private static final String MailTo = MailConfig.mailTo;
    private static final String MailCC = MailConfig.mailCC;
    private static final String MailSubject = MailConfig.mailSubject;
    private static final String MailContent = MailConfig.mailContent;

    /**
     * 发送邮件
     */
    public void sendMail(String filePath, String href) throws Exception {
        MailOperation mailOperation = new MailOperation();
        mailOperation.sendMail(USERNAME, PASSWORD,
                Protocol, HOST, Port, MailTimeout, MailForm, MailTo, MailCC, MailSubject, MailContent, filePath, href);
    }

}
