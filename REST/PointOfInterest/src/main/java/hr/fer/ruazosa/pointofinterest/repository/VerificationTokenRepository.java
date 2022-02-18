package hr.fer.ruazosa.pointofinterest.repository;

import hr.fer.ruazosa.pointofinterest.entity.User;
import hr.fer.ruazosa.pointofinterest.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository
        extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}
