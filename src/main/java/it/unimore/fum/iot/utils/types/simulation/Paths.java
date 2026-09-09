package it.unimore.fum.iot.utils.types.simulation;

public final class Paths {
    public final static String PREFIX = "/";

    public final static class Config {
        public final static String PREFIX = Paths.PREFIX + "config/";

        public final static class Carriage {
            public final static String PREFIX = Paths.Config.PREFIX + "carriage/";
            public final static class Seat {
                public final static String PREFIX = Paths.Config.Carriage.PREFIX + "seat/";
                public final static String LAMP = Paths.Config.Carriage.Seat.PREFIX + "lamp.json";
                public final static String POWER_OUTLET = Paths.Config.Carriage.Seat.PREFIX + "power_outlet.json";
            }

            public final static class Toilet {
                public final static String PREFIX = Paths.Config.Carriage.PREFIX + "toilet/";
                public final static String LIGHTS = Paths.Config.Carriage.Toilet.PREFIX + "lights.json";
            }

            public final static class Door {
                public final static String PREFIX = Paths.Config.Carriage.PREFIX + "door/";
                public final static String LOCK = Paths.Config.Carriage.Door.PREFIX + "lock.json";
                public final static String PRESENCE_MONITOR = Paths.Config.Carriage.Door.PREFIX + "presence_monitor.json";
            }

            public final static String AIR_VENTILATION = Paths.Config.Carriage.PREFIX + "air_ventilation.json";
            public final static String CARRIAGE = Paths.Config.Carriage.PREFIX + "carriage.json";
            public final static String LIGHTS = Paths.Config.Carriage.PREFIX + "lights.json";
            public final static String DOORS = Paths.Config.Carriage.PREFIX + "doors.json";
            public final static String TRASH_BINS = Paths.Config.Carriage.PREFIX + "trash_bins.json";
        }

        public final static class Simulation {
            public final static String PREFIX = Paths.Config.PREFIX + "simulation/";
            public final static String CARRIAGE = Paths.Config.Simulation.PREFIX + "carriage_simulation.json";
            public final static String PASSENGERS = Paths.Config.Simulation.PREFIX + "passengers_simulation.json";
            public final static String TEMPERATURE = Paths.Config.Simulation.PREFIX + "temperature_simulation.json";
        }
    }
}
