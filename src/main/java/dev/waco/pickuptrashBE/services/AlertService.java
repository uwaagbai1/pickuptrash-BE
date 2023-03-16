package dev.waco.pickuptrashBE.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import dev.waco.pickuptrashBE.models.*;
import dev.waco.pickuptrashBE.repositories.*;

@Service
public class AlertService {
    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private UserService userService;

    public Alert createAlert(Alert alert, Long userId) {
        User user = userService.getUserById(userId);
        alert.setUser(user);
        return alertRepository.save(alert);
    }

    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }

    public List<Alert> getAllAlertsByUserId(Long userId) {
        return alertRepository.findByUserId(userId);
    }
}

