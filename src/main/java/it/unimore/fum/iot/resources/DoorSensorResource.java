package it.unimore.fum.iot.resources;

import com.google.gson.Gson;
import it.unimore.fum.iot.models.DoorSensorModel;
import it.unimore.fum.iot.utils.CoreInterfaces;
import it.unimore.fum.iot.utils.SenMLPack;
import it.unimore.fum.iot.utils.SenMLRecord;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;

import java.util.Optional;

public class DoorSensorResource extends CoapResource {

    private static final String OBJECT_TITLE = "DoorSensor";
    private static final Number SENSOR_VERSION = 0.1;

    private DoorSensorModel model = null;
    private String devideId = null;
    private Gson gson = null;

    public DoorSensorResource(String name, String devideId) {
        super(name);
        this.devideId = devideId;
        this.init();
    }

    private void init() {
        this.gson = new Gson();
        this.model = new DoorSensorModel();

        setObservable(true);
        setObserveType(CoAP.Type.CON);

        getAttributes().setTitle(OBJECT_TITLE);
        getAttributes().addAttribute("rt", "it.unimore.device.sensor.door");
        getAttributes().addAttribute("if", CoreInterfaces.CORE_S.getValue());
        getAttributes().addAttribute("ct", Integer.toString(MediaTypeRegistry.APPLICATION_SENML_JSON));
        getAttributes().addAttribute("ct", Integer.toString(MediaTypeRegistry.TEXT_PLAIN));
    }

    private Optional<String> getJsonSenmlResponse() {
        try{
            SenMLPack senMLPack =new SenMLPack();

            SenMLRecord senMLRecord = new SenMLRecord();
            senMLRecord.setBn(this.devideId);
            senMLRecord.setN(this.getName());
            senMLRecord.setBver(SENSOR_VERSION);
            senMLRecord.setVb(this.model.isOpen());
            senMLRecord.setT(this.model.getTimestamp());

            senMLPack.add(senMLRecord);

            return Optional.of(this.gson.toJson(senMLPack));
        }catch(Exception e){
            return Optional.empty();
        }
    }

    //TODO: IMPLEMENT!
    @Override
    public void handleGET(CoapExchange exchange) {
        super.handleGET(exchange);
    }
}
