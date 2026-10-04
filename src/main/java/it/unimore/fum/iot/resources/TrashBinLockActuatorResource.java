package it.unimore.fum.iot.resources;

import com.google.gson.Gson;
import it.unimore.fum.iot.models.TrashBinLockActuatorModel;
import it.unimore.fum.iot.utils.CoreInterfaces;
import it.unimore.fum.iot.utils.SenMLPack;
import it.unimore.fum.iot.utils.SenMLRecord;
import it.unimore.fum.iot.utils.types.drivers.ActuatorDriver;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;

import java.util.Optional;

public class TrashBinLockActuatorResource extends CoapResource {

    private static final String OBJECT_TITLE = "TrashBinLockActuator";
    private static final Number ACTUATOR_VERSION = 0.1;

    private TrashBinLockActuatorModel model = null;
    private String devideId = null;
    private Gson gson = null;

    public TrashBinLockActuatorResource(String name, String deviceId, ActuatorDriver<Boolean> trashBinLockActuator) {
        super(name);
        this.devideId = deviceId;

        // INIT!
        this.gson = new Gson();
        this.model = new TrashBinLockActuatorModel(trashBinLockActuator);

        getAttributes().addAttribute(OBJECT_TITLE);
        getAttributes().addAttribute("rt", "it.unimore.device.actuator.trash_bin_lock");
        getAttributes().addAttribute("if", CoreInterfaces.CORE_A.getValue());
        getAttributes().addAttribute("ct", Integer.toString(MediaTypeRegistry.APPLICATION_SENML_JSON));
        getAttributes().addAttribute("ct", Integer.toString(MediaTypeRegistry.TEXT_PLAIN));
    }

    private Optional<String> getJsonSenmlResponse() {
        try{
            SenMLPack senMLPack =new SenMLPack();

            SenMLRecord senMLRecord = new SenMLRecord();
            senMLRecord.setBn(this.devideId);
            senMLRecord.setN(this.getName());
            senMLRecord.setBver(ACTUATOR_VERSION);
            senMLRecord.setT(this.model.getTimestamp());
            senMLRecord.setVb(this.model.isLocked());

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
            exchange.respond(CoAP.ResponseCode.CONTENT, this.model.isLocked() + "\n"
                    + this.model.getTimestamp(), exchange.getRequestOptions().getAccept());
        } else {
            exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
        }
    }

    @Override
    public void handlePUT(CoapExchange exchange) {

        if (exchange.getRequestPayload() != null) {

            boolean trashBinLockStatus = Boolean.parseBoolean(new String(exchange.getRequestPayload()));

            this.model.setLocked(trashBinLockStatus);

            if (exchange.getRequestOptions().getAccept() == MediaTypeRegistry.APPLICATION_SENML_JSON ||
                    exchange.getRequestOptions().getAccept() == MediaTypeRegistry.APPLICATION_JSON) {

                Optional<String> jsonSenmlResponse = getJsonSenmlResponse();

                if (jsonSenmlResponse.isPresent()) {
                    exchange.respond(CoAP.ResponseCode.CONTENT, jsonSenmlResponse.get(), exchange.getRequestOptions().getAccept());
                } else {
                    exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
                }
            } else if (exchange.getRequestOptions().getAccept() == MediaTypeRegistry.TEXT_PLAIN) {
                exchange.respond(CoAP.ResponseCode.CONTENT, this.model.isLocked() + "\n"
                        + this.model.getTimestamp(), exchange.getRequestOptions().getAccept());
            } else {
                exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
            }
        } else {
            exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
        }
    }
}
