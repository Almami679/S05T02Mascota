package ItAcademyJavaSpringBoot.AircraftFleet.model.sql;

import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.TimeOfDay;
import ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums.Weather;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Random;

@Entity
@Table(name = "hangars")
@Getter @Setter
@NoArgsConstructor
public class Hangar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @Enumerated(EnumType.STRING)
    private TimeOfDay timeOfDay;

    @Enumerated(EnumType.STRING)
    private Weather weather;

   @OneToMany(mappedBy = "hangar", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Plane> planes;

    public Hangar(User owner) {
        this.owner = owner;
        this.timeOfDay = TimeOfDay.getTimeBasedOnRealTime();
        this.weather = getRandomWeather();
    }

    public void updateHangarState() {
        this.timeOfDay = TimeOfDay.getTimeBasedOnRealTime();
        this.weather = getRandomWeather();
    }

    private Weather getRandomWeather() {
        Weather[] weathers = Weather.values();
        return weathers[new Random().nextInt(weathers.length)];
    }
}
