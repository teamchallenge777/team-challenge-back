package team.challenge.MobileStore.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Document
@Getter
@Setter
public class VerificationToken {
    private static final int EXPIRATION = 10;
    @Id
    private String id;
    private String token;
    @DocumentReference
    private UserModel user;
    private Date expiryDate;
    private VerificationTokenType tokenType;

    public VerificationToken(UserModel user){
        this.user = user;
        this.token = UUID.randomUUID().toString();
        expiryDate = calculateExpiryDate();
    }
    private Date calculateExpiryDate() {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, VerificationToken.EXPIRATION);
        return new Date(cal.getTime().getTime());
    }

}
