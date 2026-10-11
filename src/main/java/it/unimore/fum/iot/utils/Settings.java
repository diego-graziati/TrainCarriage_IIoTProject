package it.unimore.fum.iot.utils;

public final class Settings {
    public final static class Simulation {
        public final static class Logs {
            public final static String PASSENGER_LOGGER_NAME = "Passenger-Sim-Logger";
            public final static String TEMPERATURE_LOGGER_NAME = "Temperature-Sim-Logger";
            public final static String CARRIAGE_LOGGER_NAME = "Carriage-Sim-Logger";
        }
    }

    public final static class SmartObjects {
        public final static class Logs {
            public final static String ROOT_NAME = "SmartObjects";
            public final static String EXTERNAL_DOORS_LOGGER_GROUP_NAME = SmartObjects.Logs.ROOT_NAME + "." + "ExternalDoors";
            public final static String INTERNAL_DOORS_LOGGER_GROUP_NAME = SmartObjects.Logs.ROOT_NAME + "." + "InternalDoors";
            public final static String EXTERNAL_DOOR_LOCKS_LOGGER_GROUP_NAME = SmartObjects.Logs.ROOT_NAME + "." + "ExternalDoorLocks";
            public final static String INTERNAL_DOOR_LOCKS_LOGGER_GROUP_NAME = SmartObjects.Logs.ROOT_NAME + "." + "InternalDoorLocks";
            public final static String EXTERNAL_PRESENCE_MONITORING_GROUP_NAME = SmartObjects.Logs.ROOT_NAME + "." + "ExternalPresenceMonitoring";
            public final static String INTERNAL_PRESENCE_MONITORING_GROUP_NAME = SmartObjects.Logs.ROOT_NAME + "." + "InternalPresenceMonitoring";
            public final static String SEAT_CHARGING_STATIONS_GROUP_NAME = SmartObjects.Logs.ROOT_NAME + "." + "SeatChargingStations";
            public final static String SEAT_LIGHT_CONTROLLERS_GROUP_NAME = SmartObjects.Logs.ROOT_NAME + "." + "SeatLightControllers";
            public final static String TRASH_BINS_GROUP_NAME = SmartObjects.Logs.ROOT_NAME + "." + "TrashBins";
            public final static String CARRIAGE_LIGHT_CONTROLLERS_GROUP_NAME = SmartObjects.Logs.ROOT_NAME + "." + "CarriageLightControllers";
            public final static String TOILET_LIGHT_CONTROLLERS_GROUP_NAME = SmartObjects.Logs.ROOT_NAME + "." + "ToiletLightControllers";
            public final static String TEMPERATURE_CONTROLLER_GROUP_NAME = SmartObjects.Logs.ROOT_NAME + "." + "TemperatureController";
        }
    }
}
