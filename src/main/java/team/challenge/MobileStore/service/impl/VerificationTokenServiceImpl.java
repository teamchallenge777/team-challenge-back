package team.challenge.MobileStore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.challenge.MobileStore.exception.ModelNotFoundException;
import team.challenge.MobileStore.exception.EmailVerifiedException;
import team.challenge.MobileStore.model.UserModel;
import team.challenge.MobileStore.model.VerificationToken;
import team.challenge.MobileStore.repositories.UserRepository;
import team.challenge.MobileStore.repositories.VerificationTokenRepository;
import team.challenge.MobileStore.service.VerificationTokenService;

import java.util.Calendar;

@Service
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {
    private final VerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;
    @Override
    public VerificationToken createToken(UserModel user) {
        if (user.isEmailConfirmed()){
            throw new EmailVerifiedException("This account is already verified!");
        }
        try{
            VerificationToken byUser = getByUser(user);
            tokenRepository.delete(byUser);
            VerificationToken token = new VerificationToken(user);
            return tokenRepository.save(token);
        } catch (ModelNotFoundException e){
            VerificationToken token = new VerificationToken(user);
            return tokenRepository.save(token);
        }

    }

    @Override
    public boolean verifyToken(String token, String userId) {
        VerificationToken verificationToken = getByTokenValue(token);
        UserModel user = verificationToken.getUser();
        Calendar now = Calendar.getInstance();
        if (verificationToken.getExpiryDate().getTime() - now.getTime().getTime() >= 0 && user.getId().equals(userId)) {
            user.setEmailConfirmed(true);
            userRepository.save(user);
            tokenRepository.delete(verificationToken);
        }
        return user.isEmailConfirmed();

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
