package it.unimore.fum.iot.resources;

import com.google.gson.Gson;
import it.unimore.fum.iot.models.TrashBinFillSensorModel;
import it.unimore.fum.iot.utils.CoreInterfaces;
import it.unimore.fum.iot.utils.SenMLPack;
import it.unimore.fum.iot.utils.SenMLRecord;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;

import java.util.Optional;

public class TrashBinFillSensorResource extends CoapResource {

    private static final String OBJECT_TITLE = "TrashBinFillSensor";
    private static final Number SENSOR_VERSION = 0.1;

    private TrashBinFillSensorModel model = null;
    private String devideId = null;
    private Gson gson = null;

    public TrashBinFillSensorResource(String name, String deviceId) {
        super(name);
        this.devideId = deviceId;
        this.init();
    }

    private void init() {
        this.gson = new Gson();
        this.model = new TrashBinFillSensorModel();

        setObservable(true);
        setObserveType(CoAP.Type.CON);

        getAttributes().addAttribute(OBJECT_TITLE);
        getAttributes().addAttribute("rt", "it.unimore.device.sensor.trash_bin_fill");
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
            senMLRecord.setT(this.model.getTimestamp());
            senMLRecord.setV(this.model.getFillPercentage());

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
}
