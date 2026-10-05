package it.unimore.fum.iot.resources;

import com.google.gson.Gson;
import it.unimore.fum.iot.models.LampBrightnessActuatorModel;
import it.unimore.fum.iot.utils.CoreInterfaces;
import it.unimore.fum.iot.utils.SenMLPack;
import it.unimore.fum.iot.utils.SenMLRecord;
import it.unimore.fum.iot.utils.types.BrightnessLevelsEnum;
import it.unimore.fum.iot.utils.types.drivers.ActuatorDriver;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;

import java.util.Optional;

public class LampBrightessActuatorResource extends CoapResource {

    private static final String OBJECT_TITLE = "LampBrightessActuator";
    private static final Number ACTUATOR_VERSION = 0.1;

    private LampBrightnessActuatorModel model = null;
    private String devideId = null;
    private Gson gson = null;

    public LampBrightessActuatorResource(String name, String deviceId, ActuatorDriver<BrightnessLevelsEnum> brightnessActuator) {
        super(name);
        this.devideId = deviceId;

        // INIT!
        this.gson = new Gson();
        this.model = new LampBrightnessActuatorModel(brightnessActuator);

        getAttributes().addAttribute(OBJECT_TITLE);
        getAttributes().addAttribute("rt", "it.unimore.device.actuator.lamp_brightness");
        getAttributes().addAttribute("if", CoreInterfaces.CORE_A.getValue());
        getAttributes().addAttribute("ct", Integer.toString(MediaTypeRegistry.APPLICATION_SENML_JSON));
        getAttributes().addAttribute("ct", Integer.toString(MediaTypeRegistry.TEXT_PLAIN));
    }

    private Optional<String> getJsonSenmlResponse() {
        try{
            SenMLPack senMLPack = new SenMLPack();

            SenMLRecord senMLRecord = new SenMLRecord();
            senMLRecord.setBn(this.devideId);
            senMLRecord.setN(this.getName());
            senMLRecord.setBver(ACTUATOR_VERSION);
            senMLRecord.setT(this.model.getTimestamp());
            senMLRecord.setV(this.model.getBrightnessLevel().ordinal());

            SenMLRecord brightnessSenMLRecord = new SenMLRecord();
            brightnessSenMLRecord.setU(this.model.getLampBrightnessUnit());
            if (this.model.getBrightnessLevel() == BrightnessLevelsEnum.LOW) {
                brightnessSenMLRecord.setV(this.model.getLowBrightness());
            } else if (this.model.getBrightnessLevel() == BrightnessLevelsEnum.MEDIUM) {
                brightnessSenMLRecord.setV(this.model.getMediumBrightness());
            } else {
                brightnessSenMLRecord.setV(this.model.getHighBrightness());
            }

            senMLPack.add(senMLRecord);
            senMLPack.add(brightnessSenMLRecord);

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

            double lampBrightness;
            if (this.model.getBrightnessLevel() == BrightnessLevelsEnum.LOW) {
                lampBrightness = this.model.getLowBrightness();
            } else if (this.model.getBrightnessLevel() == BrightnessLevelsEnum.MEDIUM) {
                lampBrightness = this.model.getMediumBrightness();
            } else {
                lampBrightness = this.model.getHighBrightness();
            }

            exchange.respond(CoAP.ResponseCode.CONTENT, this.model.getBrightnessLevel().ordinal() + "\n"
                    + this.model.getLampBrightnessUnit() + "\n"
                    + lampBrightness + "\n"
                    + this.model.getTimestamp(), exchange.getRequestOptions().getAccept());
        } else {
            exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
        }
    }

    @Override
    public void handlePUT(CoapExchange exchange) {

        if (exchange.getRequestPayload() != null) {

            int brightnessLevel = Integer.parseInt(new String(exchange.getRequestPayload()));

            if (brightnessLevel >= 0 && brightnessLevel <= BrightnessLevelsEnum.values().length) {

                this.model.setBrightnessLevel(BrightnessLevelsEnum.values()[brightnessLevel]);

                if (exchange.getRequestOptions().getAccept() == MediaTypeRegistry.APPLICATION_SENML_JSON ||
                        exchange.getRequestOptions().getAccept() == MediaTypeRegistry.APPLICATION_JSON) {

                    Optional<String> jsonSenmlResponse = getJsonSenmlResponse();

                    if (jsonSenmlResponse.isPresent()) {
                        exchange.respond(CoAP.ResponseCode.CONTENT, jsonSenmlResponse.get(), exchange.getRequestOptions().getAccept());
                    } else {
                        exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
                    }
                } else if (exchange.getRequestOptions().getAccept() == MediaTypeRegistry.TEXT_PLAIN) {
                    double lampBrightness;
                    if (this.model.getBrightnessLevel() == BrightnessLevelsEnum.LOW) {
                        lampBrightness = this.model.getLowBrightness();
                    } else if (this.model.getBrightnessLevel() == BrightnessLevelsEnum.MEDIUM) {
                        lampBrightness = this.model.getMediumBrightness();
                    } else {
                        lampBrightness = this.model.getHighBrightness();
                    }

                    exchange.respond(CoAP.ResponseCode.CONTENT, this.model.getBrightnessLevel().ordinal() + "\n"
                            + this.model.getLampBrightnessUnit() + "\n"
                            + lampBrightness + "\n"
                            + this.model.getTimestamp(), exchange.getRequestOptions().getAccept());
                } else {
                    exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
                }
            } else {
                exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
            }
        } else {
            exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
        }
    }
}
