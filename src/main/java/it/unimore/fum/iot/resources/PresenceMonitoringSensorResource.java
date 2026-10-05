package it.unimore.fum.iot.resources;

import com.google.gson.Gson;
import it.unimore.fum.iot.models.PresenceMonitoringSensorModel;
import it.unimore.fum.iot.utils.CoreInterfaces;
import it.unimore.fum.iot.utils.SenMLPack;
import it.unimore.fum.iot.utils.SenMLRecord;
import it.unimore.fum.iot.utils.types.drivers.SensorDriver;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.javatuples.Pair;

import java.util.Optional;

public class PresenceMonitoringSensorResource extends CoapResource {

    private static final String OBJECT_TITLE = "PresenceMonitoringSensor";
    private static final Number SENSOR_VERSION = 0.1;

    private PresenceMonitoringSensorModel model = null;
    private String devideId = null;
    private Gson gson = null;

    public PresenceMonitoringSensorResource(String name, String deviceId, SensorDriver<Pair<Integer, Integer>> presenceMonitoringSensor) {
        super(name);
        this.devideId = deviceId;

        // INIT!
        this.gson = new Gson();
        this.model = new PresenceMonitoringSensorModel(presenceMonitoringSensor);

        setObservable(true);
        setObserveType(CoAP.Type.CON);

        getAttributes().addAttribute(OBJECT_TITLE);
        getAttributes().addAttribute("rt", "it.unimore.device.sensor.presence_monitoring");
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
            senMLRecord.setBver(SENSOR_VERSION);
            senMLRecord.setT(this.model.getTimestamp());

            SenMLRecord inSenMLRecord = new SenMLRecord();
            inSenMLRecord.setN("in");
            inSenMLRecord.setV(this.model.getIn());

            SenMLRecord outSenMLRecord = new SenMLRecord();
            outSenMLRecord.setN("out");
            outSenMLRecord.setV(this.model.getOut());

            senMLPack.add(senMLRecord);
            senMLPack.add(inSenMLRecord);
            senMLPack.add(outSenMLRecord);

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
            exchange.respond(CoAP.ResponseCode.CONTENT, this.model.getIn() + "\n"
                    + this.model.getOut() + "\n"
                    + this.model.getTimestamp(), exchange.getRequestOptions().getAccept());
        } else {
            exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
        }
    }
}
