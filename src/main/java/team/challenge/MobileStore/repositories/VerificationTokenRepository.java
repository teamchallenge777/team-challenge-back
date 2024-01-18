package team.challenge.MobileStore.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import team.challenge.MobileStore.model.UserModel;
import team.challenge.MobileStore.model.VerificationToken;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface VerificationTokenRepository extends MongoRepository<VerificationToken, String> {
    Optional<VerificationToken> findByToken(String token);
    Optional<VerificationToken> findByUser(UserModel user);
    List<VerificationToken> findAllByExpiryDateBefore(Date date);

}
