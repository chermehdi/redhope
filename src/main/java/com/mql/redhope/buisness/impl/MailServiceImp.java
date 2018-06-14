package com.mql.redhope.buisness.impl;

import com.mql.redhope.buisness.MailService;
import com.mql.redhope.buisness.qualifiers.UserProperties;
import com.mql.redhope.dao.TokenDao;
import com.mql.redhope.dao.UserDao;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailServiceImp implements MailService {

  @Inject
  private UserDao userRepository;

  @Inject
  private TokenDao tokenRepository;

  @Inject
  @UserProperties
  private Properties properties;

  private Session session;

  @Resource
  private ManagedExecutorService executorService;

  Logger log = LoggerFactory.getLogger(getClass());

  @PostConstruct
  private void init() {
    session = Session.getInstance(properties, new javax.mail.Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(properties.getProperty("mail.username"),
            properties.getProperty("mail.password"));
      }
    });
  }

  private Message createMessage(String email, String subject, String messageBody, Session session)
      throws MessagingException, UnsupportedEncodingException {
    Message message = new MimeMessage(session);
    message.setFrom(new InternetAddress(properties.getProperty("mail.username"), "RedHope Team"));
    message.addRecipient(Message.RecipientType.TO, new InternetAddress(email, email));
    message.setSubject(subject);
    message.setContent(messageBody, "text/html");
    return message;
  }

  @Override
  public void sendMail(String email, String body) {
    this.executorService.submit(() -> {
      try {
        log.info("ending email validation to user " + email);
        Transport.send(createMessage(email, "Account Validation", body, session));
      } catch (UnsupportedEncodingException | MessagingException e) {
        e.printStackTrace();
      }
    });
  }
}
