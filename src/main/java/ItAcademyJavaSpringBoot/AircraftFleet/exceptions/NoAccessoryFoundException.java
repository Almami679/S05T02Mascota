package ItAcademyJavaSpringBoot.AircraftFleet.exceptions;

public class NoAccessoryFoundException extends RuntimeException {
    public NoAccessoryFoundException(String message) {
        super(message);
    }
}
