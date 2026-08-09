package it.unimore.fum.iot.resources;

import com.google.gson.Gson;
import it.unimore.fum.iot.models.GPSSensorModel;
import it.unimore.fum.iot.utils.CoreInterfaces;
import it.unimore.fum.iot.utils.SenMLPack;
import it.unimore.fum.iot.utils.SenMLRecord;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;

import java.util.Optional;

public class GPSSensorResource extends CoapResource {

    private static final String OBJECT_TITLE = "GPSSensor";
    private static final Number SENSOR_VERSION = 0.1;

    private GPSSensorModel model = null;
    private String devideId = null;
    private Gson gson = null;

    public GPSSensorResource(String name, String deviceId) {
        super(name);
        this.devideId = deviceId;
        this.init();
    }

    private void init() {
        this.gson = new Gson();
        this.model = new GPSSensorModel();

        setObservable(true);
        setObserveType(CoAP.Type.CON);

        getAttributes().addAttribute(OBJECT_TITLE);
        getAttributes().addAttribute("rt", "it.unimore.device.sensor.gps");
        getAttributes().addAttribute("if", CoreInterfaces.CORE_S.getValue());
        getAttributes().addAttribute("ct", Integer.toString(MediaTypeRegistry.APPLICATION_SENML_JSON));
        getAttributes().addAttribute("ct", Integer.toString(MediaTypeRegistry.TEXT_PLAIN));
    }

    private Optional<String> getJsonSenmlResponse() {
        try{
            SenMLPack senMLPack =new SenMLPack();

            SenMLRecord senMLRecord = new SenMLRecord();
            senMLRecord.setBn(this.devideId);
            senMLRecord.setBver(SENSOR_VERSION);
            senMLRecord.setT(this.model.getTimestamp());

            SenMLRecord longitudeSenMLRecord = new SenMLRecord();
            longitudeSenMLRecord.setN("longitude");
            longitudeSenMLRecord.setU(this.model.getLongitudeUnit());
            longitudeSenMLRecord.setV(this.model.getLongitude());

            SenMLRecord latitudeSenMLRecord = new SenMLRecord();
            latitudeSenMLRecord.setN("latitude");
            latitudeSenMLRecord.setU(this.model.getLatitudeUnit());
            latitudeSenMLRecord.setV(this.model.getLatitude());

            senMLPack.add(senMLRecord);
            senMLPack.add(longitudeSenMLRecord);
            senMLPack.add(latitudeSenMLRecord);

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
