package ItAcademyJavaSpringBoot.AircraftFleet.exceptions;

public class AccessoryNotFoundException extends RuntimeException {
    public AccessoryNotFoundException(String message) {
        super(message);
    }
}
