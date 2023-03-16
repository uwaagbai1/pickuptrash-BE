package dev.waco.pickuptrashBE.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import dev.waco.pickuptrashBE.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
}
