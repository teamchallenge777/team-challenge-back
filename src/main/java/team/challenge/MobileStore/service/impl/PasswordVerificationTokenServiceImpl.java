package team.challenge.MobileStore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team.challenge.MobileStore.dto.PasswordResetRequest;
import team.challenge.MobileStore.exception.EmailVerifiedException;
import team.challenge.MobileStore.exception.ModelNotFoundException;
import team.challenge.MobileStore.model.UserModel;
import team.challenge.MobileStore.model.VerificationToken;
import team.challenge.MobileStore.model.VerificationTokenType;
import team.challenge.MobileStore.repositories.UserRepository;
import team.challenge.MobileStore.repositories.VerificationTokenRepository;
import team.challenge.MobileStore.service.PasswordVerificationTokenService;

import java.util.Calendar;

@Service
@RequiredArgsConstructor
public class PasswordVerificationTokenServiceImpl implements PasswordVerificationTokenService {
    private final VerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public VerificationToken createToken(UserModel user) {
        try{
            VerificationToken byUser = getByUser(user);
            tokenRepository.delete(byUser);
            VerificationToken token = new VerificationToken(user);
            token.setTokenType(VerificationTokenType.PASSWORD);
            return tokenRepository.save(token);
        } catch (ModelNotFoundException e){
            VerificationToken token = new VerificationToken(user);
            token.setTokenType(VerificationTokenType.PASSWORD);
            return tokenRepository.save(token);
        }
    }

    @Override
    public String verifyToken(String token, String userId, String newPassword) {
        VerificationToken verificationToken = getByTokenValue(token);
        UserModel user = verificationToken.getUser();
        Calendar now = Calendar.getInstance();
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            return "New password cannot be same with old!";
        }
        if (verificationToken.getExpiryDate().getTime() - now.getTime().getTime() >= 0 && user.getId().equals(userId) && verificationToken.getTokenType().equals(VerificationTokenType.PASSWORD)) {
            String hash = passwordEncoder.encode(newPassword);
            user.setPassword(hash);
            userRepository.save(user);
            tokenRepository.delete(verificationToken);
            return "Password successfully changed!";
        } else {
            return "Something was wrong! Please try again!";
        }
    }

    @Override
    public VerificationToken getByTokenValue(String token) {
        return tokenRepository.findByToken(token).orElseThrow(() -> new ModelNotFoundException(String.format("Token: %s not found!", token)));
    }

    @Override
    public VerificationToken getByUser(UserModel user) {
        return tokenRepository.findByUser(user).orElseThrow(() -> new ModelNotFoundException(String.format("Token for user: %s not found!", user.getEmail())));
    }
}
