package ItAcademyJavaSpringBoot.AircraftFleet.exceptions;

public class HealthPlaneIsFullException extends RuntimeException {
    public HealthPlaneIsFullException(String message) {
        super(message);
    }
}
