package ItAcademyJavaSpringBoot.AircraftFleet.exceptions;

public class PlaneHasFullFuel extends RuntimeException {
    public PlaneHasFullFuel(String message) {
        super(message);
    }
}
