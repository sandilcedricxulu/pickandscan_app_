package com.example.picknscan;
import android.content.pm.PackageInstaller;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
    Session session = null;
    String firstName = "",verificationCode = "",email="";


    public void sendMail(String toEmail,String verificationCode,String firstName) {
        this.firstName = firstName;
        this.email = toEmail;
        this.verificationCode = verificationCode;
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("picknscan@gmail.com", "PicknScan12345");
            }
        });
        RetreiveFeedTask task = new RetreiveFeedTask();
        task.execute();

    }

    class RetreiveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("picknscan@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(""+email));
                message.setSubject("Welcome to PicknScan");
                String emailBody = "Hello "+firstName+"\n"
                                    +"Welcome to PicknScan, and thanks for creating your own account with us!"+
                                    "\n"+"Please help us secure your PicknScan account by confirming your email"+
                                    "\n"+"address "+email+"."+
                                    "\n"+"This will let you access all the features of our service and receive future "+
                                    "\n"+"notifications from us.\nTo proceed, please enter this code:"+
                                    "\n"+"Verification Code : "+verificationCode+
                                    "\n"+"Thank you!"+
                                    "\n"+"All the best.";
                message.setContent(emailBody, "text/plain; charset=utf-8");
                Transport.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println("mail sent");
        }
    }
}
