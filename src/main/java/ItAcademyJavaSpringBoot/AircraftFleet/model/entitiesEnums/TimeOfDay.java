package ItAcademyJavaSpringBoot.AircraftFleet.model.entitiesEnums;

public enum TimeOfDay {
    DAY,
    NIGHT;

    public static TimeOfDay getTimeBasedOnRealTime() {
        int realHour = java.time.LocalTime.now().getHour();
        return (realHour % 24 >= 6 && realHour < 18) ? DAY : NIGHT;
    }
}

