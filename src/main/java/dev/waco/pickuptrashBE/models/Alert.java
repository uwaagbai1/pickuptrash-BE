package dev.waco.pickuptrashBE.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "alerts")
@Data
@NoArgsConstructor
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String imageFields;

    @Column(nullable = false)
    private String mlImageClassification;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @Column(nullable = false)
    private String location;

    // constructor

    public Alert(User user, String imageFields, String mlImageClassification, String location) {
        this.user = user;
        this.imageFields = imageFields;
        this.mlImageClassification = mlImageClassification;
        this.location = location;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
}

