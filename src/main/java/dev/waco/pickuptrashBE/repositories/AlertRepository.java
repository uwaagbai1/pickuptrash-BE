package dev.waco.pickuptrashBE.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import dev.waco.pickuptrashBE.models.Alert;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByUserId(Long userId);
}

