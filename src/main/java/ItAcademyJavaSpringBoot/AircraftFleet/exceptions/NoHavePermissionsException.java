package ItAcademyJavaSpringBoot.AircraftFleet.exceptions;

public class NoHavePermissionsException extends RuntimeException {
    public NoHavePermissionsException(String message) {
        super(message);
    }
}
