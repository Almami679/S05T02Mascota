package ItAcademyJavaSpringBoot.AircraftFleet.exceptions;

public class InsufficientCreditsException extends RuntimeException {
    public InsufficientCreditsException(String message) {
        super(message);
    }
}
