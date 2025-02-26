package ItAcademyJavaSpringBoot.AircraftFleet.exceptions;

public class PlaneHasFullFuelException extends RuntimeException {
    public PlaneHasFullFuelException(String message) {
        super(message);
    }
}
