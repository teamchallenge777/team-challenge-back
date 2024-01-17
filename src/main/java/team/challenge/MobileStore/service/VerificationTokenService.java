package team.challenge.MobileStore.service;

import team.challenge.MobileStore.model.UserModel;
import team.challenge.MobileStore.model.VerificationToken;

public interface VerificationTokenService {
    VerificationToken createToken(UserModel user);
    boolean verifyToken(String token, String userId);
    VerificationToken getByTokenValue(String token);
    VerificationToken getByUser(UserModel user);
}
