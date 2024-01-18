package team.challenge.MobileStore.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import team.challenge.MobileStore.exception.GlobalExceptionHandler;
import team.challenge.MobileStore.service.MailSenderService;
@Service
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {
    private final JavaMailSender javaMailSender;
    private static final Logger logger = LoggerFactory.getLogger(MailSenderServiceImpl.class);
    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        logger.info(String.format("Message was sand to %s", to));
        javaMailSender.send(message);

    }
}
