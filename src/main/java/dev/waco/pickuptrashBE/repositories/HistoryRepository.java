package dev.waco.pickuptrashBE.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.waco.pickuptrashBE.models.History;
import java.util.List;
import dev.waco.pickuptrashBE.models.User;

public interface HistoryRepository extends JpaRepository<History, Long> {
    
    List<History> findByUserOrderByCreatedAtDesc(User user);


}

