package it.unimore.fum.iot.resources;

import com.google.gson.Gson;
import it.unimore.fum.iot.models.DoorLockActuatorModel;
import it.unimore.fum.iot.utils.CoreInterfaces;
import it.unimore.fum.iot.utils.SenMLPack;
import it.unimore.fum.iot.utils.SenMLRecord;
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

    public DoorLockActuatorResource(String name, String deviceId) {
        super(name);
        this.devideId = deviceId;
        this.init();
    }

    private void init() {
        this.gson = new Gson();
        this.model = new DoorLockActuatorModel();

        this.setObservable(true);
        this.setObserveType(CoAP.Type.CON);

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

    //TODO: IMPLEMENT!
    @Override
    public void handleGET(CoapExchange exchange) {
        super.handleGET(exchange);
    }

    //TODO: IMPLEMENT!
    @Override
    public void handlePUT(CoapExchange exchange) {
        super.handlePUT(exchange);
    }
}
