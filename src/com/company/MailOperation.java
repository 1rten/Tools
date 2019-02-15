package Common.Mail;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

/**
 * 邮件发送操作类
 *
 */
public class MailOperation {

    /**
     * 发送邮件
     *
     * @param user     发件人邮箱
     * @param password 授权码或密码（使用授权码时密码无效）
     * @param host
     * @param from     发件人
     * @param to       接收者邮箱支持多个
     * @param cc       抄送人邮箱
     * @param subject  邮件主题
     * @param content  邮件内容
     * @param filePath 附件
     * @return success 发送成功 failure 发送失败
     * @throws Exception
     */
    public String sendMail(final String user, final String password, String protocol, String host, String port, String timeout,
                           String from, String to, String cc, String subject, String content, String filePath, String href)
            throws Exception {
        if (to != null) {
            // 创建一个程序与邮件服务器会话对象 Session
            Properties props = new Properties();
            props.setProperty("mail.transport.protocol", protocol);
            props.setProperty("mail.smtp.host", host);
            props.setProperty("mail.smtp.port", port);
            // 指定验证为true
            props.setProperty("mail.smtp.auth", "true");
            // SSL
            MailSSLSocketFactory mailSSLSocketFactory = new MailSSLSocketFactory();
            mailSSLSocketFactory.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", mailSSLSocketFactory);

            props.setProperty("mail.timeout", timeout);
            MailAuthenticator auth = new MailAuthenticator();
            MailAuthenticator.USERNAME = user;
            MailAuthenticator.PASSWORD = password;
            Session session = Session.getDefaultInstance(props, auth);

            session.setDebug(true);
            try {
                MimeMessage message = new MimeMessage(session);
                // 发件人
                message.setFrom(new InternetAddress(from));
                // 收件人
                if (null != to && !to.isEmpty())
                    message.addRecipients(Message.RecipientType.TO,
                            new InternetAddress().parse(to));

                // 抄送人
                if (null != cc && !cc.isEmpty())
                    message.addRecipients(Message.RecipientType.CC,
                            new InternetAddress().parse(cc));

                message.setSubject(subject);

//                MimeBodyPart image = new MimeBodyPart();// 图片
//                DataHandler dataHandler1 = new DataHandler(new FileDataSource(filePath));
//                image.setDataHandler(dataHandler1);
//                image.setContentID("mailPic");

                MimeBodyPart text = new MimeBodyPart(); // 正文
                // 这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片
//                text.setContent(content + "<a><img src='cid:mailPic'/></a>", "text/html;charset=utf-8");
                text.setContent(content + "<br/>" + href + "<a href='" + href + "'>", "text/html;charset=utf-8");
//                text.setContent(content + "<br/>" + "http://www.baidu.com<br/><a href='http://www.baidu.com'>", "text/html;charset=utf-8");


                //（文本+图片）设置 文本 和 图片"节点"的关系（将 文本 和 图片"节点"合成一个混合"节点"）
//                MimeMultipart mm_text_image = new MimeMultipart();
//                mm_text_image.addBodyPart(text);
//                mm_text_image.addBodyPart(image);
//                mm_text_image.setSubType("related");    // 关联关系

                // 将 文本+图片 的混合"节点"封装成一个普通"节点"
//                MimeBodyPart text_image = new MimeBodyPart();
//                text_image.setContent(mm_text_image);

                // 创建附件"节点"
                MimeBodyPart attachment = new MimeBodyPart();
                DataHandler dataHandler2 = new DataHandler(new FileDataSource(filePath));
                attachment.setDataHandler(dataHandler2);
                attachment.setFileName(MimeUtility.encodeText(dataHandler2.getName()));

                // 设置（文本+图片）和 附件 的关系（合成一个大的混合"节点" / Multipart ）
                MimeMultipart mimeMultipart = new MimeMultipart();
                mimeMultipart.addBodyPart(text);
                mimeMultipart.addBodyPart(attachment);// 如果有多个附件，可以创建多个多次添加
                mimeMultipart.setSubType("mixed");//混合关系

                message.setContent(mimeMultipart);
                message.setSentDate(new Date());
                message.saveChanges();
                Transport transport = session.getTransport();
                transport.send(message);
                System.out.println(message.toString());
            } catch (Exception e) {
                e.printStackTrace();
                return "failure";
            }
            return "success";
        } else {
            return "failure";
        }
    }

}