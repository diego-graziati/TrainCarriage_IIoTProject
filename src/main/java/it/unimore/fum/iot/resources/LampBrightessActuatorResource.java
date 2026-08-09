package it.unimore.fum.iot.resources;

import com.google.gson.Gson;
import it.unimore.fum.iot.models.LampBrightnessActuatorModel;
import it.unimore.fum.iot.utils.CoreInterfaces;
import it.unimore.fum.iot.utils.SenMLPack;
import it.unimore.fum.iot.utils.SenMLRecord;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;

import java.util.Optional;

public class LampBrightessActuatorResource extends CoapResource {

    private static final String OBJECT_TITLE = "LampBrightessActuator";
    private static final Number ACTUATOR_VERSION = 0.1;

    private LampBrightnessActuatorModel model = null;
    private String devideId = null;
    private Gson gson = null;

    public LampBrightessActuatorResource(String name, String deviceId) {
        super(name);
        this.devideId = deviceId;
        this.init();
    }

    private void init() {
        this.gson = new Gson();
        this.model = new LampBrightnessActuatorModel();

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
            senMLRecord.setV(this.model.getBrightnessLevel().ordinal());
            senMLRecord.setT(this.model.getTimestamp());

            senMLPack.add(senMLRecord);

            return Optional.of(this.gson.toJson(senMLPack));
        }catch(Exception e){
            return Optional.empty();
        }
    }

    //TODO: IMPLEMENT!!
    @Override
    public void handleGET(CoapExchange exchange) {
        super.handleGET(exchange);
    }

    //TODO: IMPLEMENT!!
    @Override
    public void handlePUT(CoapExchange exchange) {
        super.handlePUT(exchange);
    }
}
