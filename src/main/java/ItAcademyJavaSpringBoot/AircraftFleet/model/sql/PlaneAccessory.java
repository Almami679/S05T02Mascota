package ItAcademyJavaSpringBoot.AircraftFleet.model.sql;

import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.AccessoryType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "plane_accessories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaneAccessory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccessoryType type;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int level;

    @Column(nullable = false)
    private int power;

    @ManyToOne
    @JoinColumn(name = "plane_id", nullable = false)
    private Plane plane;


}

