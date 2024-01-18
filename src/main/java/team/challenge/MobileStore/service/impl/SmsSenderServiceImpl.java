package team.challenge.MobileStore.service.impl;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team.challenge.MobileStore.service.SmsSenderService;
@Service
public class SmsSenderServiceImpl implements SmsSenderService {
    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.phoneNumber}")
    private String twilioPhoneNumber;
    @Override
    public void sendSms(String to, String text) {
        Twilio.init(accountSid, authToken);
        if (!to.startsWith("+")){
            to = "+380" + to;
        }
        Message message = Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(twilioPhoneNumber),
                text
        ).create();

    }
}
