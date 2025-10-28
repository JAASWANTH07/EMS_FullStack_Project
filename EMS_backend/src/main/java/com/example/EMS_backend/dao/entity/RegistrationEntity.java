package com.example.EMS_backend.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
@Table(name = "REGISTRATIONS")
public class RegistrationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REGISTRATION_ID")
    private int registrationId;

    @Column(name = "TOTAL_TICKETS")
    private int totalTickets;

    @Column(name = "REGISTRATION_DATE")
    private LocalDateTime registrationDate;

    @PrePersist
    protected void onCreate() {this.registrationDate = LocalDateTime.now();}

    @Column(name = "STATUS")
    private String status;
    

    @Column(name = "TOTAL_PRICE")
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "USER_ID",referencedColumnName = "USER_ID")
    private UserEntity registeredUser;

    @ManyToOne
    @JoinColumn(name = "EVENT_ID",referencedColumnName = "EVENT_ID")
    private EventDetailEntity registeredEventDetails;
}
