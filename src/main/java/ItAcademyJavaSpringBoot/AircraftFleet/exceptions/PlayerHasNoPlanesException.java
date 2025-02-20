package ItAcademyJavaSpringBoot.AircraftFleet.exceptions;

public class PlayerHasNoPlanesException extends RuntimeException {
    public PlayerHasNoPlanesException(String message) {
        super(message);
    }
}
