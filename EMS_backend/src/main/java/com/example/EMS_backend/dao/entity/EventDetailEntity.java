package com.example.EMS_backend.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
@Table(name = "EVENT_DETAILS")
public class EventDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVENT_ID")
    private int eventId;

    @Column(name = "EVENT_TITLE")
    private String eventTitle;

    @Column(name = "EVENT_DESCRIPTION")
    private String eventDescription;

    @Column(name = "EVENT_DATE")
    private LocalDate eventDate;

    @Column(name = "START_TIME")
    private LocalTime startTime;

    @Column(name = "END_TIME")
    private LocalTime endTime;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "CAPACITY")
    private int capacity;

    @Column(name = "AVAILABLE_SEATS")
    private int availableSeats;

    @Column(name = "ARTISTS")
    private String artists;

    @Column(name = "AGE_LIMIT")
    private int ageLimit;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "EVENT_IMAGE")
    private String eventImage;

    @ManyToOne
    @JoinColumn(name = "ORGANIZER_ID", referencedColumnName = "USER_ID")
    private UserEntity organizer;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID",referencedColumnName = "CATEGORY_ID")
    private CategoryEntity eventCategory;

    @OneToMany(mappedBy = "registeredEventDetails",cascade = CascadeType.ALL)
    private List<RegistrationEntity> eventAllRegistrations;
}
