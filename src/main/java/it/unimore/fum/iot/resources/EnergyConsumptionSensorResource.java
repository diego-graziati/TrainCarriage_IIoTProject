package it.unimore.fum.iot.resources;

import com.google.gson.Gson;
import it.unimore.fum.iot.models.BatteryChargeSensorModel;
import it.unimore.fum.iot.models.EnergyConsumptionSensorModel;
import it.unimore.fum.iot.utils.CoreInterfaces;
import it.unimore.fum.iot.utils.SenMLPack;
import it.unimore.fum.iot.utils.SenMLRecord;
import it.unimore.fum.iot.utils.types.drivers.SensorDriver;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;

import java.util.Optional;

public class EnergyConsumptionSensorResource extends CoapResource {

    private static final String OBJECT_TITLE = "EnergyConsumptionSensor";
    private static final Number SENSOR_VERSION = 0.1;

    private EnergyConsumptionSensorModel model = null;
    private String devideId = null;
    private Gson gson = null;

    public EnergyConsumptionSensorResource(String name, String devideId, SensorDriver<Double> energyConsumptionSensor) {
        super(name);
        this.devideId = devideId;

        // INIT!
        this.gson = new Gson();
        this.model = new EnergyConsumptionSensorModel(energyConsumptionSensor);

        setObservable(true);
        setObserveType(CoAP.Type.CON);

        getAttributes().addAttribute(OBJECT_TITLE);
        getAttributes().addAttribute("rt", "it.unimore.device.sensor.energy_consumption");
        getAttributes().addAttribute("if", CoreInterfaces.CORE_S.getValue());
        getAttributes().addAttribute("ct", Integer.toString(MediaTypeRegistry.APPLICATION_SENML_JSON));
        getAttributes().addAttribute("ct", Integer.toString(MediaTypeRegistry.TEXT_PLAIN));

        this.model.setOnStateChange(this::changed);
    }

    private Optional<String> getJsonSenmlResponse() {
        try{
            SenMLPack senMLPack =new SenMLPack();

            SenMLRecord senMLRecord = new SenMLRecord();
            senMLRecord.setBn(this.devideId);
            senMLRecord.setN(this.getName());
            senMLRecord.setBver(SENSOR_VERSION);
            senMLRecord.setU(this.model.getEnergyConsumptionUnit());
            senMLRecord.setV(this.model.getEnergyConsumption());
            senMLRecord.setT(this.model.getTimestamp());

            senMLPack.add(senMLRecord);

            return Optional.of(this.gson.toJson(senMLPack));
        }catch(Exception e){
            return Optional.empty();
        }
    }

    @Override
    public void handleGET(CoapExchange exchange) {

        if (exchange.getRequestOptions().getAccept() == MediaTypeRegistry.APPLICATION_SENML_JSON ||
                exchange.getRequestOptions().getAccept() == MediaTypeRegistry.APPLICATION_JSON) {

            Optional<String> jsonSenmlResponse = getJsonSenmlResponse();

            if (jsonSenmlResponse.isPresent()) {
                exchange.respond(CoAP.ResponseCode.CONTENT, jsonSenmlResponse.get(), exchange.getRequestOptions().getAccept());
            } else {
                exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
            }
        } else if (exchange.getRequestOptions().getAccept() == MediaTypeRegistry.TEXT_PLAIN) {
            exchange.respond(CoAP.ResponseCode.CONTENT, this.model.getEnergyConsumptionUnit() + "\n"
                    + this.model.getEnergyConsumption() + "\n"
                    + this.model.getTimestamp() + "\n", exchange.getRequestOptions().getAccept());
        } else {
            exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
        }
    }
}
