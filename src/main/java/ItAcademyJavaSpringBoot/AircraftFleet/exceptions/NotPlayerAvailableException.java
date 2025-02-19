package ItAcademyJavaSpringBoot.AircraftFleet.exceptions;

public class NotPlayerAvailableException extends RuntimeException {
    public NotPlayerAvailableException(String message) {
        super(message);
    }
}
