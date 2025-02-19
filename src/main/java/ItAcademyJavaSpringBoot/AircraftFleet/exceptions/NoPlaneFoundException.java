package ItAcademyJavaSpringBoot.AircraftFleet.exceptions;

public class NoPlaneFoundException extends RuntimeException {
    public NoPlaneFoundException(String message) {
        super(message);
    }
}
