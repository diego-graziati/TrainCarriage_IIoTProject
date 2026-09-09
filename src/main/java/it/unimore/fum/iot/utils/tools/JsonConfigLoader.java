package it.unimore.fum.iot.utils.tools;

import com.google.gson.Gson;
import it.unimore.fum.iot.utils.types.simulation.defaults.DoorPresenceMonitorSensorDefaults;

import java.io.*;
import java.lang.reflect.Type;
import java.util.logging.Logger;

public class JsonConfigLoader<T> {

    protected final Gson gson;
    private final String configPath;
    private final Logger logger;
    protected final InputStream configStream;
    private final Type type;

    public JsonConfigLoader(String configFilePath, Type type) {
        this.logger =  Logger.getLogger(JsonConfigLoader.class.getName());
        this.gson = new Gson();
        this.configPath = configFilePath;

        InputStream is = JsonConfigLoader.class.getResourceAsStream(configFilePath);
        if (is == null) {
            this.logger.severe("Config file " + configFilePath + " doesn't exist");
        }
        this.configStream = is;

        this.type = type;
    }

    public T load() {
        try (Reader reader = new InputStreamReader(configStream)) {
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
