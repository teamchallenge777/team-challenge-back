package team.challenge.MobileStore.service;

import team.challenge.MobileStore.dto.PasswordResetRequest;
import team.challenge.MobileStore.model.UserModel;
import team.challenge.MobileStore.model.VerificationToken;

public interface PasswordVerificationTokenService {
    VerificationToken createToken(UserModel user);
    String verifyToken(String token, String userId, String newPassword);
    VerificationToken getByTokenValue(String token);
    VerificationToken getByUser(UserModel user);
}
