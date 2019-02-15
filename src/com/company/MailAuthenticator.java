package Common.Mail;

        import javax.mail.Authenticator;
        import javax.mail.PasswordAuthentication;

/**
 * 发件人账号密码
 */
public class MailAuthenticator extends Authenticator {

    public static String USERNAME = null;
    public static String PASSWORD = null;

    public MailAuthenticator() {
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(USERNAME, PASSWORD);
    }

}