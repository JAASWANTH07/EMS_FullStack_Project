package com.example.EMS_backend.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
@Table(name = "USERS")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private int userId;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "PHONE")
    private String phone;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_INFO_ROLES_DETAILS",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private List<RoleEntity> allRoles;

    @OneToMany(mappedBy = "createdBy")
    private List<CategoryEntity> createdCategories;

    @OneToMany(mappedBy = "organizer")
    private List<EventDetailEntity> organizedEvents;

    @OneToMany(mappedBy = "registeredUser",cascade = CascadeType.ALL)
    private List<RegistrationEntity> registeredEvents;

}
