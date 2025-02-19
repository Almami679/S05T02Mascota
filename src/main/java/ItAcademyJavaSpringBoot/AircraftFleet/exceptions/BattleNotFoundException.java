package ItAcademyJavaSpringBoot.AircraftFleet.exceptions;

public class BattleNotFoundException extends RuntimeException {
    public BattleNotFoundException(String message) {
        super(message);
    }
}
