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
@Table(name = "CATEGORIES")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private int categoryId;

    @Column(name = "CATEGORY_NAME")
    private String categoryName;

    @ManyToOne
    @JoinColumn(name = "CREATED_BY", referencedColumnName = "USER_ID")
    private UserEntity createdBy;

    @OneToMany(mappedBy = "eventCategory")
    private List<EventDetailEntity> categoryEvents;
}
