package it.unimore.fum.iot.resources;

import com.google.gson.Gson;
import it.unimore.fum.iot.models.DoorLockActuatorModel;
import it.unimore.fum.iot.utils.CoreInterfaces;
import it.unimore.fum.iot.utils.SenMLPack;
import it.unimore.fum.iot.utils.SenMLRecord;
import it.unimore.fum.iot.utils.types.drivers.ActuatorDriver;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;

import java.util.Optional;

public class DoorLockActuatorResource extends CoapResource {

    private static final String OBJECT_TITLE = "DoorLockActuator";
    private static final Number ACTUATOR_VERSION = 0.1;

    private DoorLockActuatorModel model = null;
    private String devideId = null;
    private Gson gson = null;

    public DoorLockActuatorResource(String name, String deviceId, ActuatorDriver<Boolean> doorLockActuator) {
        super(name);
        this.devideId = deviceId;

        // INIT
        this.gson = new Gson();
        this.model = new DoorLockActuatorModel(doorLockActuator);

        getAttributes().setTitle(OBJECT_TITLE);
        getAttributes().addAttribute("rt", "it.unimore.device.actuator.door-lock");
        getAttributes().addAttribute("if", CoreInterfaces.CORE_A.getValue());
        getAttributes().addAttribute("ct", Integer.toString(MediaTypeRegistry.APPLICATION_SENML_JSON));
        getAttributes().addAttribute("ct", Integer.toString(MediaTypeRegistry.TEXT_PLAIN));
    }

    private Optional<String> getJsonSenmlResponse() {
        try {
            SenMLPack senMLPack = new SenMLPack();

            SenMLRecord senMLRecord = new SenMLRecord();
            senMLRecord.setBn(this.devideId);
            senMLRecord.setN(this.getName());
            senMLRecord.setBver(ACTUATOR_VERSION);
            senMLRecord.setVb(this.model.isLocked());
            senMLRecord.setT(System.currentTimeMillis());

            senMLPack.add(senMLRecord);

            return Optional.of(this.gson.toJson(senMLPack));
        }catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void handleGET(CoapExchange exchange) {

        if (exchange.getRequestOptions().getAccept() == MediaTypeRegistry.APPLICATION_SENML_JSON ||
                exchange.getRequestOptions().getAccept() == MediaTypeRegistry.APPLICATION_JSON) {

            Optional<String> senMlPayload = getJsonSenmlResponse();

            if (senMlPayload.isPresent()) {
                exchange.respond(CoAP.ResponseCode.CONTENT, senMlPayload.get(), exchange.getRequestOptions().getAccept());
            } else {
                exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
            }

        } else if( exchange.getRequestOptions().getAccept() == MediaTypeRegistry.TEXT_PLAIN) {
            exchange.respond(CoAP.ResponseCode.CONTENT, String.valueOf(this.model.isLocked()), MediaTypeRegistry.TEXT_PLAIN);
        } else {
            exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
        }
    }

    @Override
    public void handlePUT(CoapExchange exchange) {

        if (exchange.getRequestPayload() != null) {
            boolean doorLockStatus = Boolean.parseBoolean(new String(exchange.getRequestPayload()));

            this.model.setLocked(doorLockStatus);

            if (exchange.getRequestOptions().getAccept() == MediaTypeRegistry.APPLICATION_SENML_JSON ||
                    exchange.getRequestOptions().getAccept() == MediaTypeRegistry.APPLICATION_JSON) {
                Optional<String> senMlPayload = getJsonSenmlResponse();

                if (senMlPayload.isPresent()) {
                    exchange.respond(CoAP.ResponseCode.CHANGED, senMlPayload.get(), exchange.getRequestOptions().getAccept());
                } else {
                    exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
                }
            } else if (exchange.getRequestOptions().getAccept() == MediaTypeRegistry.TEXT_PLAIN) {
                exchange.respond(CoAP.ResponseCode.CHANGED, String.valueOf(this.model.isLocked()), MediaTypeRegistry.TEXT_PLAIN);
            } else {
                exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
            }
        } else {
            exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
        }
    }
}
