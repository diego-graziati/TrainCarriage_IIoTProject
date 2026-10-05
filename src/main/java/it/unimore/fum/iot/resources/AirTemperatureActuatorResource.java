package it.unimore.fum.iot.resources;

import com.google.gson.Gson;
import it.unimore.fum.iot.models.AirTemperatureActuatorModel;
import it.unimore.fum.iot.utils.CoreInterfaces;
import it.unimore.fum.iot.utils.SenMLPack;
import it.unimore.fum.iot.utils.SenMLRecord;
import it.unimore.fum.iot.utils.types.drivers.ActuatorDriver;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;

import java.util.Optional;

public class AirTemperatureActuatorResource extends CoapResource {

    private static final String OBJECT_TITLE = "AirTemperatureActuator";
    private static final Number ACTUATOR_VERSION = 0.1;

    private AirTemperatureActuatorModel model = null;
    private String devideId = null;
    private Gson gson = null;

    public AirTemperatureActuatorResource(String name, String deviceId, ActuatorDriver<Double> setTargetTemperatureActuator) {
        super(name);
        this.devideId = deviceId;

        this.gson = new Gson();
        this.model = new AirTemperatureActuatorModel(setTargetTemperatureActuator);

        getAttributes().addAttribute(OBJECT_TITLE);
        getAttributes().addAttribute("rt", "it.unimore.device.actuator.air_temperature");
        getAttributes().addAttribute("if", CoreInterfaces.CORE_A.getValue());
        getAttributes().addAttribute("ct", Integer.toString(MediaTypeRegistry.APPLICATION_SENML_JSON));
        getAttributes().addAttribute("ct", Integer.toString(MediaTypeRegistry.TEXT_PLAIN));
    }

    private Optional<String> getJsonSenmlResponse() {
        try{
            SenMLPack senMLPack =new SenMLPack();

            SenMLRecord senMLRecord = new SenMLRecord();
            senMLRecord.setBn(this.devideId);
            senMLRecord.setBver(ACTUATOR_VERSION);
            senMLRecord.setN(this.getName());
            senMLRecord.setT(this.model.getTimestamp());
            senMLRecord.setU(this.model.getTargetTemperatureUnit());
            senMLRecord.setV(this.model.getTargetTemperature());

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
            exchange.respond(CoAP.ResponseCode.CONTENT, this.model.getTargetTemperatureUnit() + "\n"
                    + this.model.getTargetTemperature() + "\n"
                    + this.model.getTimestamp(), exchange.getRequestOptions().getAccept());
        } else {
            exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
        }
    }

    @Override
    public void handlePUT(CoapExchange exchange) {

        if (exchange.getRequestPayload() != null) {
            double targetTemperature = Double.parseDouble(new String(exchange.getRequestPayload()));

            //TODO: implement guards!
            this.model.setTargetTemperature(targetTemperature);

            if (exchange.getRequestOptions().getAccept() == MediaTypeRegistry.APPLICATION_SENML_JSON ||
                    exchange.getRequestOptions().getAccept() == MediaTypeRegistry.APPLICATION_JSON) {

                Optional<String> jsonSenmlResponse = getJsonSenmlResponse();

                if (jsonSenmlResponse.isPresent()) {
                    exchange.respond(CoAP.ResponseCode.CONTENT, jsonSenmlResponse.get(), exchange.getRequestOptions().getAccept());
                } else {
                    exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
                }
            } else if (exchange.getRequestOptions().getAccept() == MediaTypeRegistry.TEXT_PLAIN) {
                exchange.respond(CoAP.ResponseCode.CONTENT, this.model.getTargetTemperatureUnit() + "\n"
                        + this.model.getTargetTemperature() + "\n"
                        + this.model.getTimestamp(), exchange.getRequestOptions().getAccept());
            } else {
                exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
            }
        } else {
            exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
        }
    }
}
