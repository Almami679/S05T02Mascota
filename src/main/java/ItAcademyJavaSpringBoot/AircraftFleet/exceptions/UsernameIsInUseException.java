package ItAcademyJavaSpringBoot.AircraftFleet.exceptions;

public class UsernameIsInUseException extends RuntimeException {
    public UsernameIsInUseException(String message) {
        super(message);
    }
}
