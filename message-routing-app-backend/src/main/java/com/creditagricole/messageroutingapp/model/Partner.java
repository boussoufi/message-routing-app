package com.creditagricole.messageroutingapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "partners")
@Data @AllArgsConstructor @NoArgsConstructor
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String alias;

    @Column(nullable = false)
    private String type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Direction direction;

    @Column
    private String application;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProcessedFlowType processedFlowType;

    @Column(nullable = false)
    private String description;

}
