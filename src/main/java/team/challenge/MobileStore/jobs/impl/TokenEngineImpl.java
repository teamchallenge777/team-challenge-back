package team.challenge.MobileStore.jobs.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import team.challenge.MobileStore.jobs.TokenEngine;
import team.challenge.MobileStore.model.VerificationToken;
import team.challenge.MobileStore.repositories.VerificationTokenRepository;

import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenEngineImpl implements TokenEngine {
    static final Logger logger = LoggerFactory.getLogger(TokenEngine.class);
    private final VerificationTokenRepository tokenRepository;
    @Scheduled(fixedDelayString = "${verification.time}")
    @Override
    public void deleteTokens() {
        List<VerificationToken> tokenToDelete = tokenRepository.findAll().stream()
                .filter(this::isTokenUsed).toList();
        if (tokenToDelete.size() > 0){
            tokenRepository.deleteAll(tokenToDelete);
            logger.info(String.format("%d tokens was deleting", tokenToDelete.size()));
        }
    }

    private boolean isTokenUsed(VerificationToken token){
        Calendar now = Calendar.getInstance();
        return token.getExpiryDate().getTime() - now.getTime().getTime() < 0;
    }
}
