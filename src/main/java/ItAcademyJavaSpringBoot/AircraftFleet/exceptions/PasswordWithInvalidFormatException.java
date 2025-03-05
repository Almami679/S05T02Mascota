package ItAcademyJavaSpringBoot.AircraftFleet.exceptions;

public class PasswordWithInvalidFormatException extends RuntimeException {
    public PasswordWithInvalidFormatException(String message) {
        super(message);
    }
}
